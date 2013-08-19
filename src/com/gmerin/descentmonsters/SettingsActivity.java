package com.gmerin.descentmonsters;

import android.os.Bundle;

public class SettingsActivity extends MonstersPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}
