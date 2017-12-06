package com.christos;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@EnableAsync
@SpringBootApplication

// This "extends" is also needed to do the configuration for the SpringApplicationBuilder
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // This method is added to configure the application builder to run the .war file on a
    // existing TomCat Server.
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }


    // We will use Apache Tiles to create the main structure of our site. This bean here is
    // needed to declare where that configuration file is located.
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        String[] defs = {"/WEB-INF/tiles.xml"};
        // We insert as an array of Strings the definitions we'd like to change
        tilesConfigurer.setDefinitions(defs);
        return tilesConfigurer;
    }

    // Spring has a view resolver. It takes a URl and figures out what to display.
    // We need to override the default resolver, and tell spring what jsps to view and how.
    // That's what this configuration does.
    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        return tilesViewResolver;
    }

    // This bean will be used to encrypt the user password
    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This is the error page bean
    @Bean
    EmbeddedServletContainerCustomizer errorHandler() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403")
                );
            }
        };
    }

    // This is the HTML validator for malicious code on the about profile section
    // OWASP Policy Factory
    @Bean
    PolicyFactory getUserHtmlPolicy() {
        return new HtmlPolicyBuilder()
                .allowCommonBlockElements()
                .allowCommonInlineFormattingElements()
                .toFactory();
    }


}
