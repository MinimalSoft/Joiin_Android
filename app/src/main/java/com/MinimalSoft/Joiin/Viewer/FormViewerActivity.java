package com.MinimalSoft.Joiin.Viewer;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.MinimalSoft.Joiin.Preferences.UserFormFragment;
import com.MinimalSoft.Joiin.R;

public class FormViewerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.viewer_toolbar);

        toolbar.setTitle("Formulario de Registro");
        setSupportActionBar(toolbar);
        //toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.viewer_fragmentLayout, new UserFormFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This ID represents the Home or Up button. In the case of this
        // activity, the Up button is shown. For
        // more details, see the Navigation pattern on Android Design:
        //
        // http://developer.android.com/design/patterns/navigation.html#up-vs-back
        //
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}