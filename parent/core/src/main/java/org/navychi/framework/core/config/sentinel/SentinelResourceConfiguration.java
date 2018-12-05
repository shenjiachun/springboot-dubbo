package org.navychi.framework.core.config.sentinel;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;

@Configuration
public class SentinelResourceConfiguration implements InitializingBean {

    @Value("${sentinel.zookeeper.address}")
    private String sentinelZkAddr;

    @Value("${sentinel.groupId}")
    private String sentinelGroupId;

    @Value("${sentinel.flowDataId}")
    private String sentinelFlowDataId;

    @Override
    public void afterPropertiesSet() throws Exception {
        // init readable rule/**/
        SentinelNacosReadableDataSource.flushFlowRule(sentinelZkAddr, sentinelGroupId, sentinelFlowDataId);
        SentinelNacosReadableDataSource.flushSystemRule(sentinelZkAddr, sentinelGroupId, sentinelFlowDataId);
        SentinelNacosReadableDataSource.flushDegradeRule(sentinelZkAddr, sentinelGroupId, sentinelFlowDataId);

        // init writable rule
        WritableDataSource writableDataSource = new SentinelNacosWritableDataSource(sentinelZkAddr, sentinelGroupId,
                sentinelFlowDataId);
        WritableDataSourceRegistry.registerFlowDataSource(writableDataSource);
        WritableDataSourceRegistry.registerSystemDataSource(writableDataSource);
        WritableDataSourceRegistry.registerAuthorityDataSource(writableDataSource);
        WritableDataSourceRegistry.registerDegradeDataSource(writableDataSource);

    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

}
