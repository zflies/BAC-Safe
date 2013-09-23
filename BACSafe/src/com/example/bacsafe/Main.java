package com.example.bacsafe;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class Main extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TabHost tabHost=getTabHost();
        tabHost.setup();

        TabSpec spec1=tabHost.newTabSpec("Drink Counter");
        spec1.setContent(R.id.tabDrinkCounter);
        spec1.setIndicator("Drink Counter");

        TabSpec spec2=tabHost.newTabSpec("Night Group");
        spec2.setIndicator("Night Group");
        spec2.setContent(R.id.tab2);

        TabSpec spec3=tabHost.newTabSpec("Friends List");
        spec3.setIndicator("Friends List");
        spec3.setContent(R.id.tab3);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
    }


}
