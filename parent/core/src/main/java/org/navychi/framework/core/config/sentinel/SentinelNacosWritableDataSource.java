package org.navychi.framework.core.config.sentinel;

import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentinelNacosWritableDataSource implements WritableDataSource {

    private final String groupId;
    private final String dataId;

    /**
     * Note: The Nacos config might be null if its initialization failed.
     */
    private ConfigService configService = null;

    public SentinelNacosWritableDataSource(String serverAddr, String groupId, String dataId) {
        this.groupId = groupId;
        this.dataId = dataId;
        init(serverAddr);
    }

    private void init(final String serverAddr) {
        try {

            this.configService = NacosFactory.createConfigService(serverAddr);
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            RecordLog.warn("[SentinelNacosWritableDataSource] Error occurred when initializing Zookeeper data source",
                    e);
        }
    }

    @Override
    public void write(Object value) throws Exception {
        this.configService.publishConfig(dataId, groupId, JSON.toJSONString(value));
    }

    @Override
    public void close() throws Exception {
        if (null != this.configService) {
        }
    }
}
