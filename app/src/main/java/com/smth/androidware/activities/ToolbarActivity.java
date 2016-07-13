package com.smth.androidware.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.smth.androidware.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Toolbar illustrative implementation that works on android pre lvl 21.
 * This toolbar also displays the coding style used for providing apps with toolbars.
 */
public class ToolbarActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);

        setupToolbar();


//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//
//        }else{
//
//        }
    }

    /**
     * Setups the toolbar for illustrative purposes in order to contain a search button, the android popup menu
     * and branding details such as logo or title.
     */
    private void setupToolbar() {
        toolbar.setTitle("Thiscounts!");
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.inflateMenu(R.menu.manu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String msg = "";
                switch (item.getItemId()){
                    case R.id.settings:
                        msg = "Settings";
                        break;
                    case R.id.search:
                        msg = "Search";
                        break;
                }

                Toast.makeText(ToolbarActivity.this,msg,Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

}
