package com.example.processData;

import com.example.processData.dao.impl.LibraryDaoImpl;
import com.example.processData.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 臺北市火災起火原因及損失情形時間數列統計資料 資料抓取練習
 * 目標: 資料抓到後，存入DB
 */
@Component
public class App {


    public static void main(String[] args) throws IOException {
        //消防署資料 用BIG5 byCSV
        String urlString = "https://data.taipei/api/frontstage/tpeod/dataset/resource.download?rid=e37dd688-7c81-4458-aa7d-ce13a9091c32";
//        String urlString = "https://data.taipei/api/v1/dataset/e37dd688-7c81-4458-aa7d-ce13a9091c32?scope=resourceAquire";

        URL url = new URL(urlString);

        // 發起請求
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int resCode = httpURLConnection.getResponseCode();

        //資料容器
        ArrayList<Library> freqList = new ArrayList<>();
        ArrayList<String[]> reasonList = new ArrayList<>();
        ArrayList<String[]> timestampList = new ArrayList<>();
        ArrayList<String[]> locationList = new ArrayList<>();
        ArrayList<String[]> lossList = new ArrayList<>();

        // API讀取成功在回應
        if (resCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "BIG5"));
            String inputLine;
            StringBuilder content = new StringBuilder();
            inputLine = in.readLine(); //移除標頭
            // 讀取回應資料
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.replace("-", "0");   //把 "-" 轉乘0
                String[] tmpArray = inputLine.split(",");          //把源自串轉成陣列

//                processFreq(tmpArray, freqList);


                processReason(tmpArray, reasonList);
                processTimestampList(tmpArray, timestampList);
                processLocationList(tmpArray, locationList);
                processLossList(tmpArray, lossList);

                System.out.println(inputLine);
//                dataList.add(inputLine.split(","));
            }
//            frequencyDao.insertFrquency(freqList);
//            插入資料
            in.close();
        }


    }

    private static void processLossList(String[] tmpArray, ArrayList<String[]> lossList) {
        String[]  pk = Arrays.copyOfRange(tmpArray, 0, 1);
        String[]  sex = Arrays.copyOfRange(tmpArray, 41, 43);
        String[]  other = Arrays.copyOfRange(tmpArray, 44, 51);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(pk));
        list.addAll(new ArrayList<>(Arrays.asList(sex)));
        list.addAll(new ArrayList<>(Arrays.asList(other)));
        // 將 ArrayList 轉回陣列
        String[] joinArr = list.toArray(new String[0]);

//        String[]  joinArr = Arrays.copyOfRange(tmpArray, 44, 51);   //取出需要的陣列範圍
        lossList.add(joinArr);
    }

    private static void processLocationList(String[] tmpArray, ArrayList<String[]> locationList) {
        String[]  pk = Arrays.copyOfRange(tmpArray, 0, 1);
        String[] locationStr= Arrays.copyOfRange(tmpArray, 33, 40);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(pk));
        list.addAll(new ArrayList<>(Arrays.asList(locationStr)));

        String[] joinArr = list.toArray(new String[0]);

        locationList.add(joinArr);

    }

    private static void processTimestampList(String[] tmpArray, ArrayList<String[]> timestampList) {
        String[]  pk = Arrays.copyOfRange(tmpArray, 0, 1);
        String[] timeStampStr= Arrays.copyOfRange(tmpArray, 25, 33);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(pk));
        list.addAll(new ArrayList<>(Arrays.asList(timeStampStr)));
        String[]  joinArr = list.toArray(new String[0]);  //取出需要的陣列範圍
        timestampList.add(joinArr);
    }

    private static void processReason(String[] tmpArray, ArrayList<String[]> reasonList) {
        String[]  pk = Arrays.copyOfRange(tmpArray, 0, 1);
        String[]  reasonStr= Arrays.copyOfRange(tmpArray, 4, 26);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(pk));
        list.addAll(new ArrayList<>(Arrays.asList(reasonStr)));

        String[]  joinArr = list.toArray(new String[0]);  //取出需要的陣列範圍
        reasonList.add(joinArr);
    }


    /**
     * 處理 freqList
     *
     * @param freqList
     */
//    public static void processFreq(String[] tmpArray, ArrayList<Frequency> freqList) {
//        String[] joinArr = Arrays.copyOfRange(tmpArray, 0, 4);   //取出需要的陣列範圍
//        Frequency frequency=new Frequency();
//
//         String yr=joinArr[0].replace("年","");
//         int totalFireIncidents=Integer.valueOf(joinArr[1]); // 火災發生次數[次]
//        double dailyFireIncidents=Double.valueOf(joinArr[2]);; // 每日火災發生次數[次]
//         double fireIncidentsPerThousandHouseholds=Double.valueOf(joinArr[3]);; // 每千戶火災發生次數[次]
//        frequency.setYr(yr);
//        frequency.setTotalFireIncidents(totalFireIncidents);
//        frequency.setDailyFireIncidents(dailyFireIncidents);
//        frequency.setFireIncidentsPerThousandHouseholds(fireIncidentsPerThousandHouseholds);
//
//        freqList.add(frequency);
//    }


}
