package com.bleum.canton.loadpage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class LoadPageRunner {

    private static final long TIME_INTERVAL = 300000;
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException,
            UnsupportedEncodingException {
        LoadPage lp = new LoadPage();
        PrintWriter writer = new PrintWriter(new FileOutputStream("load.csv", true));
        writer.print("Timestamp,");
        writer.print("Local external ip,");
        writer.print("URL1,");
        writer.print("URL1 ip,");
        writer.print("URL1 Response time,");
        writer.print("URL2,");
        writer.print("URL2 ip,");
        writer.print("URL2 Response time,");
        writer.print("URL3,");
        writer.print("URL3 ip,");
        writer.print("URL3 Response time,");
        writer.print("URL4,");
        writer.print("URL4 ip,");
        writer.print("URL4 Response time,");
        writer.println();

        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS E");
        while (true) {
            writer.print(dateformat1.format(new Date()) + ",");
            lp.execute(writer);
            writer.println();
            writer.flush();
            System.out.println("Wait 5 minutes...");
            Thread.sleep(TIME_INTERVAL);
        }

    }
}
