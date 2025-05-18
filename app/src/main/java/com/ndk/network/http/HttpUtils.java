package com.ndk.network.http;

import java.util.Map;

/**
 * 使用curl进行请求，网络请求在自线程，回调在主线程
 */
public class HttpUtils {

    private HttpUtils() {

    }

    private static class InnerClass {
        private final static HttpUtils instance = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return InnerClass.instance;
    }


    /**
     * GET  同步请求
     */
    private String sendGetRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params) {
        return HttpClient.getInstance().sendGetRequest(url, headers, cookies, params);
    }

    public String sendGetRequest(String url, Map<String, String> headers, Map<String, String> params) {
        return sendGetRequest(url, headers, null, params);
    }

    public String sendGetRequest(Request request) {
        return sendGetRequest(request.getUrl(), request.getHeaders(), request.getCookies(), request.getParams());
    }


    /**
     * GET  异步请求
     */
    private void asyncGetRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params, NetWorkCallback callback) {
        HttpClient.getInstance().asyncGetRequest(url, headers, cookies, params, callback);
    }

    public void asyncGetRequest(String url, Map<String, String> headers, Map<String, String> params, NetWorkCallback callback) {
        asyncGetRequest(url, headers, null, params, callback);
    }

    public void asyncGetRequest(Request request, NetWorkCallback callback) {
        asyncGetRequest(request.getUrl(), request.getHeaders(), request.getCookies(), request.getParams(), callback);
    }

    /**
     * POST   同步请求
     */
    private String sendPostRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params) {
        return HttpClient.getInstance().sendPostRequest(url, headers, cookies, params);
    }

    public String sendPostRequest(String url, Map<String, String> headers, Map<String, String> params) {
        return sendPostRequest(url, headers, null, params);
    }

    public String sendPostRequest(Request request) {
        return sendPostRequest(request.getUrl(), request.getHeaders(), request.getCookies(), request.getParams());
    }


    /**
     * POST  异步请求
     */
    private void asyncPostRequest(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params, NetWorkCallback callback) {
        HttpClient.getInstance().asyncPostRequest(url, headers, cookies, params, callback);
    }

    public void asyncPostRequest(String url, Map<String, String> headers, Map<String, String> params, NetWorkCallback callback) {
        asyncPostRequest(url, headers, null, params, callback);
    }

    public void asyncPostRequest(Request request, NetWorkCallback callback) {
        asyncPostRequest(request.getUrl(), request.getHeaders(), request.getCookies(), request.getParams(), callback);
    }


}
