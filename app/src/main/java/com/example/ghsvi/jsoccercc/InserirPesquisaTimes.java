package com.example.ghsvi.jsoccercc;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class InserirPesquisaTimes extends AppCompatActivity {

    Button pesquisar;
    static EditText time;
    public  DrawerLayout drawerLayout;
    public  ActionBarDrawerToggle actionBarDrawerToggle;
    public  Toolbar toolbar;
    public  NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir_pesquisa_times);

        pesquisar = (Button) findViewById(R.id.button);
        time = (EditText) findViewById(R.id.editText);

        toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        toolbar.setTitle("Search for team by name");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutInserir);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_menu_inserir);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case(R.id.search_team):
                        Intent it = new Intent(InserirPesquisaTimes.this, InserirPesquisaTimes.class);
                        startActivity(it);
                }
                switch (menuItem.getItemId()){
                    case(R.id.home):
                        Intent it2 = new Intent(InserirPesquisaTimes.this, MainActivity.class);
                        startActivity(it2);
                }
                return true;
            }
        });

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchActivity = new Intent(InserirPesquisaTimes.this, PesquisaTimes.class);
                startActivity(launchActivity);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
