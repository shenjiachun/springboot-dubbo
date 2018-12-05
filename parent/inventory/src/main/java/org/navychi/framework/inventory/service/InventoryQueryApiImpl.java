package org.navychi.framework.inventory.service;

import org.navychi.framework.api.inventory.InventoryQueryApi;

import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass = InventoryQueryApi.class)
public class InventoryQueryApiImpl implements InventoryQueryApi {

    @Override
    public String query() {
        return "query";
    }
}
