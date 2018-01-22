package com.example.ghsvi.jsoccercc.rss;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ghsvi.jsoccercc.InserirPesquisaAllJogadores;
import com.example.ghsvi.jsoccercc.InserirPesquisaSingleJogador;
import com.example.ghsvi.jsoccercc.InserirPesquisaTimes;
import com.example.ghsvi.jsoccercc.MainActivity;
import com.example.ghsvi.jsoccercc.R;
import com.example.ghsvi.jsoccercc.email.EmailActivity;

import java.util.ArrayList;
import java.util.List;

public class RssActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    private NavigationView mNavigationView;
    private Handler handler;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        Toast.makeText(RssActivity.this, "Swipe down to load the news!!", Toast.LENGTH_LONG).show();

        handler = new Handler(Looper.getMainLooper());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FIFA News");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_menu);
        mNavigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.toolbar_viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.toolbar_tabs);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LatestNewsFragment(), "Latest News");
        adapter.addFragment(new WorldCup2018Fragment(), "Russia 2018");
        adapter.addFragment(new WorldCup2022Fragment(), "Qatar 2022");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
            Intent it = new Intent(RssActivity.this, EmailActivity.class);
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
                        Intent it = new Intent(RssActivity.this, MainActivity.class);
                        startActivity(it);
                        break;

                    case (R.id.search_team):
                        Intent it2 = new Intent(RssActivity.this, InserirPesquisaTimes.class);
                        startActivity(it2);
                        break;

                    case (R.id.search_all_players):
                        Intent it3 = new Intent(RssActivity.this, InserirPesquisaAllJogadores.class);
                        startActivity(it3);
                        break;

                    case (R.id.search_player_by_name):
                        Intent it4 = new Intent(RssActivity.this, InserirPesquisaSingleJogador.class);
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
