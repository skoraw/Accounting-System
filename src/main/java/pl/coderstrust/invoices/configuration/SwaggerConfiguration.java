package pl.coderstrust.invoices.configuration;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("pl.coderstrust.invoices.controller"))
        .paths(PathSelectors.any())
        .build()
        .consumes(Collections.singleton("application/json"))
        .produces(Collections.singleton("application/json"))
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Accounting System",
        "Application provides an invoices management.",
        "1.0",
        null,
        new Contact("CodersTrust", "http://www.coderstrust.pl", "contact@coderstrust.pl"),
        "MIT Licence", "https://choosealicense.com/licenses/mit/", Collections.emptyList());
  }
}