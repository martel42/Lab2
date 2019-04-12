package com.example.lab2;

import org.json.JSONArray;

public class DataJSON {

    private static volatile DataJSON instance;
    private JSONArray datajson;

    public static DataJSON getInstance() {
        DataJSON localInstance = instance;

        if (localInstance == null) {
            instance = localInstance = new DataJSON();
        }

        return localInstance;
    }

    public void setData(JSONArray datajson){
        this.datajson = datajson;
    }

    public JSONArray getData(){
        return this.datajson;
    }

}
