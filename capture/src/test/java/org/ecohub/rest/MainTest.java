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
        classes = MainRest.class)
public class MainTest {
//
//    private static final long FIRST_CLIENT_ID = 1L;
//    private static final Long SECOND_CLIENT_ID = 2L;
//    private static final Area AREA = new Area(new Point(new double[]{-0.5946078, 51.5235359}), new Point(new double[]{-0.1355294, 52.6008404}));
//
//    @Autowired
//    private TrashCanController trashCanController;
//    @Autowired
//    private MappingService mappingService;
//
//    @Test
//    public void test() {
//
//
//        FeatureCollection points = trashCanController.getPoints(AREA);
//
//        Long firstPoint = points.getFeatures().keySet().iterator().next();
//        Feature firstFeature = points.getFeatures().get(firstPoint);
//
//        assertTrue("The point should be captured by first request", trashCanController.conquer(FIRST_CLIENT_ID, firstFeature.getGeometry()));
//        assertFalse("The point should not be captured by second request", trashCanController.conquer(SECOND_CLIENT_ID, firstFeature.getGeometry()));
//        assertFalse("The point already captured by the same client", trashCanController.conquer(FIRST_CLIENT_ID, firstFeature.getGeometry()));
//
//        assertEquals("The frist client should capture 1 point",  Long.valueOf(3), trashCanController.score(FIRST_CLIENT_ID));
//
//        assertEquals("The frist client should capture 1 point",  Long.valueOf(0), trashCanController.score(SECOND_CLIENT_ID));
//
//        FeatureCollection updatedPoints = trashCanController.getPoints(AREA);
//
//        assertFalse("The udpated points should not be contained already captured point", updatedPoints.getFeatures().containsKey(firstPoint));
//
//    }
}

