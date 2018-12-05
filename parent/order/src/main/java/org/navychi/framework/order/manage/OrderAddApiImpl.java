package org.navychi.framework.order.manage;

import org.navychi.framework.api.inventory.InventoryQueryApi;
import org.navychi.framework.api.order.OrderAddApi;
import org.navychi.framework.api.product.ProductQueryApi;
import org.navychi.framework.order.service.OrderShardingService;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass = OrderAddApi.class)
public class OrderAddApiImpl implements OrderAddApi {

    @Autowired
    private OrderShardingService orderShardingService;

    @Reference(check = false, interfaceClass = ProductQueryApi.class)
    private ProductQueryApi productQueryApi;

    @Reference(check = false, interfaceClass = InventoryQueryApi.class)
    private InventoryQueryApi inventoryQueryApi;

    @Override
    public String query() {
        inventoryQueryApi.query();
        return productQueryApi.queryName(1l);
    }

    @Override
//    @SentinelResource(value = "order", entryType = EntryType.IN,
//            blockHandler = "exceptionHandler", fallback = "sizeallBack")
    public int size() {
        return 666;
    }

    public int sizeallBack() {
        return 90;
    }

    public int exceptionHandler(BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return 100;
    }

}
