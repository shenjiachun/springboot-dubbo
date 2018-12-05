package org.navychi.framework.dao.po.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Order implements Serializable {

    private static final long serialVersionUID = 7916378973116030273L;

    private Long orderId;

    private Long userId;

}
