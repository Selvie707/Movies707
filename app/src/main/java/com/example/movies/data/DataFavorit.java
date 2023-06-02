package com.example.movies.data;

import java.util.ArrayList;

public class DataFavorit {
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

    public  static ArrayList<Favorit> DataFavorit() {
        ArrayList<Favorit> dataFavorit = new ArrayList<>();
        for (String[] varData : data) {
            Favorit rwm = new Favorit();
            rwm.setTitle(varData[0]);
            rwm.setYear(varData[1]);
            rwm.setPhoto(varData[2]);

            dataFavorit.add(rwm);
        }
        return dataFavorit;
    }
}