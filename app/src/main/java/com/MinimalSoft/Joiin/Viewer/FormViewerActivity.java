package com.MinimalSoft.Joiin.Viewer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.MinimalSoft.Joiin.R;
import com.MinimalSoft.Joiin.Start.RegistrationFragment;

public class FormViewerActivity extends AppCompatActivity {
    public static final String FORM_TYPE_KEY = "ENUM FORM TYPE";

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
        fragmentTransaction.add(R.id.viewer_fragmentLayout, getProperFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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

    private Fragment getProperFragment() {
        switch ((FormType) getIntent().getSerializableExtra(FORM_TYPE_KEY)) {
            case USER_REGISTRATION:
                return new RegistrationFragment();

            //case PLACE_REGISTRATION:
            //  return new PlaceFormFragment();

            default:
                return new Fragment();
        }
    }

    public enum FormType {
        USER_REGISTRATION, PLACE_REGISTRATION, USER_DATA_UPDATE
    }
}