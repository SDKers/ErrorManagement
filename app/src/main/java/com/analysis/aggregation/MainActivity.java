package com.analysis.aggregation;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.analysis.libreport.ErrorReport;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Exit(View view) {
        for (int i = 0; i < 5; i++) {
            ErrorReport.reportError(this, new Throwable("test for throwable.xxxx"));
            testExceptionUpload();
        }
    }

    private void testExceptionUpload() {
        try {
            int a = 10;
            int b = 0;
            System.out.println(a / b);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorReport.reportError(this,
                    new Throwable("test:====>>>>"
                            + System.currentTimeMillis()
                            + e.getMessage()));
        }
    }

}