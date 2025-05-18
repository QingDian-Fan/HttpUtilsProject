package com.ndk.network.http;

import android.text.TextUtils;

import java.util.Map;

public class Request {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private Map<String, String> params;

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getParams() {
        return params;
    }

    private Request(String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> params) {
        this.url = url;
        this.headers = headers;
        this.cookies = cookies;
        this.params = params;
    }

    public static class Builder {
        String url;
        Map<String, String> headers;
        Map<String, String> cookies;
        Map<String, String> params;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setCookies(Map<String, String> cookies) {
            this.cookies = cookies;
            return this;
        }

        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Request build() {
            if (url==null|| TextUtils.isEmpty(url)){
                throw new NullPointerException("url must be not null");
            }
            return new Request(url, headers, cookies, params);
        }
    }


}
