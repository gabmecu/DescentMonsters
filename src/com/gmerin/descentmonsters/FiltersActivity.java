package com.gmerin.descentmonsters;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class FiltersActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.filters);

	}
}
