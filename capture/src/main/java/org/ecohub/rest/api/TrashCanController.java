
package org.ecohub.rest.api;


import org.ecohub.rest.model.Area;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.TrashOperation;
import org.ecohub.rest.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Get a location point in a certain area;
 * Conquer a location point;
 * Show your score;
 */
@Component
@RestController
public class TrashCanController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeoService geoService;


    @RequestMapping(value = "/history/{clientId}", method = RequestMethod.GET)
    public List<TrashOperation> getHistory(String clientId) {
        return geoService.getHistory(clientId);
    }


    @RequestMapping(value = "/findReceiver", method = RequestMethod.POST)
    public List<Receiver> getReceiverInArea(@Valid @RequestBody Area area){
        logger.debug("Get location with area : {}", area);
        return geoService.getReceivers(area);
    }



}