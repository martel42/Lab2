package com.example.lab2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab2.R;

import java.io.InputStream;

public class Fragments extends Fragment {
    String graphic;
    String helptext;

    public Fragments() {}

    @SuppressLint("ValidFragment")
    public Fragments(String graphic, String helptext) {
        Bundle arguments = new Bundle();
        this.graphic = graphic;
        this.helptext = helptext;
        arguments.putString("helptext", helptext);
        arguments.putString("graphic", graphic);
        this.setArguments(arguments);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_view_fragment_item, container, false);

        if(getArguments().getString("graphic") != null)
            graphic = getArguments().getString("graphic");
        if(getArguments().getString("helptext") != null)
            helptext = getArguments().getString("helptext");

        TextView desc = view.findViewById(R.id.hepltext);

        desc.setText(helptext);

        if(!graphic.equals("")) {
            ImageView image = view.findViewById(R.id.graphic);
            DownloadImage load_image_task = new DownloadImage(image);
            String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";
            load_image_task.execute(url + graphic);
        }

        return view;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}