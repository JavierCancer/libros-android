package edu.upc.eetac.dsa.jcancer.librosjavi.api;

/**
 * Created by Javier on 11/12/2014.
 */
public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}