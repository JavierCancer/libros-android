package edu.upc.eetac.dsa.jcancer.librosjavi.api;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Javier on 11/12/2014.
 */
public class Link {

    private String target;
    private Map<String, String> parameters;

    public Link() {
        parameters = new HashMap<String, String>();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}