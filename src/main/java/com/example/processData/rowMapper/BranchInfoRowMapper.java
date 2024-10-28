package com.example.processData.rowMapper;

import com.example.processData.dto.API_DTO.LibraryDetail;
import com.example.processData.entity.BranchInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BranchInfoRowMapper implements RowMapper<BranchInfo> {
    @Override
    public BranchInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        BranchInfo bi =new BranchInfo();
        bi.setId(rs.getInt("id"));
        bi.setAddress(rs.getString("address"));
        bi.setName(rs.getString("name"));
        bi.setPhone(rs.getString("phone"));
        return bi;
    }
}
