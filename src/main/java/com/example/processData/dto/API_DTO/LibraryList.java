package com.example.processData.dto.API_DTO;

public class LibraryList extends Library_abstract {
    private double  vacancy_Rate;

    public double getVacancy_Rate() {
        return vacancy_Rate;
    }

    public void setVacancy_Rate(double vacancy_Rate) {
        this.vacancy_Rate = vacancy_Rate;
    }
}
