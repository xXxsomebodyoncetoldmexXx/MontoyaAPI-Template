package org.vcs;

import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;

import java.util.Map;


public class CustomHttpHandler implements HttpHandler {
    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
        // USEFUL DOC: https://portswigger.github.io/burp-extensions-montoya-api/javadoc/burp/api/montoya/http/handler/HttpRequestToBeSent.html
        HttpRequest updateRequest = requestToBeSent.withDefaultHeaders();

        /*********************************PATH PROCESSING*********************************/
        String updatePath = updateRequest.path();
        String[] urlParts = updatePath.split("\\?", 2);

        // Raw Path
        urlParts[0] += "/Foo/Bar";

        // Parse parameter
        if (urlParts.length > 1) {
            Map<String, String> params = Helper.paramsToMap(urlParts[1]);
            Helper.logger.logToOutput(params.get("foo"));
            params.computeIfPresent("foo", (k, v) -> "foofoo");
            urlParts[1] = Helper.mapToParams(params);
            Helper.logger.logToOutput(urlParts[1]);
        }

        updatePath = String.join("?", urlParts);
        updateRequest = updateRequest.withPath(updatePath);

        /******************************PARAMETER PROCESSING*******************************/
        HttpParameter removedParameter = Helper.getParameterByName(updateRequest, "foo");
        if (removedParameter != null) {
            updateRequest.withRemovedParameters(removedParameter);
        }

        HttpParameter newParameter = HttpParameter.parameter("ABC", "XYZ", HttpParameterType.URL);
        updateRequest = updateRequest.withAddedParameters(newParameter);

        HttpParameter updateParameter = HttpParameter.parameter(newParameter.name(), "FOOBAR", HttpParameterType.URL);
        updateRequest = updateRequest.withUpdatedParameters(updateParameter);



        /*******************************HEADERS PROCESSING*******************************/
        if (Helper.getHeaderByName(updateRequest, "User-Agent") != null) {
            updateRequest = updateRequest.withRemovedHeader("User-Agent");
            updateRequest = updateRequest.withAddedHeader("User-Agent", "Foo Bar");
            updateRequest = updateRequest.withUpdatedHeader("User-Agent", "VCS Browser");
        }

        /********************************BODY PROCESSING********************************/
        String updateBody = updateRequest.bodyToString();
        updateBody += "\nFooBar";
        updateRequest = updateRequest.withBody(updateBody);
        /*******************************************************************************/


        return RequestToBeSentAction.continueWith(updateRequest);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
        // USEFUL DOC: https://portswigger.github.io/burp-extensions-montoya-api/javadoc/burp/api/montoya/http/handler/HttpResponseReceived.html
        HttpResponse updatedResponse = responseReceived.withRemovedHeader("NON_EXISTING_HEADER");

        /*******************************HEADERS PROCESSING*******************************/
        if (Helper.getHeaderByName(updatedResponse, "Date") != null) {
            updatedResponse = updatedResponse.withRemovedHeader("Date");
            updatedResponse = updatedResponse.withAddedHeader("Date2", "FUTURE");
            updatedResponse = updatedResponse.withUpdatedHeader("Date2", "FooBar");
        }

        /********************************BODY PROCESSING********************************/
        String updateBody = updatedResponse.bodyToString();
        updateBody += "\nFooBar";
        updatedResponse = updatedResponse.withBody(updateBody);
        /*******************************************************************************/

        return ResponseReceivedAction.continueWith(updatedResponse);
    }


}
