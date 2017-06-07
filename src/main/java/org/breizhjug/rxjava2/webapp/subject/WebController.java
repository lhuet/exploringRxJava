package org.breizhjug.rxjava2.webapp.subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lhuet on 28/05/2017.
 */
@Controller
public class WebController {

    public static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    Business business;


    @RequestMapping("/event")
    public ResponseEntity<String> getEvent() {

        log.info("Event received");

        business.pushData(System.currentTimeMillis());
        return ResponseEntity.ok("ok");

    }

}
