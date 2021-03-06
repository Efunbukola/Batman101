/*
 * Copyright (C) 2014 Jinsik Kim
 * 
 */
package ValidationEditText;
/**
 * ValidationRegularString.java
 * 
 * This class has patterns for regular expression.
 * I make e-mail, URL and number patterns.
 * If you want to have more patterns, you just register new patterns.
 * And If you add a new pattern, then you define selector in ValidationType.java 
 *  	and implement action in ValidationEditText.java.
 */
public class ValidationRegularString {
	final static String EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	final static String URL = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
	final static String NUMBER = "^[0-9]*$";
    final static String HU_ID = "^(?!\\s*$)[0-9\\s]{8}$";
    final static String PASSWORD = "^[a-zA-Z0-9]*$";
}
