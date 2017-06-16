package com.example.computemygrades;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

    private TextView textResult;
    private Button btnClose;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnClose = (Button) findViewById(R.id.btnClose);
        textResult = (TextView) findViewById(R.id.textResult);
        message = getIntent().getStringExtra("resultMsg");
        textResult.setText(message);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Close the view
                finish();
            }
        });
    }
}
