package com.example.ghsvi.jsoccercc.rss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghsvi.jsoccercc.R;

import java.util.ArrayList;

/**
 * Created by ghsvi on 08/01/2018.
 */

public class RecycleAdapterTimes extends  RecyclerView.Adapter<RecycleAdapterTimes.ViewHolder>{

    @Override
    public RecycleAdapterTimes.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_feed_item, viewGroup, false);
        RecycleAdapterTimes.ViewHolder viewHolder = new RecycleAdapterTimes.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterTimes.ViewHolder viewHolder, final int position) {
        final RssDataParser.Item item = mDataSet.get(position);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = item.link;
                String linkName = mDataSet.get(position).title;
                Spanned linkNameFinal = Html.fromHtml(linkName);

                Toast.makeText(viewHolder.cardView.getContext(), "Openning: " + linkNameFinal + " ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.link));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });
        String title = item.title;
        viewHolder.rssTitle.setText(Html.fromHtml(title));
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

    public RecycleAdapterTimes(Context context, ArrayList<RssDataParser.Item> dataSet){
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
