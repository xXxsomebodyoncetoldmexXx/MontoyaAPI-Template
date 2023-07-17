package org.vcs;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpMessage;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.logging.Logging;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Helper {
//    public static final Logging logger = EntryPoint.api.logging();
    public static final Logging logger = null;
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

    public static JsonElement getJsonElementByKey(JsonElement jsonParent, String key) {
        if (jsonParent.isJsonArray()) {
            JsonElement tmp = null;
            for (JsonElement jsonChild : jsonParent.getAsJsonArray()) {
                if (jsonChild.isJsonObject() || jsonChild.isJsonArray()) {
                    tmp = getJsonElementByKey(jsonChild, key);
                    if (tmp != null) {
                        return tmp;
                    }
                }
            }
        } else if (jsonParent.isJsonObject()) {
            JsonElement tmp = null;
            for (Map.Entry<String, JsonElement> jsonChild : jsonParent.getAsJsonObject().entrySet()) {
                if (jsonChild.getKey().equals(key)) {
//                if (jsonChild.getKey().equalsIgnoreCase("key")) {
//                if (jsonChild.getKey().contains("key")) {
                    return jsonParent;
                }
                if (jsonChild.getValue().isJsonArray() || jsonChild.getValue().isJsonObject()) {
                    tmp = getJsonElementByKey(jsonChild.getValue(), key);
                    if (tmp != null) {
                        return tmp;
                    }
                }
            }
        }
        return null;
    }

    public static JsonElement getJsonElementByValue(JsonElement jsonParent, JsonPrimitive value) {
        if (jsonParent.isJsonArray()) {
            JsonElement tmp = null;
            for (JsonElement jsonChild : jsonParent.getAsJsonArray()) {
                if (jsonChild.isJsonObject() || jsonChild.isJsonArray()) {
                    tmp = getJsonElementByValue(jsonChild, value);
                    if (tmp != null) {
                        return tmp;
                    }
                } else if (jsonChild.isJsonPrimitive() && jsonChild.getAsJsonPrimitive().equals(value)) {
//                } else if (jsonChild.isJsonPrimitive() && jsonChild.getAsString().contains(value.getAsString())) {
                    return jsonParent;
                }
            }
        } else if (jsonParent.isJsonObject()) {
            JsonElement tmp = null;
            for (Map.Entry<String, JsonElement> jsonChild : jsonParent.getAsJsonObject().entrySet()) {
                if (jsonChild.getValue().isJsonPrimitive() && jsonChild.getValue().getAsJsonPrimitive().equals(value)) {
//                if (jsonChild.getValue().isJsonPrimitive() && jsonChild.getValue().getAsString().contains(value.getAsString())) {
                    return jsonParent;
                }
                if (jsonChild.getValue().isJsonArray() || jsonChild.getValue().isJsonObject()) {
                    tmp = getJsonElementByValue(jsonChild.getValue(), value);
                    if (tmp != null) {
                        return tmp;
                    }
                }
            }
        }
        return null;
    }
}
