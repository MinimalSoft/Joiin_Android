package com.MinimalSoft.BUniversitaria.Transport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.MinimalSoft.BUniversitaria.R;

import java.util.List;

public class Routes extends AppCompatActivity {

    Bundle bundle;
    String titulo,agency;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_routes);
        setToolbar();

        this.bundle = getIntent().getExtras();
        this.titulo = bundle.getString("Titulo");
        this.agency = null;

        switch (this.titulo)
        {
            case "Metro":
                this.agency = "METRO";
                break;
            case "Metrobus":
                this.agency = "MB";
                break;
            case "Tren Ligero":
                this.agency = "TL";
                break;
            case "Trolebus":
                this.agency = "TB";
                break;
            case "Suburbano":
                this.agency = "SUB";
                break;
            case "Ecobici":
                this.agency = "ECO";
                break;
        }

        String sentence = "SELECT route, routeName FROM stops WHERE agency = '"+ agency + "' GROUP BY routeName order by length(route), route ASC";

        ListView listView;
        listView = (ListView) findViewById(R.id.listView);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<String> routes = databaseAccess.getRoutes(sentence);
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_route, routes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                bundle.putString("Route", position+1+"");
                intent = new Intent(getApplicationContext(), Map.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }


}
