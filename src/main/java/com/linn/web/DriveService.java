package com.linn.web;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import java.security.Principal;

public class DriveService {
    private static final String APPLICATION_NAME = "Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    public static Drive get(String token){

        Credential cred = new Credential(BearerToken.authorizationHeaderAccessMethod());
        // eyJhbGciOiJSUzI1NiIsImtpZCI6ImVlYTFiMWY0MjgwN2E4Y2MxMzZhMDNhM2MxNmQyOWRiODI5NmRhZjAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5ODc1ODYxNzY5MTEtZ2I1NWpuNWE4YjdrN281YmhxdTlvMXRwdGE3MXZmZmwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5ODc1ODYxNzY5MTEtZ2I1NWpuNWE4YjdrN281YmhxdTlvMXRwdGE3MXZmZmwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDg0NzQ2MDIwMzIxMDcxNjE2MzIiLCJlbWFpbCI6Im1pdHNvczRwYWxtb3VzQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoialJTYVZEbl9KOVBLeTl6V0s2akNOdyIsIm5vbmNlIjoiZTc2LTBhYWVqcnR1X1ZvU1dfRDJacEQ0Y01BeV9SZFFXUjVyc2xCdmE3SSIsIm5hbWUiOiLOlM63zrzOrs-Ez4HOt8-CIM6jzrnOtM63z4HPjM-Azr_Phc67zr_PgiIsInBpY3R1cmUiOiJodHRwczovL2xoNi5nb29nbGV1c2VyY29udGVudC5jb20vLWhmcnJodGdFbHFNL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y2xSYmFxUmpsdUsxMXpVaC1QMURpVVZGbklrcncvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6Is6UzrfOvM6uz4TPgc63z4IiLCJmYW1pbHlfbmFtZSI6Is6jzrnOtM63z4HPjM-Azr_Phc67zr_PgiIsImxvY2FsZSI6ImVsIiwiaWF0IjoxNjExNjkxNjg1LCJleHAiOjE2MTE2OTUyODV9.q9DLmV_BWG0HcFQ89R8NXdIIy9_IjmFP6B4vRSrT5Mw-wJe5cgt5fuOP73uMhh_cjPqJed-1LscJsPFLu4nnopnDF3W3ol-YS5DrQOExnr-K56kZAYxKZ9FAQ_fKYkVB-13qUOK8C19JUKD8-T7pUdf963GJxCySOJYwp3rZEuRnOElboyZACOAGWvRldW0WqJxRGGDFBFkBWZ1pSjOprq-SbFwIaUL7e56DSM4S3G-3jclzMnj_8waI2rhMDzE0B88m2C0_0fL_mxSMIcfcjwgJvxdmlJLZ4FNdhof3Yr1-2wmwVl5ku7KOo_lVR3UbPBpVH4j9_v-I262-GGZ_1Q

        cred.setAccessToken(token);
        GoogleClientRequestInitializer keyInitializer = new CommonGoogleClientRequestInitializer();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred).setGoogleClientRequestInitializer(keyInitializer).setApplicationName(APPLICATION_NAME).build();
    }

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    static private String getAccessToken(Principal principal){
        System.out.println(principal.getName());
        return "details.getTokenValue();";
    }
}

