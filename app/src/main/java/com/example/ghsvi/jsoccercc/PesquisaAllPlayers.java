package com.example.ghsvi.jsoccercc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

/**
 * Created by ghsvi on 29/12/2017.
 */

public class PesquisaAllPlayers extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private static Context mContext;
    private static LinearLayout linearLayout;
    private static ProgressDialog mProgressBar;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar toolbar;
    public NavigationView mNavigationView;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa_all_jogadores);

        handler = new Handler(Looper.getMainLooper());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Results for " +  InserirPesquisaAllJogadores.allPlayers.getText());
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_menu);
        mNavigationView.setNavigationItemSelectedListener(this);


        mContext = getApplicationContext();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);
        // click = (Button) findViewById(R.id.button);

        mProgressBar = new ProgressDialog(PesquisaAllPlayers.this);
        mProgressBar.setCancelable(false);
        mProgressBar.setTitle("Loading Data...");
        mProgressBar.setMessage("Initializing API");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mProgressBar.show();

        OrganizarDataAllPlayers process = new OrganizarDataAllPlayers();
        process.execute();


    }


    public static Context getContext() {
        return mContext;
    }

    public static LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public static ProgressDialog getmProgressBar() {
        return mProgressBar;
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
            return true;
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
                        Intent it = new Intent(PesquisaAllPlayers.this, MainActivity.class);
                        startActivity(it);
                        break;

                    case (R.id.search_team):
                        Intent it2 = new Intent(PesquisaAllPlayers.this, InserirPesquisaTimes.class);
                        startActivity(it2);
                        break;

                    case (R.id.search_all_players):
                        Intent it3 = new Intent(PesquisaAllPlayers.this, InserirPesquisaAllJogadores.class);
                        startActivity(it3);
                        break;

                    case (R.id.search_player_by_name):
                        Intent it4 = new Intent(PesquisaAllPlayers.this, InserirPesquisaSingleJogador.class);
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
