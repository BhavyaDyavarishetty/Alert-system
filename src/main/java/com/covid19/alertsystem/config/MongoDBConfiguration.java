package com.covid19.alertsystem.config;

import com.covid19.alertsystem.entity.UserPO;
import com.mongodb.MongoClient;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration public class MongoDBConfiguration {

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

}
