package com.gmerin.descentmonsters;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.gmerin.descentmonsters.adapters.ActXListViewAdapter;
import com.gmerin.descentmonsters.db.MonstersDBHelper;
import com.gmerin.descentmonsters.utils.PreferencesHelper;

public class Act2Activity extends Activity {
	private MonstersDBHelper monDBHelper;
	private ListView listView;
	private ActXListViewAdapter monActXAdapter;

	private PreferencesHelper prefHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act2);
		
		// Creamos esta variable para pasarle las preferencias al adaptador
		prefHelp = new PreferencesHelper(this);
		
		monDBHelper = new MonstersDBHelper(this);
		listView = (ListView) findViewById(R.id.listView_act2);
		
		// El acceso a la base de datos puede llevar tiempo, as� que mejor
		// realizarlo en otro hilo. De eso se encargar� Handler
		new Handler().post(new Runnable() {
			public void run() {
				monActXAdapter = new ActXListViewAdapter(Act2Activity.this, monDBHelper.getAct2Monsters(prefHelp), false);
				listView.setAdapter(monActXAdapter);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Si han habido cambios en las preferencias, refrescamos el contenido
		if(FiltersActivity.updateAct2Monsters() || SettingsActivity.updateAct2Monsters()) {
			FiltersActivity.resetAct2Monsters();
			SettingsActivity.resetAct2Monsters();
			monActXAdapter = new ActXListViewAdapter(Act2Activity.this, monDBHelper.getAct2Monsters(prefHelp), false);
			listView.setAdapter(monActXAdapter);
		}
	}

}
