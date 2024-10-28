package com.example.processData.rowMapper;

import com.example.processData.entity.LibraryDetail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LibraryDetailRowMapper implements RowMapper<LibraryDetail> {


    /**
     *  用於裝這串SQL
     *  select  b.id,b.name,b.address ,l.areaName  ,l.freeCount,l.totalCount,max(l.createTime),l.createTime
     * from library l
     * join branchesinfo b
     * on l.branchName =b.name
     * group by l.areaId ;
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public LibraryDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        LibraryDetail ld = new LibraryDetail();
        ld.setBranchId(rs.getInt("id"));
        ld.setBranchName(rs.getString("name"));
        ld.setAddress(rs.getString("address"));
        ld.setAreaName(rs.getString("areaName"));
        ld.setFreeCount(rs.getInt("freeCount"));
        ld.setTotalCount(rs.getInt("totalCount"));
        ld.setCreateTime(rs.getTimestamp("createTime"));
        return ld;
    }
}
