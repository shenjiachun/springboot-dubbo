package org.navychi.framework.order.manage;

import org.navychi.framework.api.order.OrderQueryApi;

import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass = OrderQueryApi.class)
public class OrderQueryApiImpl implements OrderQueryApi {

    @Override
    public String query() {
        return "query";
    }

}
