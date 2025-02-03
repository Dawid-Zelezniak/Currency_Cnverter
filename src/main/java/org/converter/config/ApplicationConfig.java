package org.converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class ApplicationConfig {

    private static final String TABLE_C_URL = "https://api.nbp.pl/api/exchangerates/tables/c/today/";

    @Bean("nbpTableCWebClient")
    public WebClient tableCClient() {
        return WebClient.create(TABLE_C_URL);
    }
}
