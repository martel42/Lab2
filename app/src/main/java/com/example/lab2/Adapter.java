package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab2.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;

public class Adapter extends RecyclerView.Adapter<Adapter.CardViewAdapter>{

    private int itemCounts;
    private Context context;

    public Adapter(int n, Context context) {
        this.itemCounts = n;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_list_view, parent, false);

        return new CardViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter holder, final int position) {

        final int index = position + 1;
        JSONArray data = DataJSON.getInstance().getData();

        TextView text = holder.text;
        try {
            text.setText(data.getJSONObject(index).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ImageView image = holder.image;

        DownloadImageTask load_image_task = new DownloadImageTask(image);
        String base_url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";
        try {
            load_image_task.execute(base_url + data.getJSONObject(index).getString("graphic"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(),
                (index) % 2 == 0 ? R.color.gray : R.color.white);

        holder.linearLayout.setBackgroundColor(backgroundColor);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ViewPagerClass.class);

                intent.putExtra("position", index-1);
                System.out.println(index-1);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCounts;
    }

    class CardViewAdapter extends RecyclerView.ViewHolder {

        public View linearLayout;
        private ImageView image;
        private TextView text;
        private View view;

        public CardViewAdapter(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.my_image);
            text = itemView.findViewById(R.id.my_text);
            linearLayout = itemView.findViewById(R.id.line);
            this.view = itemView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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
                bmImage.setImageBitmap(result);
            }
        }
    }
}
