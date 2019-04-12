package com.example.lab2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.lab2.R;

import org.json.JSONArray;
import org.json.JSONException;

public class ViewPagerClass extends FragmentActivity {
    ViewPager viewpager;
    PagerAdapter pagerAdapter;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_view_fragment);

        DataJSON dataHold = DataJSON.getInstance();
        JSONArray jsonArray = dataHold.getData();

        this.jsonArray = jsonArray;

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this.jsonArray);
        viewpager = findViewById(R.id.view_pager);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem((int) getIntent().getSerializableExtra("position"));


    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private JSONArray jsonArray;

        public MyFragmentPagerAdapter(FragmentManager fm, JSONArray jsonArray) {
            super(fm);
            this.jsonArray = jsonArray;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Header";
        }

        @Override
        public Fragment getItem(int position) {
            position++;
            String graphic = "";
            String helptext = "Not help text!";
            try {
                graphic = jsonArray.getJSONObject(position).getString("graphic");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                helptext = jsonArray.getJSONObject(position).getString("helptext");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new Fragments(graphic, helptext);
        }

        @Override
        public int getCount() {
            return jsonArray.length();
        }
    }
}
