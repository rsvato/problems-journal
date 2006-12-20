package net.paguo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

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
}
