package com.gmerin.descentmonsters;

import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SettingsActivity extends MonstersPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		// El listener no es necesario registralo aquí, 
		// ya se añade en el método onResume de la clase padre.
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Invocamos al padre
		super.onCreateOptionsMenu(menu);
		
		// Añadimos nuestro menú
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_settings, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menusettings_checkall_expan:
			this.checkAll();
			break;
		case R.id.menusettings_uncheckall_expan:
			this.uncheckAll();
			break;
		}
		return true;
	}
	
    private void checkAll() {
        this.setCheckState(true);
    }

    private void uncheckAll() {
        this.setCheckState(false);
    }
    
    private void setCheckState(Boolean state) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        @SuppressWarnings("unchecked")
        Map<String, Boolean> categories = (Map<String, Boolean>) settings.getAll();
        for (String s : categories.keySet()) 
        {
            Preference pref = findPreference(s);
            if (pref instanceof CheckBoxPreference) 
            {
                CheckBoxPreference check = (CheckBoxPreference) pref;
                // Si la preferencia es de tipo rasgo, la marcamos o desmarcamos
                if (pref.getKey().contains("show_expan_"))
                	check.setChecked(state);
            }
        }

    }
}
