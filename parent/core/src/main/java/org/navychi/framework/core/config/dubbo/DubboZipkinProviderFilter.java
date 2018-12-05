package org.navychi.framework.core.config.dubbo;

import static org.navychi.framework.core.config.zipkin.ZipkinHelper.GETTER;

import java.util.Map;

import brave.propagation.TraceContextOrSamplingFlags;
import org.navychi.framework.core.config.zipkin.ZipkinHelper;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.support.RpcUtils;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import lombok.extern.slf4j.Slf4j;

@Activate(group = Constants.PROVIDER)
@Slf4j
public class DubboZipkinProviderFilter implements Filter {

    private SpringExtensionFactory springExtensionFactory = new SpringExtensionFactory();

    private Tracer tracer;

    private TraceContext.Extractor<Map<String, String>> extractor;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Tracing tracing = springExtensionFactory.getExtension(Tracing.class, "tracing");
        tracer = tracing.tracer();
        if (null == tracer){
            return invoker.invoke(invocation);
        }

        if (null == extractor){
            extractor = tracing.propagation().extractor(GETTER);
        }

        TraceContextOrSamplingFlags extracted = extractor.extract(invocation.getAttachments());
        Span span = extracted.context() != null
                ? tracer.joinSpan(extracted.context())
                : tracer.nextSpan(extracted);

        RpcContext rpcContext = RpcContext.getContext();
        ZipkinHelper.buildSpan(span, Span.Kind.SERVER, rpcContext.getRemoteAddress(), invoker.getInterface().getSimpleName(),
                RpcUtils.getMethodName(invocation));

        return ZipkinHelper.spanTracing(span, tracer, invoker, invocation, rpcContext);
    }

}
