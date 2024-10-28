package com.example.processData.dao.impl;

import com.example.processData.dao.LibraryDao;
import com.example.processData.dto.API_DTO.LibraryDetail;
import com.example.processData.dto.API_DTO.LibraryList;
import com.example.processData.dto.LibraryWithDetail;
import com.example.processData.entity.BranchInfo;
import com.example.processData.entity.Item;
import com.example.processData.entity.Library;
import com.example.processData.rowMapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class LibraryDaoImpl implements LibraryDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    LibraryRowMapper libraryRowMapper;
    @Autowired
    LibraryWithDetailRowMapper libraryWithDetailRowMapper;

    @Autowired
    BranchInfoRowMapper branchInfoRowMapper;

    @Autowired
    LibraryDetailRowMapper libraryDetailRowMapper;

    @Autowired
    LibraryListRowMapper libraryListRowMapper;

    @Autowired
    ItemRowMapper itemRowMapper;


    public LibraryDaoImpl() {
    }

    @Override
    @Transactional  //確保資料都能寫入
    public int insertLibrInfo(ArrayList<Library> libraryList) {
        System.out.println("資料搬移到HIS");
        String sqlForHis = """
                insert into library_his
                select  areaId ,branchName ,floorName ,areaName ,freeCount ,totalCount ,createTime  from library
                 """;

        String sqlForTruncate = "truncate library ;";
        namedParameterJdbcTemplate.update(sqlForHis,new HashMap<>());
        namedParameterJdbcTemplate.update(sqlForTruncate,new HashMap<>());
        System.out.println("資料搬移到HIS_成功");


        String sql = """
                INSERT INTO library
                (areaId, branchName, floorName, areaName, freeCount, totalCount)
                VALUES(:areaId, :branchName, :floorName,:areaName,:freeCount,:totalCount)       
                """;

        // 準備批次參數
        List<MapSqlParameterSource> batchParams = new ArrayList<>();
        for (Library library : libraryList) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("areaId", library.getAreaId());
            params.addValue("branchName", library.getBranchName());
            params.addValue("floorName", library.getFloorName());
            params.addValue("areaName", library.getAreaName());
            params.addValue("freeCount", library.getFreeCount());
            params.addValue("totalCount", library.getTotalCount());
            batchParams.add(params);

        }
        // 執行批次插入
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(sql, batchParams.toArray(new MapSqlParameterSource[0]));
        // 取得插入的資料筆數
        int totalInserted = 0;
        for (int count : updateCounts) {
            totalInserted += count;
        }
        System.out.println("library寫入筆數: "+totalInserted);
        return totalInserted;

    }



    /**
     * 取得讀書館基本資訊
     * @return
     */
    @Override
    public List<BranchInfo> getBranchBasicInfo(int id) {
        String sql = "select b.id,b.address ,b.name,b.phone  from branchesinfo b where id =:id ";
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        map.put("id",id);
        List<BranchInfo> branchInfos = namedParameterJdbcTemplate.query(sql,map,branchInfoRowMapper);
        return branchInfos;
    }


    //OK
    @Override
    public List<LibraryList> getLibraryList() {

        //這串SQL版本不接受
//        String sql = """
//                select b.id ,b.name ,b.address ,(ROUND(sum(freeCount)/sum(totalCount)*100,0)) as rateOfVcancy from library l
//                join branchesinfo b
//                on l.branchName =b.name
//                group by b.name
//                order by b.id
//                """;

        String sql= """
                select b.id ,b.name ,b.address ,(ROUND(sum(freeCount)/sum(totalCount)*100,0)) as rateOfVcancy from library l
                join branchesinfo b
                on l.branchName =b.name
                group by b.name,b.id,b.address
                order by b.id
                """;


        List<LibraryList> libraryList = namedParameterJdbcTemplate.query(sql,libraryListRowMapper);
        return libraryList;
    }

    @Override
    public List<LibraryDetail> getLibraryDetail(int id) {
        List<LibraryDetail> libraryDetails=new ArrayList<>();

        List<BranchInfo> branchInfos=this.getBranchBasicInfo(id);
        List<Item> items;
        for (BranchInfo bi:branchInfos){
            LibraryDetail tmp=new LibraryDetail();
            tmp.setId(bi.getId());
            tmp.setAddress(bi.getAddress());
            tmp.setBranchPhone(bi.getPhone());
            tmp.setBranchName(bi.getName());
            items=this.getBranchItems(bi.getName());
            tmp.setItems((ArrayList<Item>) items);
            libraryDetails.add(tmp);
        }

        return libraryDetails;
    }


    /**
     * 取的單一分館資料細節
     * @param branchName
     * @return
     */
    @Override
    public List<Item> getBranchItems(String branchName) {
        String sql = """
                SELECT l.areaName ,l.floorName ,l.freeCount ,(l.totalCount-l.freeCount) as usedCount,l.totalCount ,  (ROUND((l.freeCount/l.totalCount)*100)) as vacancy_Rate,l.createTime  from library l
                WHERE l.branchName =:branchName;
                """;
        HashMap<String,String >map=new HashMap<>();
        map.put("branchName",branchName);
        List<Item> branchInfos = namedParameterJdbcTemplate.query(sql,map,itemRowMapper);

        return branchInfos;
    }

    //getBranchItems


}
