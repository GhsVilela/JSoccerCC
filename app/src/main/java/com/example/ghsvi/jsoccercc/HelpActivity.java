package com.example.ghsvi.jsoccercc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ghsvi.jsoccercc.email.EmailActivity;

/**
 * Created by ghsvi on 15/01/2018.
 */

public class HelpActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar toolbar;
    public NavigationView mNavigationView;
    public Handler handler;
    public Context context;


    @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        handler = new Handler(Looper.getMainLooper());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Help");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_menu);
        mNavigationView.setNavigationItemSelectedListener(this);

        context = getApplicationContext();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutHelp);
        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);


        WebView mWebView1 = new WebView(context);
        WebView mWebView2 = new WebView(context);
        WebView mWebView3 = new WebView(context);
        WebView mWebView4 = new WebView(context);
        WebView mWebView5 = new WebView(context);
        WebView mWebView6 = new WebView(context);
        WebView mWebView7 = new WebView(context);
        WebView mWebView8 = new WebView(context);

        String text1 = "<html><body>"
                + "<h3><font color=\"#37474F\"><b><p align=\"center\">"
                + getString(R.string.txtWhatItDoes1)
                + "</p></b></font></h4>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView1.loadData(text1, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView1);

        String text2 = "<html><body>"
                + "<font color=\"#37474F\"><p align=\"justify\">"
                + getString(R.string.txtWhatItDoes2)
                + "</p></font>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView2.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        mWebView2.loadData(text2, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView2);

        View view1 = new View(context);
        view1.setLayoutParams(lpView);
        view1.setBackgroundColor(ContextCompat.getColor(view1.getContext(), R.color.DarkGray));

        linearLayout.addView(view1);

        String text3 = "<html><body>"
                + "<font color=\"#37474F\"><p align=\"justify\">"
                + getString(R.string.txtWhatItDoes3)
                + "</p></font>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView3.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        mWebView3.loadData(text3, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView3);

        View view2 = new View(context);
        view2.setLayoutParams(lpView);
        view2.setBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.DarkGray));

        linearLayout.addView(view2);

        String text4 = "<html><body>"
                + "<font color=\"#37474F\"><p align=\"justify\">"
                + getString(R.string.txtWhatItDoes4)
                + "</p></font>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView4.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        mWebView4.loadData(text4, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView4);

        View view3 = new View(context);
        view3.setLayoutParams(lpView);
        view3.setBackgroundColor(ContextCompat.getColor(view3.getContext(), R.color.DarkGray));

        linearLayout.addView(view3);

        String text5 = "<html><body>"
                + "<font color=\"#37474F\"><p align=\"justify\">"
                + getString(R.string.txtWhatItDoes5)
                + "</p></font>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView5.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView5.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        mWebView5.loadData(text5, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView5);

        View view4 = new View(context);
        view4.setLayoutParams(lpView);
        view4.setBackgroundColor(ContextCompat.getColor(view4.getContext(), R.color.DarkGray));

        linearLayout.addView(view4);

        String text6 = "<html><body>"
                + "<font color=\"#37474F\"><p align=\"justify\">"
                + getString(R.string.txtWhatItDoes6)
                + "</p></font>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView6.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView6.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        mWebView6.loadData(text6, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView6);

        View view5 = new View(context);
        view5.setLayoutParams(lpView);
        view5.setBackgroundColor(ContextCompat.getColor(view5.getContext(), R.color.DarkGray));

        linearLayout.addView(view5);

        String text7 = "<html><body>"
                + "<h3><font color=\"#37474F\"><b><p align=\"center\">"
                + getString(R.string.txtHowItWorks1)
                + "</p></b></font><h4>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView7.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView7.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView7.loadData(text7, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView7);

        String text8 = "<html><body>"
                + "<font color=\"#37474F\"><p align=\"justify\">"
                + getString(R.string.txtHowItWorks2)
                + "</p></font>"
                + "</body></html>";

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView8.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mWebView8.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView8.loadData(text8, "text/html;charset=UTF-8", null);

        linearLayout.addView(mWebView8);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reportbugs)
        {
            Intent it = new Intent(HelpActivity.this, EmailActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()){

                    case(R.id.home):
                        Intent it = new Intent(HelpActivity.this, MainActivity.class);
                        startActivity(it);
                        break;

                    case (R.id.search_team):
                        Intent it2 = new Intent(HelpActivity.this, InserirPesquisaTimes.class);
                        startActivity(it2);
                        break;

                    case (R.id.search_all_players):
                        Intent it3 = new Intent(HelpActivity.this, InserirPesquisaAllJogadores.class);
                        startActivity(it3);
                        break;

                    case (R.id.search_player_by_name):
                        Intent it4 = new Intent(HelpActivity.this, InserirPesquisaSingleJogador.class);
                        startActivity(it4);
                        break;
                }
            }
        },400);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
