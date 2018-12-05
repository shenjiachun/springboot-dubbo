package org.navychi.framework.core.config.zipkin;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Future;

import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.protocol.dubbo.FutureAdapter;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.alibaba.fastjson.JSONObject;

import brave.Span;
import brave.Tracer;
import brave.propagation.Propagation;
import zipkin2.Endpoint;

public class ZipkinHelper {

    public static final Propagation.Setter<Map<String, String>, String> SETTER = new Propagation.Setter<Map<String, String>, String>() {
        @Override
        public void put(Map<String, String> carrier, String key, String value) {
            carrier.put(key, value);
        }

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    };

    public static final Propagation.Getter<Map<String, String>, String> GETTER = new Propagation.Getter<Map<String, String>, String>() {
        @Override
        public String get(Map<String, String> carrier, String key) {
            return carrier.get(key);
        }

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    };

    public static void buildSpan(Span span, Span.Kind kind, InetSocketAddress remoteAddress, String service, String method) {
        if (!span.isNoop()) {
            span.kind(kind).start();
            span.kind(kind);
            span.name(service + "/" + method);
            Endpoint.Builder remoteEndpoint = Endpoint.newBuilder().port(remoteAddress.getPort());
            if (!remoteEndpoint.parseIp(remoteAddress.getAddress())) {
                remoteEndpoint.parseIp(remoteAddress.getHostName());
            }
            span.remoteEndpoint(remoteEndpoint.build());
        }
    }

    public static Result spanTracing(Span span, Tracer tracer, Invoker<?> invoker, Invocation invocation,
            RpcContext rpcContext) {
        boolean isOneway = false;
        boolean deferFinish = false;
        try (Tracer.SpanInScope scope = tracer.withSpanInScope(span)) {
            Result result = invoker.invoke(invocation);
            if (result.hasException()) {
                onError(result.getException(), span);
            }
            isOneway = RpcUtils.isOneway(invoker.getUrl(), invocation);
            Future<Object> future = rpcContext.getFuture(); // the case on async client invocation
            if (future instanceof FutureAdapter) {
                deferFinish = true;
                ((FutureAdapter) future).getFuture().setCallback(new ZipkinHelper.FinishSpanCallback(span));
            }
            return result;
        } catch (Exception e) {
            onError(e, span);
            throw e;
        } finally {
            if (isOneway) {
                span.flush();
            } else if (!deferFinish) {
                span.finish();
            }
        }
    }

    static void onError(Throwable error, Span span) {
        span.error(error);
        if (error instanceof RpcException) {
            span.tag("dubbo.error_code", Integer.toString(((RpcException) error).getCode()));
        }
    }

    static final class FinishSpanCallback implements ResponseCallback {
        final Span span;

        FinishSpanCallback(Span span) {
            this.span = span;
        }

        @Override
        public void done(Object response) {
            span.finish();
        }

        @Override
        public void caught(Throwable exception) {
            onError(exception, span);
            span.finish();
        }
    }

}
