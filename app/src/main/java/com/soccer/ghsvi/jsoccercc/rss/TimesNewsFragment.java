package com.soccer.ghsvi.jsoccercc.rss;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ghsvi on 08/01/2018.
 */

public class TimesNewsFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    RecycleAdapterTimes recyclerAdapter;
    Context mContext;
    private static String rssURL;


    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = getActivity();
        mContext = activity.getApplicationContext();
    }

    public TimesNewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.soccer.ghsvi.jsoccercc.R.layout.fragment_times_news, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.soccer.ghsvi.jsoccercc.R.id.times_news_swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(com.soccer.ghsvi.jsoccercc.R.id.times_news_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerAdapter = new RecycleAdapterTimes(mContext, new ArrayList<RssDataParser.Item>());
        recyclerView.setAdapter(recyclerAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int testConnection = checkConnectivity();

                if(testConnection==1)
                {
                    recyclerAdapter.clear();
                    new TimesNewsFragment.GetNewsFeed().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(RssActivity.getContext(), "You are offline. Check your internet connection!!", Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return view;
    }

    private class GetNewsFeed extends AsyncTask<Void, Void, ArrayList<RssDataParser.Item>> {

        @Override
        protected ArrayList<RssDataParser.Item> doInBackground(Void... voids) {
            try{
                return loadXmlFromNetwork(rssURL);
            }
            catch (XmlPullParserException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RssDataParser.Item> items) {
            if(items!=null)
            {
                Log.d("ArrayList", "Items: " + items.toString());
                for(int i=0; i<items.size(); i++)
                {
                    recyclerAdapter.add(i, items.get(i));
                }
                recyclerAdapter.notifyDataSetChanged();
            }
            else
            {
                Log.e("OnPostExecute", "ArrayList Is Null");
                Snackbar.make(getView(), "The RSS Feed provider isn't availabe at this moment, try again later.", Snackbar.LENGTH_INDEFINITE).show();
            }
        }

    }

    private ArrayList loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream inputStream = null;
        RssDataParser rssDataParser = new RssDataParser();
        ArrayList<RssDataParser.Item> entries = null;

        try {
            inputStream = downloadUrl(urlString);
            entries = rssDataParser.parse(inputStream);
            return entries;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private InputStream downloadUrl(String urlString) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(urlString).build();

        Response response = client.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();

        return inputStream;
    }

    public static String getRssURL() {
        return rssURL;
    }

    public static void setRssURL(String rssURL) {
        TimesNewsFragment.rssURL = rssURL;
    }

    private int checkConnectivity() {
        boolean enabled = true;
        int internet;

        ConnectivityManager connectivityManager = (ConnectivityManager) RssTimesActivity.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            internet = 0;//not connected
            //Toast.makeText(RssTimesActivity.getContext(), "Please connect to the internet first!!", Toast.LENGTH_LONG).show();
            enabled = false;
        } else {
            internet = 1;//connected
        }

        return internet;
    }
}
