package com.example.processData.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;

@Component
public class Scheduler {
    // 每分鐘執行一次，僅限於每天 8:00 到 17:00 之間
    //  */5   每5分鐘
    @Scheduled(cron = "0 */10 8-17 * * *")
    public void scheduledTask() throws IOException {
        // 任務邏輯

        String urlString = "http://localhost:8080/getopensource";    //呼叫自己
        URL url = new URL(urlString);

        // 發起請求
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        //連線失敗直接報錯
        if (httpURLConnection.getResponseCode() != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + httpURLConnection.getResponseCode());
        }


        // API讀取成功在回應
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder jsonStr = new StringBuilder();
        String lineOutput;

        // 讀取回應資料
        while ((lineOutput = in.readLine()) != null) {
            jsonStr.append(lineOutput);
        }

        System.out.println("Scheduled Task :: Execution Time - " + LocalDateTime.now());
    }
}
