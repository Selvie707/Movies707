package com.example.movies.domain;

import com.example.movies.adapter.HelpCenterAdapter;
import com.example.movies.data.DataHelpDetail;
import com.example.movies.detail.HelpCenter;

import java.util.ArrayList;

public class HelpCenterDomain {

    private String title, pic;
    private String pos;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
