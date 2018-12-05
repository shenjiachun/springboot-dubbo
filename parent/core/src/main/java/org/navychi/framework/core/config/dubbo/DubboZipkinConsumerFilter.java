package org.navychi.framework.core.config.dubbo;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import lombok.extern.slf4j.Slf4j;
import org.navychi.framework.core.config.zipkin.ZipkinHelper;

import java.util.Map;

import static org.navychi.framework.core.config.zipkin.ZipkinHelper.*;

@Activate(group = Constants.CONSUMER)
@Slf4j
public class DubboZipkinConsumerFilter implements Filter {

    private SpringExtensionFactory springExtensionFactory = new SpringExtensionFactory();

    private Tracer tracer;

    private TraceContext.Injector<Map<String, String>> injector;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Tracing tracing = springExtensionFactory.getExtension(Tracing.class, "tracing");
        tracer = tracing.tracer();
        if (tracer == null){
            return invoker.invoke(invocation);
        }

        if (null == injector){
            injector = tracing.propagation().injector(SETTER);
        }

        RpcContext rpcContext = RpcContext.getContext();
        Span span = tracer.nextSpan();
        injector.inject(span.context(), invocation.getAttachments());

        ZipkinHelper.buildSpan(span, Span.Kind.CONSUMER, rpcContext.getRemoteAddress(), invoker.getInterface().getSimpleName(),
                RpcUtils.getMethodName(invocation));

        return ZipkinHelper.spanTracing(span, tracer, invoker, invocation, rpcContext);
    }
}
