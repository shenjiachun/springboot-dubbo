package org.navychi.framework.gateway.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.navychi.framework.api.order.OrderAddApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Reference(interfaceClass = OrderAddApi.class, check = false)
    private OrderAddApi orderAddApi;

    @GetMapping(value = "/query")
    public String query() {
        return orderAddApi.query();
    }

    @GetMapping(value = "/size")
    public int size() {
        return orderAddApi.size();
    }

}
