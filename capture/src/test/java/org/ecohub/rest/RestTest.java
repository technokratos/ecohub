package org.ecohub.rest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  Get a location point in a certain area;
 *  Conquer a location point;
 *  Show your score;
 *
 * @author Denis B. Kulikov<br/>
 * date: 24.08.2019:21:16<br/>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = MainRest.class, webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestTest {

//
//    static final String URL_LOCATION = "http://localhost:9762/location";
//    static final String URL_CONQUER = "http://localhost:9762/conquer/%d";
//    static final String URL_SCORE = "http://localhost:9762/score/%d";
//
//    private static final long FIRST_CLIENT_ID = 1L;
//    private static final Long SECOND_CLIENT_ID = 2L;
//
//    private static final Area AREA = new Area(new Point(new double[]{-0.5946078, 51.5235359}), new Point(new double[]{-0.1355294, 52.6008404}));
//
//
//    @Autowired
//    TrashCanController trashCanController;
//    @Test
//    public void test() {
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<FeatureCollection> points = restTemplate.postForEntity(URL_LOCATION, AREA, FeatureCollection.class);
//        assertEquals(HttpStatus.OK, points.getStatusCode());
//        Map<Long, Feature> features = points.getBody().getFeatures();
//        Long firstPointId = features.keySet().iterator().next();
//        Feature firstPoint = features.get(firstPointId);
//
//        assertTrue("The point should be captured by first request",  restTemplate.postForEntity(format(URL_CONQUER, FIRST_CLIENT_ID), firstPoint.getGeometry(), Boolean.class).getBody());
//        assertFalse("The point should not be captured by second request",  restTemplate.postForEntity(format(URL_CONQUER, SECOND_CLIENT_ID), firstPoint.getGeometry(), Boolean.class).getBody());
//        assertFalse("The point already captured by the same client",  restTemplate.postForEntity(format(URL_CONQUER, FIRST_CLIENT_ID), firstPoint.getGeometry(), Boolean.class).getBody());
//
//        assertEquals("The frist client should capture 1 point",  Long.valueOf(1),  restTemplate.getForObject(format(URL_SCORE, FIRST_CLIENT_ID), Long.class));
//
//        assertEquals("The frist client should capture 1 point",  Long.valueOf(0), restTemplate.getForObject(format(URL_SCORE, SECOND_CLIENT_ID), Long.class));
//
//        ResponseEntity<FeatureCollection> updatedPoints = restTemplate.postForEntity(URL_LOCATION, AREA, FeatureCollection.class);
//
//        assertFalse("The udpated points should not be contained already captured point", updatedPoints.getBody().getFeatures().containsKey(firstPointId));
//
//
//    }
}

