package com.entpress.entpress.utility.api;

/**
 * Created by utimac on 04/02/2018.
 */
public class ApiUrl {
    private static String HOSTNAME = "http://entpress.calitunes.com/api/";
    public static String LOGIN_URL = HOSTNAME+"?url=user&action=login&";
    public static String SIGNUP_URL = HOSTNAME+"?url=user&action=signup&";
    public static String ALLPOST_URL = HOSTNAME+"?url=posts";
    public static String CAT_POSTS_URL = HOSTNAME+"?url=catposts&cat_id=";
    public static String SINGLE_POST_URL = HOSTNAME+"?url=singlepost&post_id=";
    public static String CAT_URL = HOSTNAME+"?url=categories";
    public static String SEARCH_URL = HOSTNAME+"?url=search&q=";
    public static String USER_POSTS = HOSTNAME+"?url=user&action=posts&userid=";
    public static String FILE_PATH_URL = "https://blog.calitunes.com/wp-content/uploads/";
}
