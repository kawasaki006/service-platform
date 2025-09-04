package com.kawasaki.service.common.constants;

public class AuthConstants {
    // claim field names
    // for user tokens
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String ROLES = "roles";
    public static final String TOKEN_TYPE = "token_type";
    // for internal tokens
    public static final String CLIENT_ID = "client_id";
    public static final String SCOPES = "scopes";

    // token types
    public static final String INTERNAL_TOKEN = "internal";
    public static final String USER_TOKEN = "user";

    // role types
    public static final String USER = "USER";
    public static final String PROVIDER = "PROVIDER";
    public static final String ADMIN = "ADMIN";
    public static final String INTERNAL = "INTERNAL";

    // role prefixes
    public static final String ROLE_PREFIX = "ROLE_";
}
