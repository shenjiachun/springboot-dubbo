package org.navychi.framework.core.config.hystrix;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DubboHystrixCommand extends HystrixCommand<Result> {

    private Invocation invocation;

    // 统计一定时间内成功请求数
    private static final int STATUSTIME = 20000;

    // 用于计算百分比的滚动窗口时间长度(毫秒)
    private static final int ROLLINGTIME = 60000;

    private Invoker<?> invoker;

    // 使用dubbo配置的优先级 method > interface > application 同等级别 consumer > provider
    public DubboHystrixCommand(Invoker invoker, Invocation invocation, String group) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group)) // 组名使用模块名称
                // 服务等级为NORMAL的隔离粒度为模块,其他服务等级的隔离粒度为接口
                .andCommandKey(HystrixCommandKey.Factory.asKey(group))
                // 通用配置
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 是否开启熔断
                        .withCircuitBreakerEnabled(true)
                        // 触发熔断的错误率
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        // 强制关闭熔断器
                        .withCircuitBreakerForceClosed(false)
                        // 触发熔断器需要的请求量
                        .withCircuitBreakerRequestVolumeThreshold(20)
                        // 熔断器从打开到半开的等待时间
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        // 使用信号量隔离的方式 默认:线程池
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        // .withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
                        // 信号量阈值 默认:10
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
                        // // 是否开启超时时间中断抛出异常的功能
                        .withExecutionTimeoutEnabled(false)
                        // 超时后是否中断线程
                        .withExecutionIsolationThreadInterruptOnTimeout(true)
                        // // 超时时间 默认:1000//
                        .withExecutionTimeoutInMilliseconds(1000)
                        // 是否开启降级
                        .withFallbackEnabled(true)
                        // 信号量隔离时,允许请求降级的最大并发数
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(10)
                        // 计算错误率的间隔时间
                        .withMetricsHealthSnapshotIntervalInMilliseconds(500)
                        // 设置每个bucket内执行的次数,如果超过这个次数,丢弃最早的，加入最新的
                        .withMetricsRollingPercentileBucketSize(100)
                        // 是否开启监控统计功能,如果设置false,任何统计都返回-1
                        .withMetricsRollingPercentileEnabled(true)
                        // 用于计算百分比的滚动窗口内buckets的个数
                        .withMetricsRollingPercentileWindowBuckets(6)
                        // 用于计算百分比的滚动窗口时间长度
                        .withMetricsRollingPercentileWindowInMilliseconds(60000)
                        // 可统计的滚动窗口内的buckets数量,用于熔断器和指标发布
                        .withMetricsRollingStatisticalWindowBuckets(10)
                        // 可统计的滚动窗口的时间长度,这段时间内的执行数据用于熔断器和指标发布
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                        // 是否开启缓存
                        .withRequestCacheEnabled(true)
                        // 是否开启日志
                        .withRequestLogEnabled(true))
                // 线程池策略时的配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)
                        .withKeepAliveTimeMinutes(5).withMaxQueueSize(-1).withMetricsRollingStatisticalWindowBuckets(10)
                        .withMetricsRollingStatisticalWindowInMilliseconds(10).withQueueSizeRejectionThreshold(20)));

        this.invoker = invoker;
        this.invocation = invocation;
    }

    @Override
    protected Result run() throws Exception {
        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            throw new HystrixRuntimeException(HystrixRuntimeException.FailureType.COMMAND_EXCEPTION,
                    DubboHystrixCommand.class, result.getException().getMessage(), result.getException(), null);
        }
        return result;
    }

    @Override
    protected Result getFallback() {
        return new RpcResult("the dubbo fallback.");
    }

}
