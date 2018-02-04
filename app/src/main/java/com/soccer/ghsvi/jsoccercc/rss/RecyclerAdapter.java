package com.soccer.ghsvi.jsoccercc.rss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.soccer.ghsvi.jsoccercc.R;

/**
 * Created by ghsvi on 27/12/2017.
 */

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_feed_item, viewGroup, false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final RssDataParser.Item item = mDataSet.get(position);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String url = item.link;

                    Toast.makeText(viewHolder.cardView.getContext(), "Openning: " + mDataSet.get(position).title + " ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    if(url.contains("http")) {
                        intent.setData(Uri.parse(item.link));
                    }
                    else {
                        url = "http://www.fifa.com" + url;
                        intent.setData(Uri.parse(url));
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }
        });

        viewHolder.rssTitle.setText(item.title);
        String s = item.description;
        viewHolder.rssDescription.setText(Html.fromHtml(s));
        viewHolder.rssPubDate.setText(item.pubDate);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private Context mContext;
    private ArrayList<RssDataParser.Item> mDataSet;

    public RecyclerAdapter(Context context, ArrayList<RssDataParser.Item> dataSet){
        this.mContext = context;
        this.mDataSet = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView rssTitle;
        TextView rssDescription;
        TextView rssPubDate;

        public ViewHolder(View view){
                    super(view);
                    cardView = (CardView) view.findViewById(R.id.card_view);
                    rssTitle = (TextView) view.findViewById(R.id.item_title);
                    rssDescription = (TextView) view.findViewById(R.id.item_description);
                    rssPubDate = (TextView) view.findViewById(R.id.item_pub_date);
                }

    }


    public void add(int position, RssDataParser.Item item){
        mDataSet.add(position, item);
        notifyItemChanged(position);
    }

    public void remove(RssDataParser.Item item){

        int position = mDataSet.indexOf(item);
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        mDataSet.clear();
    }

}


