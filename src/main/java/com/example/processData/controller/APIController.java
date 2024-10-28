package com.example.processData.controller;

import com.example.processData.dao.impl.LibraryDaoImpl;
import com.example.processData.dto.API_DTO.LibraryDetail;
import com.example.processData.dto.API_DTO.LibraryList;
import com.example.processData.dto.LibraryWithDetail;
import com.example.processData.entity.BranchInfo;
import com.example.processData.entity.Library;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class APIController {

    @Autowired
    LibraryDaoImpl libraryDao;


    /**
     * 取得指定分館的各區使用狀況
     * @param id
     * @return
     */
    @GetMapping("/getlibrarydetail/{id}")
    public ResponseEntity<List<LibraryDetail>> getLibraryDetail(@PathVariable("id") int id) {
        System.out.println("傳送圖書館資訊給前端開始  /getlibrarydetail");
        List<LibraryDetail> libraryDetails = libraryDao.getLibraryDetail(id);
        System.out.println("傳送圖書館資訊給前端結束  /getlibrarydetail");
        return ResponseEntity.ok() .header("Content-Type", "application/json").body(libraryDetails);
    }


    /**
     * 抓各分館使用率
     * @return
     */
    //ok
    @GetMapping("/getlibrarylist")
    public ResponseEntity<List<LibraryList>> getLibraryList() {
        System.out.println("傳送圖書館資訊給前端開始  /getlibrarylist");
        List<LibraryList> libraryList=null;
        try{
            libraryList = libraryDao.getLibraryList();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("DB異常");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(libraryList);
        }

        System.out.println("傳送圖書館資訊給前端結束  /getlibrarylist");
        return ResponseEntity.ok().header("Content-Type", "application/json").body(libraryList);
    }


    /**
     * 抓最新圖書館資料API
     *
     * @return
     * @throws IOException
     */

    @GetMapping("/getopensource")
    public ResponseEntity<String> getAPISource() throws IOException {
        System.out.println("抓圖書館最新資料開始   ");
        String url = "https://seat.tpml.edu.tw/sm/service/getAllArea";

        //取得網址的json資料
        String jsonStr = getJsonStringFromURL(url);
        if (jsonStr == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("台北市府無法連線");

        }
        //JSON字串轉換成物件
        ArrayList<Library> libraries = jsonToOBJ(jsonStr);

        //插入資料
        int insertAMT = libraryDao.insertLibrInfo(libraries);

        System.out.println("抓圖書館最新資料結束   ");
        return ResponseEntity.ok().body("已抓到" + insertAMT + "筆資料");
    }


    /**
     * 取得各分館資訊(地址/電話等等)
     * @param id
     * @return
     */
    @GetMapping("/getbranchbasicinfo/{id}")
    public ResponseEntity< List<BranchInfo>> getBranchBasicInfo(@PathVariable("id") int id){
        System.out.println("----取得基本資料開始----");
        List<BranchInfo> branchInfos = libraryDao.getBranchBasicInfo(id);
        System.out.println("----取得基本資料結束-----");
        return  ResponseEntity.ok().body(branchInfos);

    }



    /**
     * 處裡json 字串
     *
     * @param jsonStr
     * @return
     */
    public ArrayList<Library> jsonToOBJ(String jsonStr) {
        // 將 JSON 字串解析為 JSONArray
        JSONArray jsonArray = new JSONArray(jsonStr);

//        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        ArrayList<Library> libraries = new ArrayList<>();

        // 遍歷 JSON 陣列
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Library library = new Library();
            // 取得每個對象的資料
            int areaId = jsonObject.getInt("areaId");
            String branchName = jsonObject.getString("branchName");
            String floorName = jsonObject.getString("floorName");
            String areaName = jsonObject.getString("areaName");
            int freeCount = jsonObject.getInt("freeCount");
            int totalCount = jsonObject.getInt("totalCount");

            //填入物件
            library.setAreaId(areaId);
            library.setBranchName(branchName);
            library.setFloorName(floorName);
            library.setAreaName(areaName);
            library.setFreeCount(freeCount);
            library.setTotalCount(totalCount);
//            library.setCurrentTime(currentTime);      //因DB 已預設自動寫入時間，故不再set 時間

            libraries.add(library);
        }
        return libraries;
    }


    public String getJsonStringFromURL(String urlStr) throws IOException {
        String urlString = urlStr;
        URL url = new URL(urlString);

        //確認是否可以上網
//        if (!isInternetAvailable()){
//            System.out.println("Bro! you have suck connection");
//            throw new RuntimeException("internet is interrupted : 網路無法連接");
//        }

        // 發起請求
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        //連線失敗直接報錯
        try {
            if (httpURLConnection.getResponseCode() != 200) {
                System.out.println("Taipei Government's url connectted but wrong");
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Taipei Government's url connectted fail");
            return null;
        }


        // API讀取成功在回應
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder jsonStr = new StringBuilder();
        String lineOutput;

        // 讀取回應資料
        while ((lineOutput = in.readLine()) != null) {
            jsonStr.append(lineOutput);
        }
        in.close();
        System.out.println(jsonStr);
        return jsonStr.toString();
    }


    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(3000); // 試著在 3 秒內連接到 Google 的 DNS
        } catch (IOException e) {
            return false; // 無法連接時回傳 false
        }

    }

}
