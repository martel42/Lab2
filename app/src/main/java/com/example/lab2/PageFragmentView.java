package com.example.lab2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab2.R;

import org.json.JSONArray;

public class PageFragmentView extends Fragment {

    private RecyclerView recycleview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view =  inflater.inflate(R.layout.list_main, container, false);

        // Получение json в виде строки и преобразование к JSONObject
        DataJSON datajson = DataJSON.getInstance();
        JSONArray jsonArray = datajson.getData();
        System.out.println(jsonArray.toString());

        Context context = getActivity();

        recycleview = view.findViewById(R.id.card);
        Adapter adapter = new Adapter(jsonArray.length()-1, context);
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

}
