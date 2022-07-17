package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import org.springframework.core.env.Environment;

@Configuration
@PropertySource({
        "classpath:application.properties"
})
public class MvcConfig implements WebMvcConfigurer {

    private final Environment env;

    @Autowired
    public MvcConfig(Environment environment) {
        this.env = environment;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML5")
    public ClassLoaderTemplateResolver templateResolver() {
        var templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("/templates/" + env.getProperty("site.template") + "/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {
        var viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }
}
