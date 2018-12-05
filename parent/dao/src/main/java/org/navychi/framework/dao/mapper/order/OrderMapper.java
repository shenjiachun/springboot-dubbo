package org.navychi.framework.dao.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.navychi.framework.dao.po.order.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    Order selectByPrimaryKey(Long orderId);

    int insert(Order order);

    List<Order> selectAll();

    List<Order> in(List<Long> ids, @Param("max") Long max, @Param("min") Long min);

}
