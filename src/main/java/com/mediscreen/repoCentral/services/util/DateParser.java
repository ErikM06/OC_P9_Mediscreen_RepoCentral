package com.mediscreen.repoCentral.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateParser {

    Logger logger = LoggerFactory.getLogger(DateParser.class);

    public Date stringToDate (String date) {
        Date parsedDate = new Date();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            logger.info("Error at DateParser");
            e.getMessage();
        }
        return parsedDate;
    }
}
