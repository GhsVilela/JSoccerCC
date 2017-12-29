package com.example.ghsvi.jsoccercc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ghsvi on 29/12/2017.
 */

public class InserirPesquisaAllJogadores extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button pesquisar;
    static EditText allPlayers;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar toolbar;
    public NavigationView mNavigationView;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir_pesquisa_all_jogadores);

        pesquisar = (Button) findViewById(R.id.buttonAllPlayers);
        allPlayers = (EditText) findViewById(R.id.editTextAllPlayers);

        handler = new Handler(Looper.getMainLooper());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search for all players");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_menu);
        mNavigationView.setNavigationItemSelectedListener(this);

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int testConnection = checkConnectivity();

                if(allPlayers.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "This field can't be empty!!", Toast.LENGTH_LONG).show();
                }

                else if(testConnection == 1 && !allPlayers.getText().toString().isEmpty()){
                    Intent launchActivity = new Intent(InserirPesquisaAllJogadores.this, PesquisaAllPlayers.class);
                    startActivity(launchActivity);

                }
            }
        });

    }

    private int checkConnectivity() {
        boolean enabled = true;
        int internet;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            internet = 0;//not connected
            Toast.makeText(getApplicationContext(), "Please connect to the internet first!!", Toast.LENGTH_LONG).show();
            enabled = false;
        } else {
            internet = 1;//connected
        }

        return internet;
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
        if (id == R.id.settings)
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
                        Intent it = new Intent(InserirPesquisaAllJogadores.this, MainActivity.class);
                        startActivity(it);
                        break;

                    case (R.id.search_team):
                        Intent it2 = new Intent(InserirPesquisaAllJogadores.this, InserirPesquisaTimes.class);
                        startActivity(it2);
                        break;

                    case (R.id.search_all_players):
                        Intent it3 = new Intent(InserirPesquisaAllJogadores.this, InserirPesquisaAllJogadores.class);
                        startActivity(it3);
                        break;

                    case (R.id.search_player_by_name):
                        Intent it4 = new Intent(InserirPesquisaAllJogadores.this, InserirPesquisaSingleJogador.class);
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
