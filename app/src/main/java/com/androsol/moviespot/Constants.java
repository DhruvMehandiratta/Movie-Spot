package com.androsol.moviespot;

/**
 * Created by Dhruv on 24-03-2017.
 */

public class Constants {
    public static final String BACKGROUND_MUSIC_SHARED_PREFERENCE = "back_music_sp";
    public static final String ENABLE_DIALOGBOX = "enable_back_music";
    public static final String SHOW_DIALOGBOX = "show_dialogbox";
    public static final String SPLASH_SCREEN_SHARED_PREFERENCE = "splash_sp";
    public static final String UPCOMING = "upcoming";
    public static final String LATEST = "latest";
    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR = "popular";
    public static final String IN_THEATERS = "now_playing";
    public static final String AIRING_TODAY = "airing_today";
    public static final String AIRING_THIS_WEEK = "airing_this_week";
    public static String LANGUAGE = "-1";
    public static long clickedMovieId = 0;

    public static String getLanguageUrl(){
        String language = LANGUAGE;
        String postURL = "" ;
        if(language.equals("-1")){
            //english
            postURL = "&language=en";
        }
        else if(language.equals("0")){
            //korean
            postURL = "&language=ko";
        }else if(language.equals("1")){
            //Japanese
            postURL = "&language=ja";
        }
        else if(language.equals("2")){
            //German
            postURL = "&language=de";
        }
        else if(language.equals("3")){
            //French
            postURL = "&language=fr";
        }
        else if(language.equals("4")){
            //Chinese
            postURL = "&language=zh";
        }
        return postURL;
    }

}
