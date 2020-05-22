package com.covid19.alertsystem.config;

import com.covid19.alertsystem.entity.UserPO;
import com.mongodb.MongoClient;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration public class ApplicationConfiguration {

  @Autowired(required = false)
  private MongoClient mongoClient;

  @Bean
  public Morphia morphia() {
    Morphia morphia = new Morphia();
    morphia.map(UserPO.class);
    morphia.getMapper().getOptions().setStoreNulls(true);
    return morphia;
  }

  @Bean(name = "dataStore")
  public Datastore dataStore() {
    Datastore datastore = morphia().createDatastore(new MongoClient(), "alertSystem");
    datastore.getCollection(UserPO.class).dropIndexes();
    datastore.ensureIndexes();
    return datastore;
  }

  @Bean
  public AdvancedDatastore advancedDatastore() {
    AdvancedDatastore advancedDatastore =
        (AdvancedDatastore) morphia().createDatastore(mongoClient, "alertSystem");
    advancedDatastore.ensureIndexes();
    return advancedDatastore;
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
      }
    };
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
