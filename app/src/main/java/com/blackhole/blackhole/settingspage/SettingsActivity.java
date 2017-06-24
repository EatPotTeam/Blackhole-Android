package com.blackhole.blackhole.settingspage;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.blackhole.blackhole.BuildConfig;
import com.blackhole.blackhole.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_settings);

            findPreference(getString(R.string.pk_about)).setSummary(BuildConfig.APP_VERSION);

            if (!BuildConfig.DEBUG) {
                PreferenceGroup preferenceGroup = (PreferenceGroup) findPreference(getString(R.string.pk_category_developer_options));
                if (preferenceGroup != null) {
                    getPreferenceScreen().removePreference(preferenceGroup);
                }
            }
        }
    }
}
