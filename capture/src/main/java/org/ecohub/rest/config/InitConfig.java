package org.ecohub.rest.config;

/**
 * @author Denis B. Kulikov<br/>
 * date: 24.08.2019:19:24<br/>
 */

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.ReceiverCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
public class InitConfig {
    private static Logger logger = LoggerFactory.getLogger(InitConfig.class);

    @Bean
    @Qualifier("initReceiverCollection")
    public List<Receiver> receiverCollection(){
        try {
            File file = ResourceUtils.getFile("classpath:data.json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(new FileInputStream(file), ReceiverCollection.class).getReceivers();
        } catch (IOException e) {
            logger.error("Not load default values", e);
            return Collections.emptyList();
        }
    }
}
