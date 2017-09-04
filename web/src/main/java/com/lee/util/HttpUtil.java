package com.lee.util;

import com.lee.entity.HeaderDTO;
import com.lee.entity.Resp;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;

import java.io.*;
import java.util.List;

public class HttpUtil {

    private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
    private static int maxConnectionPerHost = 15;
    private static int maxTotalConnection =40;

    private static final int CONN_OUT_TIME = 3000 * 1000;
    private static final int SO_OUT_TIME = 300 * 1000;

    private static int HTTPCLIENT_CONNECT_TIMEOUT = 10000;
    private static int HTTPCLIENT_RESPONSE_TIMEOUT = 30000;

    public static void setPara(int timeout) {
        manager.getParams().setConnectionTimeout(timeout);
        manager.getParams().setSoTimeout(timeout);
        manager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        manager.getParams().setMaxTotalConnections(maxTotalConnection);

    }

    public static Resp<Integer, String> getGetResponse(String url, List<HeaderDTO> httpHeaders, String requestData, String encode, int timeout)
            throws HttpException, IOException {


        HttpClient client = null;
        GetMethod get = null;
        int responseCode = 404;
        String resultStr = null;
        try {
            client = new HttpClient(manager);
            HttpUtil.setParam(timeout);
            get = new GetMethod(url);
            get.setFollowRedirects(false);
            if (httpHeaders != null && !httpHeaders.isEmpty()) {
                for (HeaderDTO header : httpHeaders) {
                    get.setRequestHeader(header.getKey(), header.getValue());
                }
            }
            get.setFollowRedirects(false);
            get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1,false));
            responseCode = client.executeMethod(get);
            BufferedReader in = new BufferedReader(new InputStreamReader(mtd.getResponseBodyAsStream(), mtd.getResponseCharSet()));
            String inputLine = null;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            resultStr = sb.toString();

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

    public static Resp<Integer, String> getPostResponse(String url, List<HeaderDTO> httpHeaders, String requestData, String encode, int timeout)
            throws HttpException, IOException {



        HttpClient client = null;
        PostMethod mtd = null;
        int responseCode = 404;
        String resultStr = null;
        try {
            client = new HttpClient(manager);
            HttpUtil.setParam(timeout);
            mtd = new PostMethod(url);
            mtd.setFollowRedirects(false);
            if (httpHeaders != null && !httpHeaders.isEmpty()) {
                for (HeaderDTO header : httpHeaders) {
                    mtd.setRequestHeader(header.getKey(), header.getValue());
                }
            }
            mtd.setRequestEntity(new StringRequestEntity(requestData, "application/json", "utf-8"));
            responseCode = client.executeMethod(mtd);
            BufferedReader in = new BufferedReader(new InputStreamReader(mtd.getResponseBodyAsStream(), mtd.getResponseCharSet()));
            String inputLine = null;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            resultStr = sb.toString();

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
        if (source 1= null) {
            try {
                return new String(source.getBytes(srcEncode), destEncode);
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else {
            return "";
        }
    }
}
