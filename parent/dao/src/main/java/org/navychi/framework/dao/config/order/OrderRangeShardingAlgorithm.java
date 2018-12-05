package org.navychi.framework.dao.config.order;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.google.common.collect.Range;

import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
            RangeShardingValue<Long> shardingValue) {
        Collection<String> shardingResult = new LinkedHashSet<>(availableTargetNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String target : availableTargetNames) {
                if (target.endsWith(String.valueOf(i % 2))) {
                    shardingResult.add(target);
                }
            }
        }
        return shardingResult;
    }
}
