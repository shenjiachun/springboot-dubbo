package org.navychi.framework.order.service.impl;

import org.navychi.framework.dao.mapper.order.OrderItemMapper;
import org.navychi.framework.dao.mapper.order.OrderMapper;
import org.navychi.framework.dao.po.order.Order;
import org.navychi.framework.order.service.OrderShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderShardingServiceImpl implements OrderShardingService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public int batchInsert() {

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Order order = new Order();
                order.setOrderId(Long.valueOf(j));
                order.setUserId(Long.valueOf(i));
                orderMapper.insert(order);
                order = null;
            }
        }
        return 500;
    }

    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    @Override
    public List<Order> in() {
        List<Long> ids = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            ids.add(Long.valueOf(i));
        }
        return orderMapper.in(ids, 8l, 1l);
    }
}
