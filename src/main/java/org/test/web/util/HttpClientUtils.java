package org.test.web.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.web.exception.HttpResponseNotOkException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * HttpClientUtils
 */
public class HttpClientUtils {

    /**
     * 3分钟
     */
    public static final int MINUTE_FIVE = 180000;
    /**
     * 3分钟
     */
    public static final int MINUTE_TEN = 180000;
    /**
     * 日志工具
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    /**
     * HttpClient
     */
    private static final HttpClient client = getInstance();


    /**
     * Get方法查询
     */
    public static String doGet(String address) throws Exception {
        return doGet(address, getDefaultTimeOutConfig());
    }

    /**
     * 自定义超时的Get方法查询
     */
    public static String doGet(String address, int connectionTimeout, int socketTimeout) throws Exception {
        return doGet(address, getTimeOutConfig(socketTimeout, connectionTimeout));
    }

    /**
     * HttpClient Get方法请求数据
     */
    public static String doGet(String address, RequestConfig config) throws Exception {
        byte[] result = get(address, config);
        return new String(result, "utf-8");
    }

    /**
     * HttpClient Get方法请求数据
     */
    public static String doGet_GBK(String address) throws Exception {
        byte[] result = get(address, getDefaultTimeOutConfig());
        return new String(result, "gbk");
    }

    /**
     * get header
     *
     * @param address
     * @param headerMap
     * @return
     * @throws Exception
     */
    public static String doGetWithHeader(String address, Map<String, String> headerMap) throws Exception {
        RequestConfig config = getDefaultTimeOutConfig();
        return doGet(address, config, headerMap);
    }

    /**
     * Post方法查询
     */
    public static String doPost(String address, HttpEntity paramEntity) throws Exception {
        RequestConfig config = getDefaultTimeOutConfig();
        return doPost(address, paramEntity, config);
    }

    /**
     * Post方法查询
     */
    public static String doPostWithHeader(String address, HttpEntity paramEntity, Map<String, String> headerMap) throws Exception {
        RequestConfig config = getDefaultTimeOutConfig();
        return doPostWithHeader(address, paramEntity, config, headerMap);
    }

    /**
     * 自定义超时的Post方法
     */
    public static String doPost(String address, HttpEntity paramEntity, int connectionTimeout, int socketTimeout) throws Exception {
        RequestConfig config = getTimeOutConfig(socketTimeout, connectionTimeout);
        return doPost(address, paramEntity, config);
    }

    /**
     * HttpClient Post方法请求数据
     */
    public static String doPost(String address, HttpEntity paramEntity, RequestConfig config) throws Exception {
        logger.info("Start Access Url(" + address + ") By Post");
        byte[] content = post(address, paramEntity, config);
        return new String(content, "utf-8");
    }

    /**
     * HttpClient Post方法请求数据
     */
    public static String doPostWithHeader(String address, HttpEntity paramEntity, RequestConfig config, Map<String, String> headerMap) throws Exception {
        logger.info("Start Access Url(" + address + ") By Post");
        byte[] content = postWithHeader(address, paramEntity, config, headerMap);
        return new String(content, "utf-8");
    }

    /**
     * 对外发送post数据
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！", e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 转化返回为byte数组
     *
     * @param entity
     * @return byte[]
     * @throws Exception
     */
    public static byte[] convertEntityToBytes(HttpEntity entity) throws Exception {
        InputStream inputStream = null;
        try {
            /*if (entity == null || entity.getContent() == null || entity.getContent().available() == 0) {
                throw new RuntimeException("Response Entity Is null");
            }*/
            if (entity == null || entity.getContent() == null) {
                throw new RuntimeException("Response Entity Is null");
            }
            inputStream = entity.getContent();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            return out.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * http post json
     */
    public static String doPostWithJsonData(String strURL, String params) throws Exception {
        OutputStreamWriter out = null;
        InputStream in = null;

        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();

            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

            out.append(params);
            out.flush();

            // 读取响应
            int length = connection.getContentLength();// 获取长度
            in = connection.getInputStream();

            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = in.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }
        } catch (IOException e) {
            logger.error("发生异常：{}", e.getMessage(), e);
            throw new Exception("发生异常：" + e.getMessage());
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(out);
            org.apache.commons.io.IOUtils.closeQuietly(in);
        }

        return "error"; // 自定义错误信息
    }

    /**
     * 获取ip网段中的所有ip
     *
     * @param ip 账户表中的ip白名单字段，支持单个、多个或网段，如：0.0.0.0或0.0.0.0-10或0.0.0.0|0.0.0.3
     * @return ip的集合
     * @throws Exception 程序报错
     */
    public static HashSet<String> getIpSet(String ip) throws Exception {

        try {
            HashSet<String> ipSet = new HashSet<String>();

            //解析多个ip
            if (ip.contains("|")) {
                String[] ips = ip.split("\\|");
                for (int i = 0; i < ips.length; i++) {
                    ipSet.addAll(parseIp(ips[i]));
                }
            } else {
                ipSet.addAll(parseIp(ip));
            }
            return ipSet;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取ip异常");
        }
    }

