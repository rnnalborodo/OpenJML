package java.util;

// FIXME - needs completion
 
public class MissingResourceException extends RuntimeException {
  
    /*@ public normal_behavior
      @   ensures standardThrowable(s);
      @*/
    //@ pure
    public MissingResourceException(String s, String ss, String sss);

    /*@ public normal_behavior
      @   ensures standardThrowable(s);
      @*/
    //@ pure
    MissingResourceException(String s, String ss, String sss, Throwable t);

    public String getKey();

    public String getClassName();
}
