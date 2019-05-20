package com.sp.mq.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientUtil {
    @SuppressWarnings("resource")
    public static String doPost(String url, String jsonstr, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        CookieStore cookieStore = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept","application/json");
            httpPost.setEntity(new StringEntity(jsonstr.toString(), Charset.forName("UTF-8")));
            cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.getConnectionManager().shutdown();
            }
            return result;
        }
    }


    public static String doPostPaasHeader(String url, String jsonstr, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        CookieStore cookieStore = null;
        String res = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            cookieStore = new BasicCookieStore();
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookiePolicy.BROWSER_COMPATIBILITY).build();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpPost);

//            List<Cookie> cookies = cookieStore.getCookies();
//            for (int i = 0; i < cookies.size(); i++) {
//                System.out.println("Local cookie: " + cookies.get(i));
//            }
            Header[] tce = response.getHeaders("Set-Cookie");
            if (response != null) {
                if (tce != null) {
                    for (int i = 0; i < tce.length; i++) {
                        Header header = tce[i];
                        String[] arr = header.getValue().split(";");

                        for (int j = 0; j < arr.length; j++) {

                            if (arr[j].indexOf("tce.sig") != -1) {
                                map.put("tce.sig", arr[j].split("=")[1]);
                            }

                            if (arr[j].indexOf("tce") != -1) {
                                map.put("tce", arr[j].split("=")[1]);
                            }
                            if (arr[j].indexOf("tce_user_current_config.sig") != -1) {
                                map.put("tce_user_current_config.sig", arr[j].split("=")[1]);
                            }
                            if (arr[j].indexOf("tce_user_current_config") != -1) {
                                map.put("tce_user_current_config", arr[j].split("=")[1]);
                            }

                        }

                    }


                }
                res = JSON.toJSONString(map);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.getConnectionManager().shutdown();
            }
            return res;
        }
    }

    public static String doPostMicroHeader(String url, Map<String, Object> namePairs, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> m : namePairs.entrySet()) {
                pairs.add(new BasicNameValuePair(m.getKey(), m.getValue().toString()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
            httpPost.setEntity(urlEncodedFormEntity);

            HttpResponse response = httpClient.execute(httpPost);

            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = replaceBlank(EntityUtils.toString(resEntity, charset));
                    EntityUtils.consume(resEntity);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != httpClient) {
                httpClient.getConnectionManager().shutdown();
            }
            return result;
        }

    }

    private static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
