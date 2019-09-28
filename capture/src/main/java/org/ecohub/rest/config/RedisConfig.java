package org.ecohub.rest.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.service.conditional.ClusterCondition;
import org.ecohub.rest.service.impl.MemoryGeoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Denis B. Kulikov<br/>
 * date: 25.08.2019:4:58<br/>
 */
@Configuration
@Conditional(ClusterCondition.class)
public class RedisConfig {

    private static Logger logger = LoggerFactory.getLogger(MemoryGeoServiceImpl.class);

    @Value("${redis.host:192.168.99.100}")
    private String redisHost;

    @Value("${redis.port:32768}")
    private int redisPort;


    @Autowired
    @Qualifier("initReceiverCollection")
    private List<Receiver> receivers;
    private ObjectMapper mapper = new ObjectMapper();
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }



    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfig);

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new GenericToStringSerializer<String>(String.class, Charset.forName("UTF8")));
        template.setValueSerializer(new GenericToStringSerializer<>(String.class, Charset.forName("UTF8")));
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        initFirstValue(template);
        return template;
    }

    private void initFirstValue(RedisTemplate<String, Object> template) {
        GeoOperations<String, Object> geoOperations = template.opsForGeo();
        receivers.stream()
                .forEach(r->
                        geoOperations.add("receivers", new Point(r.getLocation().getLongitude(), r.getLocation().getLatitude()), receiverToJson(r)));

    }

    private String receiverToJson(Receiver receiver)  {
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, receiver);
            return writer.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
