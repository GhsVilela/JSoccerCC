package com.example.ghsvi.jsoccercc.rss;

import android.app.Activity;
import android.content.Context;
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

import com.example.ghsvi.jsoccercc.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestNewsFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerAdapter recyclerAdapter;
    Context mContext;

    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = getActivity();
        mContext = activity.getApplicationContext();
    }

    public LatestNewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_news, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.latest_news_swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.latest_news_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(mContext, new ArrayList<RssDataParser.Item>());
        recyclerView.setAdapter(recyclerAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerAdapter.clear();
                new GetNewsFeed().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private class GetNewsFeed extends AsyncTask<Void, Void, ArrayList<RssDataParser.Item>> {

        @Override
        protected ArrayList<RssDataParser.Item> doInBackground(Void... voids) {
            try{
                return loadXmlFromNetwork("http://www.fifa.com/rss/index.xml");
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
                Snackbar.make(getView(), "No Connection Was Made. Check Your Internet Connection!!", Snackbar.LENGTH_LONG).show();
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

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        HttpURLConnection.setFollowRedirects(false);
        connection.setConnectTimeout(15 * 1000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
        connection.setRequestProperty("Accept","*/*");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        return inputStream;
    }



}
