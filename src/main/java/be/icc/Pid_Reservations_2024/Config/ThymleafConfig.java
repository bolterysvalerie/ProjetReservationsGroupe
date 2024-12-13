package be.icc.Pid_Reservations_2024.Config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to set up custom Thymeleaf dialects.
 * This configuration registers the {@link LayoutDialect} to enable layout features in Thymeleaf templates.
 */
@Configuration
public class ThymleafConfig {

    /**
     * Provides a {@link LayoutDialect} bean to be used by Thymeleaf for layout management.
     * @return a {@link LayoutDialect} instance for Thymeleaf
     */
    @Bean
    public LayoutDialect thymleafDialect() {
        return new LayoutDialect();
    }

}
