package org.ecohub.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.ecohub.rest.api.data.ReceiverCollection;
import org.ecohub.rest.model.Box;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.ReceiverType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
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
        String[] objects = new String[]{"organic",
                "battery",
                "plastic",
                "glass",
                "light",
                "metal",
                "paper"};
        AtomicLong idGen = new AtomicLong(0);
        for (int i = 0; i < 100; i++) {
            double lng= base.getLongitude() + r.nextDouble() - 0.5;
            double lat = base.getLatitude() + r.nextDouble() -0.5;
            List<Box> boxes = Stream.generate(() -> new Box(idGen.incrementAndGet(), r.nextDouble(), objects[r.nextInt(objects.length)]))
                    .limit(r.nextInt(4)).collect(Collectors.toList());
            Receiver receiver = new Receiver(idGen.incrementAndGet(), ReceiverType.BOX,  new Location(lat, lng), true, boxes);
            receivers.add(receiver);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.writeValue(new FileOutputStream("capture\\src\\main\\resources\\data.json"), new ReceiverCollection(receivers));

        ReceiverCollection list = mapper.readValue((new FileInputStream("temp.json")), ReceiverCollection.class);
    }
}
