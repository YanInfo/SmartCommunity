package com.yaninfo.smartcommunity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yaninfo.smartcommunity.fragment.PartyFragment1;
import com.yaninfo.smartcommunity.fragment.PartyFragment2;
import com.yaninfo.smartcommunity.fragment.PartyFragment3;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private TextView mTextMessage;

    private int mLastfragment = 0;
    // fragment数组
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
    }

    /**
     * 初始化UI
     */
    private void initView() {
        mToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //加载总体布局
        mDrawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        // 侧滑栏
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // 底部导航栏
        mTextMessage =  findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * 初始化fragment
     */
    private void initFragment()
    {
        PartyFragment1 fragment1 = new PartyFragment1();
        PartyFragment2 fragment2 = new PartyFragment2();
        PartyFragment3 fragment3 = new PartyFragment3();
        mFragments = new Fragment[]{fragment1,fragment2,fragment3};

        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, fragment1).show(fragment1).commit();
    }

    /**
     * BottomNavigationView监听
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    if (mLastfragment != 0) {
                        switchFragment(mLastfragment, 0);
                        mLastfragment = 0;
                    }

                    return true;
                }
                case R.id.navigation_dashboard: {
                    if (mLastfragment != 1) {
                        switchFragment(mLastfragment, 1);
                        mLastfragment = 1;
                    }

                    return true;
                }
                case R.id.navigation_notifications: {
                    if (mLastfragment != 2) {
                        switchFragment(mLastfragment, 2);
                        mLastfragment = 2;
                    }

                    return true;
                }
            }

          return false;
       }
    };

        /**
         * 界面切换,这里是动态加载fragment
         * @param lastfragment
         * @param index
         */
    private void switchFragment(int lastfragment,int index)
        {
           FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
           //隐藏上个Fragment
           transaction.hide(mFragments[lastfragment]);
           if(mFragments[index].isAdded()==false) {
            transaction.add(R.id.mainview, mFragments[index]);
        }
        transaction.show(mFragments[index]).commitAllowingStateLoss();

    }


    @Override
    public void onBackPressed() {
        mDrawer =  findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 左边侧滑栏
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
