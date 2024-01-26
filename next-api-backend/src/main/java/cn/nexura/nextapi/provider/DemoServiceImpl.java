package cn.nexura.nextapi.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author PeiYP
 * @since 2024年01月26日 17:21
 */
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("Hello " + name + ", request from consumer: " + RpcContext.getServiceContext().getRemoteAddress());
        return "Hello " + name;
    }
    @Override
    public String sayHello2(String name) {
        return "nexura";
    }
}