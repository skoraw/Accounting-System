package pl.coderstrust.invoices.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
//    implements WebMvcConfigurer {

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false).
        favorParameter(true).
        parameterName("mediaType").
        ignoreAcceptHeader(true).
        useJaf(false).
        defaultContentType(MediaType.APPLICATION_JSON).
        mediaType("pdf", MediaType.APPLICATION_PDF).
        mediaType("json", MediaType.APPLICATION_JSON);
  }

  @Bean
  public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    resolver.setContentNegotiationManager(manager);

    List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

    resolvers.add(jsonViewResolver());
    resolvers.add(pdfViewResolver());

    resolver.setViewResolvers(resolvers);
    return resolver;
  }

  @Bean
  public ViewResolver jsonViewResolver() {
    return new JsonViewResolver();
  }

  @Bean
  public ViewResolver pdfViewResolver() {
    return new PdfViewResolver();
  }
}