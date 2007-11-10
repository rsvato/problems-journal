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
