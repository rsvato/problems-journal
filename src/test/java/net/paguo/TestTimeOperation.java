package net.paguo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import junit.framework.Assert;

/**
 * User: slava
 * Date: 16.12.2006
 * Time: 1:27:03
 * Version: $Id$
 */
public class TestTimeOperation {

    @Test
    public void testTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 0);
        Date result = cal.getTime();
        
        cal.setTimeInMillis(result.getTime());
        Date nDate = cal.getTime();
        System.out.println(nDate);
        nDate.setTime(nDate.getTime() + 23 * 60 * 60 * 1000);
        System.out.println(nDate);
        assertEquals("Date must be equals", nDate, result);
    }

    @Test
    public void testTimeInput(){
        String date = "2006-12-23 17:00";
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            System.out.println(sdFormat.parse(date));
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }
}
