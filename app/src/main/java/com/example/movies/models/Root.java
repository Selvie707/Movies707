package com.example.movies.models;

import com.example.movies.models.Result;

import java.util.ArrayList;

public class Root{
    public int page;
    public ArrayList<Result> results;
    public ArrayList<Genre> genres;
    public int total_pages;
    public int total_results;

    public int getPage() {
        return page;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
