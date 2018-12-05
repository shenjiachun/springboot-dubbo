package org.navychi.framework.product.manage;

import com.alibaba.dubbo.config.annotation.Service;
import org.navychi.framework.api.product.ProductQueryApi;

@Service(interfaceClass = ProductQueryApi.class)
public class ProductQueryApiImpl implements ProductQueryApi {

    @Override
    public String queryName(Long id) {
        return "queryName";
    }

}
