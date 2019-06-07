package com.skuniv.cs.geonyeong.portal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
public class PortalApplicationTests {

    SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");

    @Test
    public void test() throws ParseException {
        Date startDate = dt.parse("20190301");
        Date endDate = dt.parse("20190620");
        long diff = endDate.getTime() - startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        diffDays = Math.abs(diffDays);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        for (int i = 1; i <= diffDays; i++) {
            cal.add(Calendar.DATE, 1);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
            log.info("dayNum => {}", dayNum);
        }
    }

}
