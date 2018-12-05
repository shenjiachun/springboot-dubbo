package org.navychi.framework.dao.config.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages = {"org.navychi.framework.dao.mapper.order"})
public class OrderMapperConfig {
}
