package org.navychi.framework.dao.config.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages = {"org.navychi.framework.dao.mapper.product"})
public class ProductMapperConfig {
}
