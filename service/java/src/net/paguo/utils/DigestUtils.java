package net.paguo.utils;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.NoSuchProviderException;

/**
 * User: slava
 * Date: 04.12.2006
 * Time: 2:30:43
 * Version: $Id$
 */
public class DigestUtils {
    public static String digestString(String s) throws NoSuchAlgorithmException, NoSuchProviderException {
           MessageDigest sha = MessageDigest.getInstance("SHA");
           byte[] r = sha.digest(s.getBytes());
           StringBuffer hexString = new StringBuffer();
           for (byte aR : r) {
               hexString.append(Integer.toHexString(0xFF & aR));
           }
           return hexString.toString();
       }
    
}
