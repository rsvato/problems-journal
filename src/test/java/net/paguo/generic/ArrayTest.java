package net.paguo.generic;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Array;

/**
 * @author Reyentenko
 */
public class ArrayTest {
    @Test
    public void testArray() {
        List<String> tst = new ArrayList<String>();
        tst.add("A");
        tst.add("B");
        tst.add("C");
        tst.add("D");
        Class<String> cmpClass = String.class;
        String[] listenerArray =
                (String[]) Array.newInstance(cmpClass, 4);
        listenerArray = tst.toArray(listenerArray);
    }
}
