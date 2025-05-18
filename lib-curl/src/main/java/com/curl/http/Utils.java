package com.curl.http;

import android.net.Uri;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String getDomain(String url) {
        try {
            Pattern pattern = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|top)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(url);
            matcher.find();
            return matcher.group();
        } catch (IllegalStateException e) {
            Log.e("TAG----->IllegalStateException", e.getMessage() + "");
            return Uri.parse(url).getHost();
        }
    }
}
