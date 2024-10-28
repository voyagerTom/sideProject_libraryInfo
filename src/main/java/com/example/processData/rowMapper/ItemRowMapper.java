package com.example.processData.rowMapper;

import com.example.processData.entity.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ItemRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setAreaName(rs.getString("areaName"));
        item.setFloorName(rs.getString("floorName"));
        item.setFreeCount(rs.getInt("freeCount"));
        item.setTotalCount(rs.getInt("totalCount"));
        item.setUsedCount(rs.getInt("usedCount"));
        item.setVacancyRate(rs.getString("vacancy_Rate") + "%");
        item.setCreateTime(timeFotmat(rs.getTimestamp("createTime")));
        return item;
    }

    public String timeFotmat(Timestamp createTime) {
        LocalDateTime now = createTime.toLocalDateTime();
        // 定義格式化模式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化時間
        String formattedDate = now.format(formatter);

//        // 輸出結果
//        System.out.println(formattedDate); // Output: 2024-09-04 14:30:05
        return formattedDate;
    }
}
