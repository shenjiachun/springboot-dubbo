package org.navychi.framework.gateway;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = { "org.navychi.framework" })
@EnableDubbo
@Slf4j
public class GatewayApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() {
        DubboFallbackRegistry.setConsumerFallback(new GatewayFallBack());
    }

    @Slf4j
    protected static class GatewayFallBack implements DubboFallback {

        @Override
        public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
            log.info("GatewayFallBack {}", "handle");
            RpcResult result = new RpcResult();
            result.setValue("11");
            return result;
        }
    }

}
