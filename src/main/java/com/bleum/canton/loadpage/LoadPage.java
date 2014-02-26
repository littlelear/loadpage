package com.bleum.canton.loadpage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

/* <Bleum Inc. Copyright>
 * Copyright (C) 2013 Bleum Inc.
 * All Rights Reserved.  No use, copying or distribution of this
 * work may be made except in accordance with a valid license
 * agreement from Bleum Inc..  This notice must be
 * included on all copies, modifications and derivatives of this
 * work.
 *
 * Bleum Inc. MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Belum Inc. 
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF 
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * </Bleum Inc. Copyright>
 */

public class LoadPage {
    static private Logger LOGGER = Logger.getLogger(LoadPage.class);

    private static final String URL1 = "http://54.249.41.226/shokay/images/world/impact.png";

    private static final String URL2 = "http://54.219.153.165/images/world/impact.png ";

    private static final String URL3 = "http://www.shokay.com/images/world/impact.png";

    private static final String URL4 = "http://d4lm0puflwr0p.cloudfront.net/images/world/impact.png ";

    private static final String CSV_SEPERATOR = ",";

    public void execute(PrintWriter writer) throws FileNotFoundException, UnsupportedEncodingException {

        LOGGER.info("-----------------Start conneting-----------------");
        logLocalIp(writer);
        logIp(URL1, writer);
        logTime(URL1, writer);
        logIp(URL2, writer);
        logTime(URL2, writer);
        logIp(URL3, writer);
        logTime(URL3, writer);
        logIp(URL4, writer);
        logTime(URL4, writer);
        LOGGER.info("-----------------End conneting-----------------");
    }

    private void logLocalIp(PrintWriter writer) {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            LOGGER.info("IP address of localhost is: " + ip);
            ip = getWebIp("http://checkip.amazonaws.com/");
            LOGGER.info("External ip address of " + ip);
            writer.print(ip + CSV_SEPERATOR);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
        }

    }

    private void logTime(String url, PrintWriter writer) {
        StopWatch watch = new StopWatch();

        try {
            watch.start();
            readContentFromGet(url);
        } catch (IllegalStateException e1) {
            // TODO Auto-generated catch block
        } catch (IOException e1) {
            // TODO Auto-generated catch block
        } finally {
            watch.stop();
            long milli=(long)(watch.getNanoTime()/1000);
            LOGGER.info("GET " + url + " : " + milli);
            writer.print(milli + CSV_SEPERATOR);
        }

    }

    private void logIp(String urlStr, PrintWriter writer) {
        URL url;
        InetAddress address = null;
        try {
            url = new URL(urlStr);
            String host = url.getHost();
            address = InetAddress.getByName(host);
            String ip = address.getHostAddress();
            LOGGER.info("Ip address of " + urlStr + " is: " + ip);
            writer.print(urlStr + CSV_SEPERATOR);
            writer.print(ip + CSV_SEPERATOR);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
        }

    }

    private String getWebIp(String strUrl) {
        BufferedReader br = null;
        try {
            URL url = new URL(strUrl);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String webContent = "";
            webContent = br.readLine();
            return webContent;

        } catch (Exception e) {
            return "unknown";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    private void readContentFromGet(String url) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL getUrl = new URL(url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.setUseCaches(false);
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((reader.readLine()) != null) {
                // System.out.println(lines);
            }
        } finally {
            IOUtils.closeQuietly(reader);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