    /**
     * 获取ip
     */
    public static String getIp(HttpServletRequest servletReques) {
        String ip = "";
        if (servletReques != null) {
            ip = servletReques.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = servletReques.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = servletReques.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = servletReques.getRemoteAddr();
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = servletReques.getHeader("http_client_ip");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = servletReques.getHeader("HTTP_X_FORWARDED_FOR");
            }
            // 如果是多级代理，那么取第一个ip为客户ip
            if (ip != null && ip.indexOf(",") != -1) {
                ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
            }
            if (ip.indexOf("0:") != -1) {
                ip = "127.0.0.1";
            }
        }
        return ip;
    }

    /**
     * 让Httpclient支持https
     *
     * @return HttpClient
     */
    private static HttpClient getInstance() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("error to init httpclient", e);
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(800);// 客户端总并行链接最大数
        connManager.setDefaultMaxPerRoute(400); // 每个主机的最大并行链接数
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(connManager);
        httpClientBuilder.setSSLSocketFactory(sslsf);
        return httpClientBuilder.build();
    }

    private static final RequestConfig getDefaultTimeOutConfig() {
        return getTimeOutConfig(20000, 5000);
    }

    private static final RequestConfig getTimeOutConfig(int socketTimeout, int connectionTimeout) {
        return RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).build();
    }


    private static byte[] get(String address, RequestConfig config) throws Exception {
        HttpGet get = new HttpGet(address);
        try {
            logger.info("Start Access Address(" + address + ") With Get Request");
            get.setConfig(config);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                int code = response.getStatusLine().getStatusCode();
                //throw new RuntimeException("HttpGet Access Fail , Return Code(" + code + ")");
                throw new HttpResponseNotOkException(String.valueOf(code), "HttpPost Request Access Fail Return Code(" + code + ")");
            }
            return convertEntityToBytes(response.getEntity());
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
        }
    }

    private static byte[] post(String address, HttpEntity paramEntity, RequestConfig config) throws Exception {
        HttpPost post = new HttpPost(address);
        try {
            if (paramEntity != null) {
                post.setEntity(paramEntity);
            }
            post.setConfig(config);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                int code = response.getStatusLine().getStatusCode();
                //throw new RuntimeException("HttpPost Request Access Fail Return Code(" + code + ")");
                throw new HttpResponseNotOkException(String.valueOf(code), "HttpPost Request Access Fail Return Code(" + code + ")");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new RuntimeException("HttpPost Request Access Fail Response Entity Is null");
            }
            return convertEntityToBytes(entity);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    private static byte[] postWithHeader(String address, HttpEntity paramEntity, RequestConfig config, Map<String, String> headerMap) throws Exception {
        HttpPost post = new HttpPost(address);
        try {
            if (paramEntity != null) {
                post.setEntity(paramEntity);
            }
            post.setConfig(config);
            if (headerMap != null && headerMap.size() > 0) {
                Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
                Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> mapEntry = iterator.next();
                    post.addHeader(mapEntry.getKey(), mapEntry.getValue());
                }
            }
            HttpResponse response = client.execute(post);
            logSomeHeaders(response);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                int code = response.getStatusLine().getStatusCode();
                throw new HttpResponseNotOkException(String.valueOf(code), "HttpPost Request Access Fail Return Code(" + code + ")");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new RuntimeException("HttpPost Request Access Fail Response Entity Is null");
            }
            return convertEntityToBytes(entity);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    private static String doGet(String address, RequestConfig config, Map<String, String> headerMap) throws Exception {
        logger.info("Start Access Url(" + address + ") By Get");
        byte[] content = get(address, config, headerMap);
        return new String(content, "utf-8");
    }

    private static byte[] get(String address, RequestConfig config, Map<String, String> headerMap) throws Exception {
        HttpGet get = new HttpGet(address);
        try {
            get.setConfig(config);
            if (headerMap != null && headerMap.size() > 0) {
                Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
                Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> mapEntry = iterator.next();
                    get.addHeader(mapEntry.getKey(), mapEntry.getValue());
                }
            }
            HttpResponse response = client.execute(get);
            logSomeHeaders(response);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String code = String.valueOf(response.getStatusLine().getStatusCode());
                throw new HttpResponseNotOkException(String.valueOf(code), "HttpPost Request Access Fail Return Code(" + code + ")");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new RuntimeException("HttpGet Request Access Fail Response Entity Is null");
            }
            return convertEntityToBytes(entity);
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
        }
    }


    private static HashSet<String> parseIp(String ip) throws Exception {
        HashSet<String> ipSet = new HashSet<String>();
        String end = ip.substring(ip.lastIndexOf(".") + 1, ip.length());
        if (!end.contains("-")) {
            ipSet.add(ip);
        } else {
            String[] ipArray = end.split("-");
            if (Integer.parseInt(ipArray[1]) - Integer.parseInt(ipArray[0]) < 0) {
                throw new Exception("ip网段数据异常");
            } else {
                for (int i = Integer.parseInt(ipArray[0]); i <= Integer.parseInt(ipArray[1]); i++) {
                    String ipStr = ip.substring(0, ip.lastIndexOf(".") + 1) + i;
                    ipSet.add(ipStr);
                }
            }
        }
        return ipSet;
    }


    private static void logSomeHeaders(HttpResponse response) {
        if (isNull(response)) {
            return;
        }

        Header[] remainingLimit = response.getHeaders("x-ratelimit-remaining"); //限流剩余
        Header[] totalLimit = response.getHeaders("x-ratelimit-total"); //限流总计

        logHeader(remainingLimit);
        logHeader(totalLimit);
    }

    private static void logHeader(Header... headers) {
        if (isNull(headers)) {
            return;
        }

        for (Header header : headers) {
            if (nonNull(header)) {
                logger.info(">>>header name={}, value={}", header.getName(), header.getValue());
            }
        }
    }
}
