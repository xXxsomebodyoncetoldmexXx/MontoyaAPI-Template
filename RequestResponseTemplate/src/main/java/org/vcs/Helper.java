package org.vcs;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpMessage;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.logging.Logging;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Helper {
    public static final Logging logger = EntryPoint.api.logging();
    public static boolean isMethod (HttpRequest httpRequest, String method) {
        return httpRequest.method().equalsIgnoreCase(method);
    }

    public static HttpHeader getHeaderByName (HttpMessage httpMessage, String headerName) {
        for (HttpHeader header : httpMessage.headers()) {
//            if (header.name().contains(headerName)) {
            if (header.name().equals(headerName)) {
                return header;
            }
        }
        return null;
    }

    public static HttpHeader getHeaderByValue (HttpMessage httpMessage, String headerValue) {
        for (HttpHeader header : httpMessage.headers()) {
//            if (header.value().contains(headerValue)) {
            if (header.value().equals(headerValue)) {
                return header;
            }
        }
        return null;
    }

    public static HttpParameter getParameterByName (HttpRequest httpRequest, String parameterName) {
        for(ParsedHttpParameter parameter : httpRequest.parameters()) {
//            if (parameter.name().contains(parameterName)) {
            if (parameter.name().equals(parameterName)) {
                return parameter;
            }
        }
        return null;
    }

    public static HttpParameter getParameterByValue (HttpRequest httpRequest, String parameterValue) {
        for(ParsedHttpParameter parameter : httpRequest.parameters()) {
//            if (parameter.name().contains(parameterValue)) {
            if (parameter.value().equals(parameterValue)) {
                return parameter;
            }
        }
        return null;
    }

    public static Map<String, String> paramsToMap(String params) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        for(String param : params.split("&")) {
            String[] keyVal = param.split("=", 2);
            paramsMap.put(keyVal[0], EntryPoint.api.utilities().urlUtils().decode(keyVal[1]));
        }
        return paramsMap;
    }

    public static String mapToParams (Map<String, String> params) {
        return params.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + EntryPoint.api.utilities().urlUtils().encode(e.getValue()))
                .collect(Collectors.joining("&"));
    }
}
