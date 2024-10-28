package com.example.processData.dto;

import java.sql.Timestamp;


/**
 * 用於傳給前端的容器
 */
public class LibraryWithDetail {

    private String branchName;      // 分館位置
    private String branchLocation;      // 分館名稱
    private String branchPhone;      // 分館電話
    private String floorName;       // 樓層名稱
    private String areaName;        // 區域名稱
    private int freeCount;          // 剩餘座位數
    private int usedCount;          // 已使用座位數
    private int totalCount;         // 總座位數
    private String vacancy_Rate;         // 空位率
    private Timestamp createTime;  // 創建時間


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getVacancy_Rate() {
        return vacancy_Rate;
    }

    public void setVacancy_Rate(String vacancy_Rate) {
        this.vacancy_Rate = vacancy_Rate;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
