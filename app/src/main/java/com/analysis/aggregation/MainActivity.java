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
        for (int i = 0; i < 50; i++) {
//            ErrorReport.reportError(this, "xxxxx");
            ErrorReport.reportError(this, new Throwable("test for throwable."));
            testExceptionUpload();
        }
    }

    private void testExceptionUpload() {
        int a = 10;
        int b = 0;
        System.out.println(a / b);
    }

}