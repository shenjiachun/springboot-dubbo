package org.navychi.framework.order.service;

import org.navychi.framework.dao.po.order.Order;

import java.util.List;

public interface OrderShardingService {

    int batchInsert();

    List<Order> selectAll();

    List<Order> in();

}
