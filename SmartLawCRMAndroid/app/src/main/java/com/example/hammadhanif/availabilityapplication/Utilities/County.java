package com.example.hammadhanif.availabilityapplication.Utilities;

import java.util.ArrayList;
import java.util.Collections;

public class County {
    private static ArrayList<com.kmincorp.smartlawcrm.County> arrayList = null;

    public static ArrayList<com.kmincorp.smartlawcrm.County> getArrayList() {
        return County.arrayList;
    }

    public static void setArrayList(ArrayList<com.kmincorp.smartlawcrm.County> arrayList) {
        County.arrayList = arrayList;
        Collections.sort(County.arrayList);
    }
}
