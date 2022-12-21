package com.yg.shop.server.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.io.FileUtils;

import javax.net.ssl.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类名：OkHttpBo
 * 描述：OKHttp工具类
 * 时间：2020/7/21 12:04
 *
 * @author cloud
 */
public class OkHttpUtil {

    private static final MediaType MEDIA_TYPE_FORM =
            MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    /**
     * 超时时间，单位秒
     */
    public static Integer timeout = 30;//PropUtil.getInteger(baseName, "okHttp.timeout", 30);
    /**
     * 最大线程池数量
     */
    public static Integer maxPoolSize = 200;//PropUtil.getInteger(baseName, "okHttp.pool.maxPoolSize", 200);
    /**
     * 最大等待时间
     */
    public static Integer keepAliveDuration = 5;//PropUtil.getInteger(baseName, "okHttp.pool.keepAliveDuration", 5);

    private OkHttpUtil() {
    }

    private static OkHttpClient okHttpClient = null;

    public static OkHttpUtil getInstance() {
        return Singleton.SINGLETON.getInstance();
    }

    private enum Singleton {
        /**
         * 单例对象
         */
        SINGLETON;

        private OkHttpUtil singleton;

        /**
         * jvm保证这个方法只调用一次
         */
        Singleton() {
            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                    .retryOnConnectionFailure(false)
                    .connectionPool(pool())
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .writeTimeout(timeout, TimeUnit.SECONDS)
                    .hostnameVerifier((String hostname, SSLSession session) -> true)
                    .build();
            singleton = new OkHttpUtil();
        }

        public OkHttpUtil getInstance() {
            return singleton;
        }

        /**
         * Create a new connection pool with tuning parameters appropriate for a single-user application.
         * The tuning parameters in this pool are subject to change in future OkHttp releases. Currently
         */
        private ConnectionPool pool() {
            return new ConnectionPool(maxPoolSize, keepAliveDuration, TimeUnit.SECONDS);
        }

        private X509TrustManager x509TrustManager() {
            return new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        }

