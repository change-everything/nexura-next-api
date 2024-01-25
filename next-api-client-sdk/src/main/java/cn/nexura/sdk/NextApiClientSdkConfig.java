package cn.nexura.sdk;

import cn.nexura.sdk.client.NextApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author peiYP
 */
@Configuration
@ConfigurationProperties("nextapi.client")
@Data
@ComponentScan
public class NextApiClientSdkConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public NextApiClient nextApiClient() {
        return new NextApiClient(accessKey, secretKey);
    }

}
