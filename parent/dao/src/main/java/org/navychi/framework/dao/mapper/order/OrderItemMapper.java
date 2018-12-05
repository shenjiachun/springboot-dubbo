package org.navychi.framework.dao.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.navychi.framework.dao.po.order.OrderItem;

@Mapper
public interface OrderItemMapper {

    OrderItem selectByPrimaryKey(Long orderItemId);

    int insert(OrderItem orderItem);

}