        private SSLSocketFactory sslSocketFactory() {
            try {
                //信任任何链接
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
                return sslContext.getSocketFactory();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * post json 访问
     *
     * @param api    api 接口
     * @param params 参数,非空
     * @return
     */
    public String postJson(String api, Object params) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .addHeader("Connection", "close")
                .url(api)
                .post(RequestBody.create(mediaType, JSONObject.toJSONString(params)))
                .build();
        return requestCommon(request);
    }

    /**
     * post json 访问
     *
     * @param api       api 接口
     * @param params    参数,非空
     * @param headerMap 参数,非空
     * @return
     */
    public String postJson(String api, Object params, Map<String, String> headerMap) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request.Builder builder = new Request.Builder();
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                if (headerMap.get(key) == null) {
                    continue;
                }
                builder.addHeader(key, headerMap.get(key));
            }
        }
        builder.addHeader("Connection", "close");
        Request request = builder.url(api).post(RequestBody.create(mediaType, JSONObject.toJSONString(params)))
                .build();
        return requestCommon(request);
    }

    /**
     * post 文件上传
     *
     * @param url     url 接口
     * @param params  参数
     * @param file    文件
     * @param fileKey 文件参数协议key，
     *                比如平常表单发送的文件名 file
     *                那么后台接收参数也叫file
     */
    public String postFile(String url, Map<String, String> params, File file, String fileKey) {
        // "multipart/form-data" 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MEDIA_TYPE_FORM, file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart(fileKey, filename, body);
        }
        if (params != null) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        try {
            return requestCommon(request);
        } finally {
            ConnectionPool connectionPool = okHttpClient.connectionPool();
            connectionPool.evictAll();
        }
    }

    /**
     * post 文件上传
     *
     * @param url      url 接口
     * @param params   参数
     * @param filename 文件名称
     * @param file     文件内容
     * @param fileKey  文件参数协议key，
     *                 比如平常表单发送的文件名 file
     *                 那么后台接收参数也叫file
     */
    public String postFileByte(String url, Map<String, String> params, String filename,
                               byte[] file, String fileKey) {
        // "multipart/form-data" 表单形式上传
        Request request = wrapperFileReq(url, params, filename, file, fileKey);
        try {
            return requestCommon(request);
        } finally {
            ConnectionPool connectionPool = okHttpClient.connectionPool();
            connectionPool.evictAll();
        }
    }

    public byte[] postFileByteAndRetByte(String url, Map<String, String> params, String filename,
                                         byte[] file, String fileKey) {
        Request request = wrapperFileReq(url, params, filename, file, fileKey);
        try {
            Response response;
            try {
                response = okHttpClient.newCall(request).execute();
                ResponseBody body = response.body();
                return body.bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionPool connectionPool = okHttpClient.connectionPool();
            connectionPool.evictAll();
        }
        return null;
    }

    @NotNull
    private Request wrapperFileReq(String url, Map<String, String> params, String filename, byte[] file, String fileKey) {
        // "multipart/form-data" 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MEDIA_TYPE_FORM, file);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart(fileKey, filename, body);
        }
        if (params != null) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return new Request.Builder().url(url).post(requestBody.build()).build();
    }

    /**
     * post 表单 访问
     *
     * @param url    api 接口
     * @param params 参数
     * @return
     */
    public Object post(String url, Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        Request request = new Request.Builder().addHeader("Connection", "close")
                .url(url).post(builder.build()).build();
        return requestCommon(request);
    }

    /**
     * get 访问
     *
     * @param api       api 接口
     * @param params    参数
     * @param headerMap header参数
     * @return
     * @throws RuntimeException
     */
    public String get(String api, Map<String, Object> params,
                      Map<String, String> headerMap) throws RuntimeException {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            if (params != null) {
                for (String key : params.keySet()) {
                    if (params.get(key) == null) {
                        continue;
                    }
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    //对参数进行URLEncoder
                    tempParams.append(String.format("%s=%s", key, params.get(key) != null ?
                            URLEncoder.encode(params.get(key).toString(), "utf-8") : null));
                    pos++;
                }
            }
            String requestUrl = String.format("%s?%s", api, tempParams.toString());
            Request.Builder builder = new Request.Builder();
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    if (headerMap.get(key) == null) {
                        continue;
                    }
                    builder.addHeader(key, headerMap.get(key));
                }
            }
            Request request = builder.addHeader("Connection", "close").url(requestUrl).get().build();
            return requestCommon(request);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件
     *
     * @param url      接口
     * @param params   参数
     * @param path     保存的文件路径
     * @param fileName 文件名
     * @return
     * @throws RuntimeException
     */
    public File downloadFile(String url, Map<String, String> params, String path, String fileName)
            throws RuntimeException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            if (params != null) {
                for (String key : params.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    //对参数进行URLEncoder
                    tempParams.append(String.format("%s=%s", key, params.get(key) != null ? URLEncoder.encode(params.get(key), "utf-8") : null));
                    pos++;
                }
            }
            String requestUrl = String.format("%s?%s", url, tempParams);
            Request request = new Request.Builder().addHeader("Connection", "close").url(requestUrl).get().build();
            File file = new File(path, fileName);
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if (response.isSuccessful()) {
                try (InputStream is = response.body().byteStream()) {
                    FileUtils.copyInputStreamToFile(is, file);
                    Thread.sleep(10);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    body.close();
                    response.close();
                }
            } else {
                int code = response.code();
                response.close();
                throw new RuntimeException("" + code);
            }
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String requestCommon(Request request) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if (response.isSuccessful()) {
                try {
                    String res = body.string();
                    return res;
                } catch (IOException e) {
                    throw new RuntimeException("超时");
                } finally {
                    body.close();
                    response.close();
                }
            } else {
                int code = response.code();
                response.close();
                throw new RuntimeException("运行异常!" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
