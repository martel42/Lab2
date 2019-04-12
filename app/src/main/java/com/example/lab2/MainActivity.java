package com.example.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.lab2.R;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private LoadDataTask loadData;
    private String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData = new LoadDataTask(this);
        loadData.execute(this.url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadData.cancel(true);
    }

    public void Die() {
        Intent intent = new Intent(MainActivity.this, ActivityCreate.class);
        startActivity(intent);
        finish();
    }

    private static class LoadDataTask extends AsyncTask<String, Void, String> {
        final MainActivity listener;
        OkHttpClient client = new OkHttpClient();

        LoadDataTask(MainActivity listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url(String.valueOf(params[0]));
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            DataJSON dataHold = DataJSON.getInstance();

            JSONArray json = null;

            try {
                json = new JSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dataHold.setData(json);

            if (listener != null)
                listener.Die();
        }
    }
}
