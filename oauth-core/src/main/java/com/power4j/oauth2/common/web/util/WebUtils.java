package com.power4j.oauth2.common.web.util;

import com.power4j.oauth2.common.constants.OAuthConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * ClassName: org.power4j.web.util <br>
 *
 * @author Kuo Hong
 * @version 2015-10-11
 */
public class WebUtils {
    protected WebUtils() {
    }


    public static void writeOAuthJsonResponse(HttpServletResponse response, OAuthResponse oAuthResponse) {

        final int responseStatus = oAuthResponse.getResponseStatus();
        try {

            final Map<String, String> headers = oAuthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            // CORS setting
            response.setHeader("Access-Control-Allow-Origin", "*");

            response.setContentType(OAuth.ContentType.JSON);    //json
            response.setStatus(responseStatus);

            final PrintWriter out = response.getWriter();
            out.print(oAuthResponse.getBody());
            out.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Write OAuthResponse error", e);
        }
    }





    public static void writeOAuthQueryResponse(HttpServletResponse response, OAuthResponse oAuthResponse) {
        final String locationUri = oAuthResponse.getLocationUri();
        try {
            if (locationUri== null || locationUri.equals(OAuthConstants.OOB_RESPONSE)){
                writeOAuthOOBResponse(response,oAuthResponse);
            }else {
                final Map<String, String> headers = oAuthResponse.getHeaders();
                for (String key : headers.keySet()) {
                    response.addHeader(key, headers.get(key));
                }

                response.setStatus(oAuthResponse.getResponseStatus());
                response.sendRedirect(locationUri);
            }


        } catch (IOException e) {
            throw new IllegalStateException("Write OAuthResponse error", e);
        }
    }

    public static void writeOAuthOOBResponse(HttpServletResponse response, OAuthResponse oAuthResponse) {
        final String locationUri = oAuthResponse.getLocationUri();
        try {

            final Map<String, String> headers = oAuthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            // CORS setting
            response.setHeader("Access-Control-Allow-Origin", "*");

            response.setContentType(OAuth.ContentType.JSON);    //json
            response.setStatus(oAuthResponse.getResponseStatus());

            final PrintWriter out = response.getWriter();
            out.print(oAuthResponse.getBody());
            out.flush();

        } catch (IOException e) {
            throw new IllegalStateException("Write OAuthResponse error", e);
        }
    }


    /**
     * Retrieve client ip address
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String retrieveClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isUnAvailableIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isUnAvailableIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isUnAvailableIp(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isUnAvailableIp(String ip) {
        return (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip));
    }
}
