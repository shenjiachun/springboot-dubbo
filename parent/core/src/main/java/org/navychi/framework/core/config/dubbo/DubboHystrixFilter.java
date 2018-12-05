package org.navychi.framework.core.config.dubbo;

import org.navychi.framework.core.config.hystrix.DubboHystrixCommand;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Activate(group = Constants.CONSUMER)
public class DubboHystrixFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        DubboHystrixCommand command = new DubboHystrixCommand(invoker, invocation, "consumer");
        return command.execute();
    }

}
