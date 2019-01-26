package com.digimaster.digipay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ScrollView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bottom Nav
        final AHBottomNavigation bottom_nav = (AHBottomNavigation)findViewById(R.id.bottom_navigation);

        //Tab items
        AHBottomNavigationItem d_board = new AHBottomNavigationItem(R.string.dash_board,R.drawable.home,R.color.colorPrimaryLight);
        AHBottomNavigationItem pay_bill = new AHBottomNavigationItem(R.string.pay_bill,R.drawable.pay,R.color.colorPrimaryLight);
        AHBottomNavigationItem history = new AHBottomNavigationItem(R.string.history,R.drawable.scroll,R.color.colorPrimaryLight);
        AHBottomNavigationItem settings= new AHBottomNavigationItem(R.string.settings,R.drawable.settings,R.color.colorPrimaryLight);

        //Add items to Bottom nav
        bottom_nav.addItem(d_board);
        bottom_nav.addItem(pay_bill);
        bottom_nav.addItem(history);
        bottom_nav.addItem(settings);



        //Set Background color of bottom nav
        bottom_nav.setDefaultBackgroundColor(Color.parseColor("#263238"));
        //bootom nav Colors
        bottom_nav.setTitleTextSizeInSp(12,9);



        //Manage bottom nav Titles
        bottom_nav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottom_nav.setCurrentItem(0);

        //Load Dashboard frag
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Dashboard dashboard = new Dashboard();
        fragmentTransaction.add(R.id.dashboard_body, dashboard);
        fragmentTransaction.commit();


        //Bottom Nav Listeners
        bottom_nav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentTransaction nav_transaction = fragmentManager.beginTransaction();
                if (position==0){
                    nav_transaction.replace(R.id.dashboard_body, dashboard);
                    bottom_nav.setCurrentItem(position);

                }else if (position==1){
                    PayBills payBills = new PayBills();
                    nav_transaction.replace(R.id.dashboard_body,payBills);
                    bottom_nav.setCurrentItem(position);
                }else if (position==2){
                    History history=new History();
                    nav_transaction.replace(R.id.dashboard_body,history);
                    bottom_nav.setCurrentItem(position);
                }else if (position==3){
                    Settings settings = new Settings();
                    nav_transaction.replace(R.id.dashboard_body,settings);
                    bottom_nav.setCurrentItem(position);
                }



                nav_transaction.commit();


                return wasSelected;
            }
        });
        bottom_nav.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });






        //Circular View user profile pic
      // CircularImageView circlular_v = (CircularImageView) findViewById(R.id.circular_v);

        //Setting Border
       //circlular_v.setBorderColor(getResources().getColor(R.color.colorAccent));
        //circlular_v.setBorderWidth(5);

        //Circular v shadow
        //circlular_v.setShadowRadius(10);
        //circlular_v.setShadowColor(Color.parseColor("#E0E0E0"));
        //circlular_v.setBackgroundColor(Color.parseColor("#616161"));
        //circlular_v.setShadowGravity(CircularImageView.ShadowGravity.CENTER);




        //Add Scroll view to Linear Layout
       /* LinearLayoutCompat main = (LinearLayoutCompat)findViewById(R.id.main_body);
        ScrollView scrollView = new ScrollView(this);
        main.setVerticalScrollBarEnabled(true);

        main.addView(scrollView);*/







    }
}
