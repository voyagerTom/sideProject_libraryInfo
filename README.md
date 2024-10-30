


###專案介紹:
到北市府抓取公開資料，https://seat.tpml.edu.tw/sm/service/getAllArea，並藉由API 取得相關資訊。

【API 說明】
>> GET /getopensource : 抓取json資料，並轉換存到DB，舊資料會被移動到歷史區
>> GET /getbranchbasicinfo/{id} : 依照ID 取得各分館資訊(地址/電話等等)
>> GET /getlibrarylist : 抓各分館使用率
>> GET /getlibrarydetail/{id} : 取得指定分館的各區使用狀況

<分館ID對照表>
1	--> 廣慈分館
2 -->	文山分館
3 --> 稻香分館
4	--> 總館
5	-->	西湖分館


【其他說明】
此專案有設計排程
 // 每分鐘執行一次，僅限於每天 8:00 到 17:00 之間進行資料自動更新
 //  */5   每5分鐘  、 */10   每10分鐘 依此類推
 @Scheduled(cron = "0 */10 8-17 * * *")



【未來展望】
添加會員機制
添加UI介面
