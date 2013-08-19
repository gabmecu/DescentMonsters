package com.gmerin.descentmonsters;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.gmerin.descentmonsters.adapters.ActXListViewAdapter;
import com.gmerin.descentmonsters.db.MonstersDBHelper;
import com.gmerin.descentmonsters.utils.PreferencesHelper;

public class Act1Activity extends Activity {
	private MonstersDBHelper monDBHelper;
	private ListView listView;
	private ActXListViewAdapter monActXAdapter;
	
	private PreferencesHelper prefHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act1);
		
		// Creamos esta variable para pasarle las preferencias al adaptador
		prefHelp = new PreferencesHelper(this);
		
		monDBHelper = new MonstersDBHelper(this);
		listView = (ListView) findViewById(R.id.listView_act1);
		
		// El acceso a la base de datos puede llevar tiempo, así que mejor
		// realizarlo en otro hilo. De eso se encargará Handler
		new Handler().post(new Runnable() {
			public void run() {
				monActXAdapter = new ActXListViewAdapter(Act1Activity.this, monDBHelper.getAct1Monsters(prefHelp), false);
				listView.setAdapter(monActXAdapter);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Si han habido cambios en las preferencias, refrescamos el contenido
		if(FiltersActivity.updateAct1Monsters() || SettingsActivity.updateAct1Monsters()) {
			FiltersActivity.resetAct1Monsters();
			SettingsActivity.resetAct1Monsters();
			monActXAdapter = new ActXListViewAdapter(Act1Activity.this, monDBHelper.getAct1Monsters(prefHelp), false);
			listView.setAdapter(monActXAdapter);
		}
	}

}
