package org.navychi.framework.dao.config.product;

import java.util.Collection;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

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
