package com.example.processData.rowMapper;


import com.example.processData.entity.Library;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LibraryRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Library library=new Library();

        library.setAreaId(rs.getInt("areaId"));
        library.setBranchName(rs.getString("branchName"));
        library.setFloorName(rs.getString("floorName"));
        library.setAreaName(rs.getString("areaName"));
        library.setFreeCount(rs.getInt("freeCount"));
        library.setTotalCount(rs.getInt("totalCount"));
        library.setCreateTime(rs.getTimestamp("createTime"));
        return library;
    }
}
