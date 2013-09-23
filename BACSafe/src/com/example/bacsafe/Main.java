package com.example.bacsafe;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class Main extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        TabHost tabHost=getTabHost();
        tabHost.setup();

        //Tab - Drink Counter
        TabSpec spec1=tabHost.newTabSpec("Drink Counter");
        spec1.setIndicator("Drink Counter");
        spec1.setContent(R.id.tabDrinkCounter);
     
        //Tab - Night Group
        TabSpec spec2=tabHost.newTabSpec("Groups");
        spec2.setIndicator("Night Group");
        spec2.setContent(R.id.tabNightGroup);

        //Tab - Friends List
        TabSpec spec3=tabHost.newTabSpec("Friends List");
        spec3.setIndicator("Friends List");
        spec3.setContent(R.id.tabFriendsList);

       
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
    }
}
