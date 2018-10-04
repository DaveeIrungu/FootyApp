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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

public class Following extends Fragment {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RssObject object;
    String teamSelected;
    SwipeRefreshLayout swipeRefreshLayout;

    //constructor
    public Following() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        final String[] teams = {"Arsenal", "Chelsea", "Liverpool", "Manchester City", "Manchester United", "Tottenham Hotspur"};

        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, teams);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teamSelected = parent.getItemAtPosition(position).toString();

                switch (teamSelected) {
                    case "Arsenal":
                        getArsenalNews();
                        toolbar.setTitle("Arsenal News");
                        break;
                    case "Chelsea":
                        getChelseaNews();
                        toolbar.setTitle("Chelsea News");
                        break;
                    case "Liverpool":
                        getLiverpoolNews();
                        toolbar.setTitle("Liverpool News");
                        break;
                    case "Manchester City":
                        getManCityNews();
                        toolbar.setTitle("Man City News");
                        break;
                    case "Manchester United":
                        getManUtdNews();
                        toolbar.setTitle("Man Utd News");
                        break;
                    case "Tottenham Hotspur":
                        getSpursNews();
                        toolbar.setTitle("Spurs News");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeView);
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

    private void getArsenalNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);

                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String rss_to_json_api = "https://api.rss2json.com/v1/api.json?rss_url=";
        String rss_link = "http://www.espnfc.com/club/arsenal/359/rss";

        asyncTask.execute(rss_to_json_api + rss_link);
    }

    private void getChelseaNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);

                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String rss_to_json_api = "https://api.rss2json.com/v1/api.json?rss_url=";
        String rss_link = "http://www.espnfc.com/club/chelsea/363/rss";

        asyncTask.execute(rss_to_json_api + rss_link);

    }

    private void getLiverpoolNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);

                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String rss_to_json_api = " https://api.rss2json.com/v1/api.json?rss_url=";
        String rss_link = "http://www.espnfc.com/club/liverpool/364/rss";

        asyncTask.execute(rss_to_json_api + rss_link);
    }

    private void getManCityNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);

                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String rss_to_json_api = " https://api.rss2json.com/v1/api.json?rss_url=";
        String rss_link = "http://www.espnfc.com/club/manchester-city/382/rss";

        asyncTask.execute(rss_to_json_api + rss_link);
    }

    private void getManUtdNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);

                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String rss_to_json_api = " https://api.rss2json.com/v1/api.json?rss_url=";
        String rss_link = "http://www.espnfc.com/club/manchester-united/360/rss";

        asyncTask.execute(rss_to_json_api + rss_link);
    }

    private void getSpursNews() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {
            ProgressDialog dialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Loading, please wait...");
                dialog.setIndeterminate(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                result = httpDataHandler.getHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                object = new Gson().fromJson(s, RssObject.class);

                FeedAdapter feedAdapter = new FeedAdapter(object, getActivity());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        String rss_to_json_api = " https://api.rss2json.com/v1/api.json?rss_url=";
        String rss_link = "http://www.espnfc.com/club/tottenham-hotspur/367/rss";

        asyncTask.execute(rss_to_json_api + rss_link);
    }

}