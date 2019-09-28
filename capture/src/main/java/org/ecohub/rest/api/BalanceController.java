
package org.ecohub.rest.api;


import org.ecohub.rest.model.TrashClient;
import org.ecohub.rest.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Get a location point in a certain area;
 * Conquer a location point;
 * Show your score;
 *
 */
@Component
@RestController
public class BalanceController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeoService geoService;

    /**
     * 40 008,55 кь = 360
     * 1 m - ?
     *
     * @return
     */

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public Double balance(Long id) {
        TrashClient client =  geoService.getClientById(id);

        if (client == null) {
            throw new IllegalStateException("Not found receiver");
        }

        return client.getBalance();

    }



}