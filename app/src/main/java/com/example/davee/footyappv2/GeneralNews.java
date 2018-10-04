package com.example.davee.footyappv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.gson.Gson;

public class GeneralNews extends Fragment {
    RecyclerView recyclerView;
    RssObject object;
    SwipeRefreshLayout swipeRefreshLayout;

    public GeneralNews() {
    }

    @Nullable
        @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generalnews, container, false);

        loadNews();

        //setLayoutmanager - how it appears on the screen
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 4000);
            }
        });

        return view;
    }

    private void loadNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false); //user cannot interact with activity until dialog stops
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //used to make the window fullscreen
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();//fetches data from website
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);
                // Google Gson is a Java library used to convert data from a Json string to Java Objects and vice versa

                //FeedAdapter is used to put the "news" items in the recyclerview object
                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();     // this method is called when data has been modified and to show the new data items
            }
        };

        String rss_to_json_api = "https://api.rss2json.com/v1/api.json?rss_url=";   // RSS link converted to Json
        String rss_link = "http://www.independent.co.uk/sport/football/rss";    // RSS link

        asyncTask.execute(rss_to_json_api + rss_link);  // calling AsyncTask with the links provided
    }

}