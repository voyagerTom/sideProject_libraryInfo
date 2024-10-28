package com.example.processData.controller;

import com.example.processData.dao.impl.LibraryDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryAPI {

    @Autowired
    LibraryDaoImpl libraryDao;


    @RequestMapping("/getBranchBasicInfo ")
    public String getBranchBasicInfo(){

        return "0";
    }

}
