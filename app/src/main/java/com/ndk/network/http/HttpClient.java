package com.ndk.network.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HttpClient {

    static {
        System.loadLibrary("native-lib");
    }

    private native String RequestGetCurl(String url, List<String> headers);


    private native boolean DownloadGetCurl(String url, List<String> headers);


    private native String RequestPostCurl(String url, List<String> headers, String params);




    private HttpClient() {

    }

    private static class InnerClass {
        private final static HttpClient instance = new HttpClient();
    }

    public static HttpClient getInstance() {
        return HttpClient.InnerClass.instance;
    }


    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private final HashMap<String, HashMap<String, String>> cookieMap = new HashMap<>();


    /**
     * GET     同步请求
     *
     * @param url 请求地址
     * @return 请求结果
     */
    public String sendGetRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException(("url must not null"));
        }
        String domainString = Utils.INSTANCE.getDomain(url);
        List<String> headerList = getHeaderList(url, headers, cookies);
        if (params != null && params.size() != 0) {
            url += "?";
            StringBuilder paramsStringBuilder = new StringBuilder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value))) {
                    paramsStringBuilder.append(key);
                    paramsStringBuilder.append("=");
                    paramsStringBuilder.append(value);
                    paramsStringBuilder.append("&");
                }
            }
            String paramsString = paramsStringBuilder.substring(0, paramsStringBuilder.length() - 1);
            url += paramsString;
        }
        String responseString = RequestGetCurl(url, headerList);
        String[] responseStrings = responseString.split("\n");
        if (responseStrings.length > 0) {
            HashMap<String, String> childMap = getCookieMap(domainString);
            for (String responseHeader : responseStrings) {
                if (responseHeader.startsWith("Set-Cookie:")) {
                    String cookieString = responseHeader.substring(responseHeader.indexOf(":"));
                    String cookieKey = cookieString.substring(0, cookieString.indexOf("="));
                    String cookieValue = cookieString.substring(cookieString.indexOf("=") + 1, cookieString.indexOf(";"));
                    childMap.put(cookieKey, cookieValue);
                }
            }
            return responseStrings[responseStrings.length - 1];
        }
        return responseString;
    }

    /**
     * GET             异步请求
     *
     * @param url      请求地址
     * @param callback 结果回调
     */
    public void asyncGetRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params, NetWorkCallback callback) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException(("url must not null"));
        }
        String domainString = Utils.INSTANCE.getDomain(url);
        List<String> headerList = getHeaderList(url, headers, cookies);
        if (params != null && params.size() != 0) {
            url += "?";
            StringBuilder paramsStringBuilder = new StringBuilder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value))) {
                    paramsStringBuilder.append(key);
                    paramsStringBuilder.append("=");
                    paramsStringBuilder.append(value);
                    paramsStringBuilder.append("&");
                }
            }
            String paramsString = paramsStringBuilder.substring(0, paramsStringBuilder.length() - 1);
            url += paramsString;
        }

        String urlString = url;
        ThreadPoolUtil.execute(() -> {
            String responseString = RequestGetCurl(urlString, headerList);
            String[] responseStrings = responseString.split("\n");
            if (responseStrings.length > 0) {
                HashMap<String, String> childMap = getCookieMap(domainString);
                for (String responseHeader : responseStrings) {
                    if (responseHeader.startsWith("Set-Cookie:")) {
                        String cookieString = responseHeader.substring(responseHeader.indexOf(":"));
                        String cookieKey = cookieString.substring(0, cookieString.indexOf("="));
                        String cookieValue = cookieString.substring(cookieString.indexOf("=") + 1, cookieString.indexOf(";"));
                        childMap.put(cookieKey, cookieValue);
                    }
                }
                if (callback != null) {
                    mHandler.post(() -> callback.onMessage(responseStrings[responseStrings.length - 1]));
                }
            }
        });
    }

    /**
     * POST             同步请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 请求结果
     */
    public String sendPostRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException(("url must not null"));
        }
        String domainString = Utils.INSTANCE.getDomain(url);
        List<String> headerList = getHeaderList(url, headers, cookies);
        String paramsString = "";
        if (params != null && params.size() != 0) {
            StringBuilder paramsStringBuilder = new StringBuilder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value))) {
                    paramsStringBuilder.append(key);
                    paramsStringBuilder.append("=");
                    paramsStringBuilder.append(value);
                    paramsStringBuilder.append("&");
                }

            }
            paramsString = paramsStringBuilder.substring(0, paramsStringBuilder.length() - 1);
        }

        String responseString = RequestPostCurl(url, headerList, paramsString);
        String[] responseStrings = responseString.split("\n");
        if (responseStrings.length > 0) {
            HashMap<String, String> childMap = getCookieMap(domainString);
            for (String responseHeader : responseStrings) {
                if (responseHeader.startsWith("Set-Cookie:")) {
                    String cookieString = responseHeader.substring(responseHeader.indexOf(":"));
                    String cookieKey = cookieString.substring(0, cookieString.indexOf("="));
                    String cookieValue = cookieString.substring(cookieString.indexOf("=") + 1, cookieString.indexOf(";"));
                    childMap.put(cookieKey, cookieValue);
                }
            }
            return responseStrings[responseStrings.length - 1];
        }
        return responseString;
    }


    /**
     * POST            异步请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 结果回调
     */
    public void asyncPostRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params, NetWorkCallback callback) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException(("url must not null"));
        }
        String domainString = Utils.INSTANCE.getDomain(url);

        List<String> headerList = getHeaderList(url, headers, cookies);
        String paramsString = "";
        if (params != null && params.size() != 0) {
            StringBuilder paramsStringBuilder = new StringBuilder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value))) {
                    paramsStringBuilder.append(key);
                    paramsStringBuilder.append("=");
                    paramsStringBuilder.append(value);
                    paramsStringBuilder.append("&");
                }
            }
            paramsString = paramsStringBuilder.substring(0, paramsStringBuilder.length() - 1);
        }
        String paramsStrings = paramsString;
        ThreadPoolUtil.execute(() -> {
            String responseString = RequestPostCurl(url, headerList, paramsStrings);
            String[] responseStrings = responseString.split("\n");
            if (responseStrings.length > 0) {
                HashMap<String, String> childMap = getCookieMap(domainString);
                for (String responseHeader : responseStrings) {
                    if (responseHeader.startsWith("Set-Cookie:")) {
                        String cookieString = responseHeader.substring(responseHeader.indexOf(":"));
                        String cookieKey = cookieString.substring(0, cookieString.indexOf("="));
                        String cookieValue = cookieString.substring(cookieString.indexOf("=") + 1, cookieString.indexOf(";"));
                        childMap.put(cookieKey, cookieValue);
                    }
                }
                if (callback != null) {
                    mHandler.post(() -> callback.onMessage(responseStrings[responseStrings.length - 1]));
                }
            }
        });
    }

    /**
     * 获取当前Host下的cookie
     * @param domainString
     * @return
     */
    private HashMap<String, String> getCookieMap(String domainString) {
        HashMap<String, String> childMap = cookieMap.get(domainString);
        if (childMap == null) {
            childMap = new HashMap<>();
        }
        cookieMap.put(domainString, childMap);
        return childMap;
    }

    /**
     * 拼接请求头
     *
     * @param urlString
     * @param headers
     * @param cookies
     * @return
     */
    private List<String> getHeaderList(String urlString, Map<String, String> headers, Map<String, String> cookies) {
        String domainString = Utils.INSTANCE.getDomain(urlString);
        if (TextUtils.isEmpty(domainString)) {
            throw new NullPointerException(("domainString must not null"));
        }

        if ((!TextUtils.isEmpty(domainString)) && cookies != null && cookies.size() != 0) {
            getCookieMap(domainString).putAll(cookies);
        }

        List<String> headerList = new ArrayList<>();
        if (headers != null && headers.size() != 0) {//暂时没验证
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value))) {
                    headerList.add(key + ":" + value + ";");
                }
            }
        }
        HashMap<String, String> childMap = getCookieMap(domainString);
        if (childMap.size() != 0) {
            for (String key : childMap.keySet()) {
                String value = childMap.get(key);
                if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value))) {
                    headerList.add("Cookie:" + key + "=" + value + ";");
                }
            }
        }
        return headerList;
    }

}
