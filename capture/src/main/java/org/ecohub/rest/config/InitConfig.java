package org.ecohub.rest.config;

/**
 * @author Denis B. Kulikov<br/>
 * date: 24.08.2019:19:24<br/>
 */

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.api.data.ReceiverCollection;
import org.ecohub.rest.model.TrashClient;
import org.ecohub.rest.model.TrashOperation;
import org.ecohub.rest.model.TrashStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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

    @Bean
    @Qualifier("clientCache")
    public Map<Long, TrashClient> clientCache() {
        return LongStream.iterate(1, operand -> operand + 1)
                .limit(5)
                .mapToObj(index -> new TrashClient(index, 0.0))
                .collect(Collectors.toMap(TrashClient::getId,
                trashClient -> trashClient));
    }

    @Bean
    @Qualifier("initTrashOperation")
    public List<TrashOperation> initTrashOperation(){
        List<Receiver> receivers = receiverCollection();
        Random r = new Random();
        AtomicLong id = new AtomicLong(0);
        return clientCache().entrySet().stream().map(e -> {
            TrashClient value = e.getValue();
            Receiver receiver = receivers.get(r.nextInt(receivers.size() - 1));
            Long clientId = value.getId();
            value.setBalance(r.nextDouble()*3);
            return new TrashOperation(id.incrementAndGet(), clientId, receiver.getId(), ZonedDateTime.now(), TrashStatus.IN_BOX, receiver.getLocation(), 0.4, "plastic");


        }).collect(Collectors.toList());
    };
}
