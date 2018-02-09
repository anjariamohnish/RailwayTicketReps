package com.example.mohnish.railwayticket.SupportFiles;

/**
 * Created by mohnish on 21-01-2018.
 */

public class station {


    public  String stnName;
    public  String stnCode;

    public station() {
    }

    public station(String stnName, String stnCode) {
        this.stnName = stnName;
        this.stnCode = stnCode;
    }


    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }

    public String getStnCode() {
        return stnCode;
    }

    public void setStnCode(String stnCode) {
        this.stnCode = stnCode;
    }

    @Override
    public String toString() {
        return stnName;
    }
}
