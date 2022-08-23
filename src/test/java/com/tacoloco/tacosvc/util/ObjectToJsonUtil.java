package com.tacoloco.tacosvc.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJsonUtil {

    /**
     * Method to convert an object to JSON string to be used to test HTTP calls using MockWebServer
     *
     * @param obj Object
     * @return String format of the object
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
