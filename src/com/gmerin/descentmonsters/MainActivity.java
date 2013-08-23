package com.gmerin.descentmonsters;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Nos quedamos con el TabHost para poblarlo
		TabHost tabHost = getTabHost();
		
		// Nos quedamos con los recursos
		Resources res = getResources();
		
		String txtTabMon = res.getString(R.string.main_tab_monsters);
		String txtTabAct1 = res.getString(R.string.main_tab_act1);
		String txtTabAct2 = res.getString(R.string.main_tab_act2);
		
		// Pestaña para los Monstruos Generales
		TabSpec monSpec = tabHost.newTabSpec(txtTabMon);
		monSpec.setIndicator(txtTabMon);
		Intent monIntent = new Intent(this, MonstersActivity.class);
		monSpec.setContent(monIntent);
		
		// Pestaña para los Monstruos del Acto I
		TabSpec acto1Spec = tabHost.newTabSpec(txtTabAct1);
		acto1Spec.setIndicator(txtTabAct1);
		Intent acto1Intent = new Intent(this, Act1Activity.class);
		acto1Spec.setContent(acto1Intent);
		
		// Pestaña para los Monstruos del Acto II
		TabSpec acto2Spec = tabHost.newTabSpec(txtTabAct2);
		acto2Spec.setIndicator(txtTabAct2);
		Intent acto2Intent = new Intent(this, Act2Activity.class);
		acto2Spec.setContent(acto2Intent);
		
		// Añadimos las pestañas al TabHost
		tabHost.addTab(monSpec);
		tabHost.addTab(acto1Spec);
		tabHost.addTab(acto2Spec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Invocamos al padre
		super.onCreateOptionsMenu(menu);	
		
		// Añadimos nuestro menu de configuración
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case  R.id.menu_config:
				startActivity(new Intent(MainActivity.this, SettingsActivity.class));
				break;
			case R.id.menu_filter:
				startActivity(new Intent(MainActivity.this, FiltersActivity.class));
				break;
			case R.id.menu_about:
				startActivity(new Intent(MainActivity.this, AboutActivity.class));
				break;
			case R.id.menu_exit:
				finish();
		}
		return true;
	}

}
