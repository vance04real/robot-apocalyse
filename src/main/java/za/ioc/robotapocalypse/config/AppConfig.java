package za.ioc.robotapocalypse.config;


import okhttp3.OkHttpClient;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Configuration
public class AppConfig {

  @Bean
  public Mapper mapper() {
    return new DozerBeanMapper();
  }


  @Bean
  public OkHttpClient OkHttpClientFactory() {
    return new OkHttpClient();
  }
}
