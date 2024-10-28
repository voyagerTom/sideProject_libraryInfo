package com.example.processData.rowMapper;

import com.example.processData.dto.API_DTO.LibraryList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LibraryListRowMapper implements RowMapper<LibraryList> {
    @Override
    public LibraryList mapRow(ResultSet rs, int rowNum) throws SQLException {
        LibraryList ll=new LibraryList();
        ll.setId(rs.getInt("id"));
        ll.setAddress(rs.getString("address"));
        ll.setBranchName(rs.getString("name"));
        ll.setVacancy_Rate((rs.getFloat("rateOfVcancy")));
        return ll;
    }
}
