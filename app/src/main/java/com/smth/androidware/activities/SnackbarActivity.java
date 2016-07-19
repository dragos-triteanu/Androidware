package com.smth.androidware.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smth.androidware.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnackbarActivity extends AppCompatActivity {

    @BindView(R.id.snackbarLayout)
    CoordinatorLayout snackbarLayout;

    @BindView(R.id.showSnackbarBtn)
    Button showSnackbarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        ButterKnife.bind(this);

        showSnackbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar make = Snackbar.make(snackbarLayout, "Internet issue", Snackbar.LENGTH_INDEFINITE);
                make.setAction("DoSmth", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                make.show();
            }
        });


    }
}
