package pl.coderstrust;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

  @Autowired
  private ApplicationContext context;

  @Test
  public void testContextLoaded() {
    assertThat(context).isNotNull();
    System.out.println(String.format("Application name: %s", context.getApplicationName()));
    System.out
        .println(String.format("Application startup timestamp: %d", context.getStartupDate()));
    System.out
        .println(String.format("Number of beans loaded: %d", context.getBeanDefinitionCount()));
    for (String beanName : context.getBeanDefinitionNames()) {
      System.out.println(beanName);
    }
  }

}
