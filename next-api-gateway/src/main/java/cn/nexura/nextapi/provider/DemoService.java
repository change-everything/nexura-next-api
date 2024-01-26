package cn.nexura.nextapi.provider;

import java.util.concurrent.CompletableFuture;

/**
 * @author PeiYP
 * @since 2024年01月26日 17:17
 */
public interface DemoService {
    String sayHello(String name);
    String sayHello2(String name);
    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}