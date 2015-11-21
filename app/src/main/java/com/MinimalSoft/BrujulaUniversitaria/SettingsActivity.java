package com.MinimalSoft.BrujulaUniversitaria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;


public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.settings);
        bindPreferenceSummaryToValue(findPreference("notifications_freq"));

        findPreference("pref_logOut").setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener(){
               @Override public boolean onPreferenceClick(Preference preference){

                   confirmDialog();

                   return true;
               }
           }
        );

    }

    private void confirmDialog ()
    {
        new AlertDialog.Builder(this)
                .setTitle("¿Serguro que deseas salir?")
                .setMessage("Cada que alguien nos deja, nuestro  DevTeam llora")
                .setPositiveButton("No me importa", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        SharedPreferences settings = getSharedPreferences("facebook_pref", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("userId", "NA").commit();

                        String id = settings.getString("userId", "NA");

                        FacebookSdk.sdkInitialize(getApplicationContext());
                        LoginManager.getInstance().logOut();

                        Intent intent = new Intent(getApplicationContext(), FBStartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("Evitar que lloren", null).show();
    }

                    /**
                     * Hace el Toolbar bonito si es posible
                     */
                    private void setupActionBar() {
                        ActionBar actionBar = getSupportActionBar();
                        if (actionBar != null) {
                            // Show the Up button in the action bar.
                            actionBar.setDisplayHomeAsUpEnabled(true);
                        }
                    }

                    @Override
                    public boolean onMenuItemSelected(int featureId, MenuItem item) {
                        int id = item.getItemId();
                        if (id == android.R.id.home) {
                            if (!super.onMenuItemSelected(featureId, item)) {
                                NavUtils.navigateUpFromSameTask(this);
                            }
                            return true;
                        }
                        return super.onMenuItemSelected(featureId, item);
                    }


                    /**
                     * Actualiza el Summary para reflejar el nuevo valor
                     */
                    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object value) {
                            String stringValue = value.toString();

                            if (preference instanceof ListPreference) {
                                // For list preferences, look up the correct display value in
                                // the preference's 'entries' list.
                                ListPreference listPreference = (ListPreference) preference;
                                int index = listPreference.findIndexOfValue(stringValue);

                                // Set the summary to reflect the new value.
                                preference.setSummary(
                                        index >= 0
                                                ? listPreference.getEntries()[index]
                                                : null);

                            } else {
                                // For all other preferences, set the summary to the value's
                                // simple string representation.
                                preference.setSummary(stringValue);
                            }
                            return true;
                        }
                    };

                    /**
                     * Binds a preference's summary to its value. More specifically, when the
                     * preference's value is changed, its summary (line of text below the
                     * preference title) is updated to reflect the value. The summary is also
                     * immediately updated upon calling this method. The exact display format is
                     * dependent on the type of preference.
                     *
                     * @see #sBindPreferenceSummaryToValueListener
                     */
                    private static void bindPreferenceSummaryToValue(Preference preference) {
                        // Set the listener to watch for value changes.
                        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

                        // Trigger the listener immediately with the preference's
                        // current value.
                        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                                PreferenceManager
                                        .getDefaultSharedPreferences(preference.getContext())
                                        .getString(preference.getKey(), ""));
                    }

                    /**
                     * This method stops fragment injection in malicious applications.
                     * Make sure to deny any unknown fragments here.
                     */
                    protected boolean isValidFragment(String fragmentName) {
                        return PreferenceFragment.class.getName().equals(fragmentName);
                    }

                }
