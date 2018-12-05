package org.navychi.framework.dao.config.order;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class OrderPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        for (String target : availableTargetNames) {
            if (target.endsWith(String.valueOf(shardingValue.getValue() % 2))) {
                return target;
            }
        }
        throw new IllegalArgumentException();
    }



}
