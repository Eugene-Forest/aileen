package org.aileen.mod.auth.helper;

import org.aileen.mod.auth.enums.RequestEncryptType;

import java.util.Map;

public class AmHttpHelper {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String ENCRYPT_HEADER = "Ali-Encrypt";


    public static void addEncryptHeader(Map<String, String> headers, RequestEncryptType type){
        headers.put(ENCRYPT_HEADER, type.getValue());
    }
}
