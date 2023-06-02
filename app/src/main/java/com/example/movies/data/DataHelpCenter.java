package com.example.movies.data;

import com.example.movies.domain.HelpCenterDomain;

import java.util.ArrayList;

public class DataHelpCenter {
    public static String[][] data = new String[][] {
            {"@drawable/pokemon", "Get Started", "a"},
            {"@drawable/pokemon", "Account", "b"},
            {"@drawable/pokemon", "Movie", "c"},
            {"@drawable/pokemon", "Premium", "d"},
            {"@drawable/pokemon", "Troubleshoot", "e"},
            {"@drawable/pokemon", "Contact Us", "f"}
    };

    public static ArrayList<HelpCenterDomain> DataHelpCenter () {
        ArrayList<HelpCenterDomain> dataHelpCenter = new ArrayList<>();
        for (String[] varData : data) {
            HelpCenterDomain hcd = new HelpCenterDomain();
            hcd.setPic(varData[0]);
            hcd.setTitle(varData[1]);
            hcd.setPos(varData[2]);

            dataHelpCenter.add(hcd);
        }
        return dataHelpCenter;
    }
}
