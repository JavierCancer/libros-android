package edu.upc.eetac.dsa.jcancer.librosjavi.api;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Javier on 11/12/2014.
 */
public class LibrosRootAPI {
    private Map<String, Link> links;

    public LibrosRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }
}