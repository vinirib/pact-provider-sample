package br.com.vinirib.provider.pact.account.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class AccountBeans {

    @Bean
    public Module moneyModule() {
        return new MoneyModule()
                .withMoney()
                .withDefaultFormatting();
    }
}
