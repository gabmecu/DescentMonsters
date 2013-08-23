
package com.gmerin.descentmonsters;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		
		// Intentamos obtener el número de versión
		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String version = pInfo.versionName;
			
			// Obtenemos el elemento gráfico
			TextView versionValue = (TextView) findViewById(R.id.versionValue);
			// Le asignamos valor
			versionValue.setText(version);
			
		} catch (NameNotFoundException e) {
			// No deberíamos llegar aquí nunca
		}
	}
}
