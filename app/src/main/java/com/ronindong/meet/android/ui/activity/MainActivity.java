package com.ronindong.meet.android.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ronindong.meet.android.R;
import com.ronindong.meet.android.helper.CxHelper;
import com.ronindong.meet.android.manager.SingletonManager;
import com.ronindong.meet.android.service.MeetAndroidAccessibilityService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView openAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        openAccess = findViewById(R.id.openAccess);
        openAccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openAccess:
                SingletonManager.get(CxHelper.class).openAccessSetting();
                break;
            default:
                break;
        }
    }
}
