package com.example.processData.rowMapper;

import com.example.processData.dto.LibraryWithDetail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LibraryWithDetailRowMapper implements RowMapper{
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        LibraryWithDetail libraryWithDetail = new LibraryWithDetail();
        libraryWithDetail.setBranchName(rs.getString("branchName"));
        libraryWithDetail.setBranchLocation(rs.getString("address"));
        libraryWithDetail.setBranchPhone(rs.getString("phone"));
        libraryWithDetail.setAreaName(rs.getString("areaName"));
        libraryWithDetail.setFloorName(rs.getString("floorName"));
        libraryWithDetail.setFreeCount(rs.getInt("freeCount"));
        libraryWithDetail.setTotalCount(rs.getInt("totalCount"));
        libraryWithDetail.setUsedCount(libraryWithDetail.getTotalCount()-libraryWithDetail.getFreeCount());
        libraryWithDetail.setVacancy_Rate(getVacancy_Rate(libraryWithDetail.getFreeCount(),libraryWithDetail.getTotalCount()));  //百分比
        libraryWithDetail.setCreateTime(rs.getTimestamp("createTime"));
        return libraryWithDetail;
    }

    public String getVacancy_Rate(int freeCount,int totalCount ){
        double dfreeCount=freeCount;
        double dtotalCount=totalCount;
        double value= (dfreeCount/dtotalCount)*100;
        String rate=String.format("%.2f", value)+"%";

        return rate;

    }
}
