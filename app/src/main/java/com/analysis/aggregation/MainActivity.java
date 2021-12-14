package com.analysis.aggregation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.analysis.aggregation.util.ErrorReport;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Exit(View view) {
        for (int i = 0; i < 50; i++) {
            ErrorReport.reportError(this, "xxxxx");
//            ReportUtils.reportError(this, new Throwable("test for throwable."));
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
        }
    }
}