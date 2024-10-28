package com.example.processData.dto.API_DTO;

import com.example.processData.entity.Item;

import java.util.ArrayList;

public class LibraryDetail extends Library_abstract {
    private String branchPhone;
    private ArrayList<Item> Items;

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Item> items) {
        Items = items;
    }
}
