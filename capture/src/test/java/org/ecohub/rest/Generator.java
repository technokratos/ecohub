package org.ecohub.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.api.data.ReceiverCollection;
import org.ecohub.rest.model.ReceiverType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Denis B. Kulikov<br/>
 * date: 27.09.2019:21:04<br/>
 */
public class Generator {

    public static void main(String[] args) throws IOException {
        Location base = new Location(55.794742, 49.106220);
        Random r = new Random();
        List<Receiver> receivers = new ArrayList<>();
        String[] objects = Stream.generate(() -> RandomString.make(3)).limit(5).toArray(String[]::new);
        for (int i = 0; i < 2000; i++) {
            double lng= base.getLongitude() + r.nextDouble() - 0.5;
            double lat = base.getLatitude() + r.nextDouble() -0.5;
            List<String> type = new ArrayList<>();
            type.add(objects[r.nextInt(objects.length)]);
            if( r.nextDouble()> 0.4){
                type.add(objects[r.nextInt(objects.length)]);
            }
            Receiver receiver = new Receiver((long)i, ReceiverType.BOX, r.nextDouble(), new Location(lat, lng), Arrays.asList(), true);
            receivers.add(receiver);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.writeValue(new FileOutputStream("temp.json"), new ReceiverCollection(receivers));

        ReceiverCollection list = mapper.readValue((new FileInputStream("temp.json")), ReceiverCollection.class);
    }
}
