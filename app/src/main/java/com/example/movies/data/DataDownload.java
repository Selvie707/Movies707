package com.example.movies.data;

import java.util.ArrayList;

public class DataDownload {
    public static String[][] data = new String[][] {
            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"},

            {"DR. Stone", "2022", "https://i.pinimg.com/736x/ff/3c/e5/ff3ce5c5beaf47f6c571edb3810d4059.jpg"}
    };

    public  static ArrayList<Download> DataDownload() {
        ArrayList<Download> dataDownload = new ArrayList<>();
        for (String[] varData : data) {
            Download rwm = new Download();
            rwm.setTitle(varData[0]);
            rwm.setYear(varData[1]);
            rwm.setPhoto(varData[2]);

            dataDownload.add(rwm);
        }
        return dataDownload;
    }
}
