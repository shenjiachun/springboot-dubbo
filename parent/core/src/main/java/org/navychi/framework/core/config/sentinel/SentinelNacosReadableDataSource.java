package org.navychi.framework.core.config.sentinel;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentinelNacosReadableDataSource {

    /**
     * flushFlowRule
     * 
     * @param sentinelZkAddr
     * @param sentinelGroupId
     * @param sentinelFlowDataId
     */
    public static void flushFlowRule(String sentinelZkAddr, String sentinelGroupId, String sentinelFlowDataId) {
        // readable flow rule config
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(sentinelZkAddr,
                sentinelGroupId, sentinelFlowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

    /**
     * flushSystemRule
     *
     * @param sentinelZkAddr
     * @param sentinelGroupId
     * @param sentinelFlowDataId
     */
    public static void flushSystemRule(String sentinelZkAddr, String sentinelGroupId, String sentinelFlowDataId) {
        // readable flow rule config
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(sentinelZkAddr,
                sentinelGroupId, sentinelFlowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                }));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
    }

    /**
     * flushDegradeRule
     *
     * @param sentinelZkAddr
     * @param sentinelGroupId
     * @param sentinelFlowDataId
     */
    public static void flushDegradeRule(String sentinelZkAddr, String sentinelGroupId, String sentinelFlowDataId) {
        // readable flow rule config
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(sentinelZkAddr,
                sentinelGroupId, sentinelFlowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                }));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
    }

    /**
     * flushAuthorityRule
     *
     * @param sentinelZkAddr
     * @param sentinelGroupId
     * @param sentinelFlowDataId
     */
    public static void flushAuthorityRule(String sentinelZkAddr, String sentinelGroupId, String sentinelFlowDataId) {
        // readable flow rule config
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new NacosDataSource<>(sentinelZkAddr,
                sentinelGroupId, sentinelFlowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                }));
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());
    }

}
