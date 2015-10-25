package com.power4j.oauth2.web;

import com.power4j.oauth2.common.constants.OAuthConstants;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * ClassName: com.power4j.oauth2.web <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-10-23
 */
public class WebUtils extends com.power4j.oauth2.common.web.util.WebUtils {
    private WebUtils() {
        super();
    }


    public static Response writeOAuthJsonResponse(OAuthResponse oAuthResponse) {
        final int responseStatus = oAuthResponse.getResponseStatus();

        Response.ResponseBuilder builder = Response.status(oAuthResponse.getResponseStatus());

        final Map<String, String> headers = oAuthResponse.getHeaders();
        for (String key : headers.keySet()) {
            builder.header(key, headers.get(key));
        }
        // CORS setting
        builder.header("Access-Control-Allow-Origin", "*");
        builder.type(MediaType.APPLICATION_JSON_TYPE);
           /* response.setContentType(OAuth.ContentType.JSON);    //json
            response.setStatus(responseStatus);*/
        builder.entity(oAuthResponse.getBody());
        return builder.build();
    }


    public static Response writeOAuthQueryResponse(OAuthResponse oAuthResponse) {
        Response.ResponseBuilder builder = Response.status(oAuthResponse.getResponseStatus());
        final String locationUri = oAuthResponse.getLocationUri();
        try {
            if (locationUri== null || locationUri.equals(OAuthConstants.OOB_RESPONSE)){
                //writeOAuthOOBResponse(response,oAuthResponse);
            }else {
                final Map<String, String> headers = oAuthResponse.getHeaders();
                for (String key : headers.keySet()) {
                    builder.header(key, headers.get(key));
                }

                //response.setStatus(oAuthResponse.getResponseStatus());
                final URI location = new URI(locationUri);
               builder.location(location);
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

}
