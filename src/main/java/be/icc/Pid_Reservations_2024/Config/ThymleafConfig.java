package be.icc.Pid_Reservations_2024.Config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymleafConfig {

    @Bean
    public LayoutDialect thymleafDialect() {
        return new LayoutDialect();
    }

}
