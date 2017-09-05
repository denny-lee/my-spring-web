package com.lee.util;

import com.lee.entity.HeaderDTO;
import com.lee.entity.Resp;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;

public class HttpUtil {

    private static PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private static int maxConnectionPerHost = 15;
    private static int maxTotalConnection =40;

    private static final int CONN_OUT_TIME = 3000 * 1000;
    private static final int SO_OUT_TIME = 300 * 1000;

    private static int HTTPCLIENT_CONNECT_TIMEOUT = 10000;
    private static int HTTPCLIENT_RESPONSE_TIMEOUT = 30000;

    private static final HttpRequestRetryHandler myHttpRequestRetryHandler = new HttpRequestRetryHandler() {

        public boolean retryRequest(
                IOException exception,
                int executionCount,
                HttpContext context) {
            if (executionCount > 1) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // Connection refused
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }

    };

    public static void setPara() {
        manager.setDefaultMaxPerRoute(maxConnectionPerHost);
        manager.setMaxTotal(maxTotalConnection);
    }

    static {
        setPara();
    }

    public static Resp<Integer, String> getGetResponse(String url, List<HeaderDTO> httpHeaders, String requestData, String encode, int timeout)
            throws HttpException, IOException {

        CloseableHttpClient client = null;
        HttpGet get = null;
        int responseCode = 404;
        String resultStr = null;
        try {
            client = HttpClients.custom()
                    .setConnectionManager(manager).setRetryHandler(myHttpRequestRetryHandler)
                    .build();
            get = new HttpGet(url);
            if (httpHeaders != null && !httpHeaders.isEmpty()) {
                for (HeaderDTO header : httpHeaders) {
                    get.addHeader(header.getKey(), header.getValue());
                }
            }
            get.setConfig(setTimeouts());

            ResponseHandler<String> rh = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();
                    if (statusLine.getStatusCode() >= 300) {
                        throw new HttpResponseException(
                                statusLine.getStatusCode(),
                                statusLine.getReasonPhrase());
                    }
                    if (entity == null) {
                        throw new ClientProtocolException("Response contains no content");
                    }
                    ContentType contentType = ContentType.getOrDefault(entity);
                    Charset charset = contentType.getCharset();
                    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
                    String inputLine = null;
                    StringBuffer sb = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    in.close();
                    String str = sb.toString();
                    return str;
                }
            };
            resultStr = client.execute(get, rh);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
//            log.info(sw.toString());
        } finally {
            get.releaseConnection();
        }

        return new Resp(responseCode, resultStr);
    }

    public static Resp<Integer, String> getPostResponse(String url, List<HeaderDTO> httpHeaders, String requestData)
            throws HttpException, IOException {
        CloseableHttpClient client = null;
        HttpPost mtd = null;
        int responseCode = 404;
        String resultStr = null;
        try {
            client = HttpClients.custom()
                    .setConnectionManager(manager).setRetryHandler(myHttpRequestRetryHandler)
                    .build();

            mtd = new HttpPost(url);
            if (httpHeaders != null && !httpHeaders.isEmpty()) {
                for (HeaderDTO header : httpHeaders) {
                    mtd.addHeader(header.getKey(), header.getValue());
                }
            }
            RequestConfig conf = RequestConfig.custom().setConnectTimeout(CONN_OUT_TIME).setSocketTimeout(SO_OUT_TIME).setRedirectsEnabled(false).build();
            mtd.setConfig(conf);
            mtd.setEntity(new StringEntity(requestData, "application/json", "utf-8"));
            ResponseHandler<String> rh = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();
                    if (statusLine.getStatusCode() >= 300) {
                        throw new HttpResponseException(
                                statusLine.getStatusCode(),
                                statusLine.getReasonPhrase());
                    }
                    if (entity == null) {
                        throw new ClientProtocolException("Response contains no content");
                    }
                    ContentType contentType = ContentType.getOrDefault(entity);
                    Charset charset = contentType.getCharset();
                    BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
                    String inputLine = null;
                    StringBuffer sb = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    in.close();
                    String str = sb.toString();
                    return str;
                }
            };
            resultStr = client.execute(mtd, rh);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
//            log.info(sw.toString());
        } finally {
            mtd.releaseConnection();
        }

        return new Resp(responseCode, resultStr);
    }

    private static String converterStringCode(String source, String srcEncode, String destEncode) {
        if (source != null) {
            try {
                return new String(source.getBytes(srcEncode), destEncode);
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    private static RequestConfig setTimeouts() {
        return RequestConfig.custom().setConnectTimeout(CONN_OUT_TIME).setSocketTimeout(SO_OUT_TIME).setRedirectsEnabled(false).build();
    }
}
