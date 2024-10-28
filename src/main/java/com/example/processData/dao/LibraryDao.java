package com.example.processData.dao;


import com.example.processData.dto.API_DTO.LibraryDetail;
import com.example.processData.dto.API_DTO.LibraryList;
import com.example.processData.dto.LibraryWithDetail;
import com.example.processData.entity.BranchInfo;
import com.example.processData.entity.Item;
import com.example.processData.entity.Library;

import java.util.ArrayList;
import java.util.List;

public interface LibraryDao {

    public int insertLibrInfo(ArrayList<Library> frqList);

//    public List<Library> getCurrentSource();


    public List<BranchInfo> getBranchBasicInfo(int id);


    public List<LibraryList> getLibraryList();

    public List<LibraryDetail> getLibraryDetail(int id);

//    public List<LibraryDetail> getlibraryDetail();

    public List<Item> getBranchItems(String branchName);

}
