package com.mcc.schoolmarket;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class AboutSchoolMarketActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school_market);
        findViewById(R.id.backIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
