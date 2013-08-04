package com.gmerin.descentmonsters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

import com.gmerin.descentmonsters.adapters.MonstersListViewAdapter;
import com.gmerin.descentmonsters.db.MonstersDBHelper;
import com.gmerin.descentmonsters.utils.PreferencesHelper;

public class MonstersActivity extends Activity {

	private MonstersListViewAdapter monAdapter;
	private MonstersDBHelper monDBHelper;
	private ListView listView;
	
	private PreferencesHelper prefHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monsters);
		
		// Creamos esta variable para pasarle las preferencias al adaptador
		prefHelp = new PreferencesHelper(this);
		
		monDBHelper = new MonstersDBHelper(this);
		listView = (ListView) findViewById(R.id.listView_monsters);
		
		// El acceso a la base de datos puede llevar tiempo, así que mejor
		// realizarlo en otro hilo. De eso se encargará Handler
		new Handler().post(new Runnable() {
			public void run() {
				monAdapter = new MonstersListViewAdapter(MonstersActivity.this, monDBHelper.getMonsters(prefHelp), false);
				listView.setAdapter(monAdapter);
			}
		});
	}
	
	@Override
	protected void onResume() {
		// Siempre que se vuelva a mostrar la pantalla, recargamos los datos no sea que se hayan modificado las preferencias
		super.onResume();
		monAdapter = new MonstersListViewAdapter(MonstersActivity.this, monDBHelper.getMonsters(prefHelp), false);
		listView.setAdapter(monAdapter);
	}

}
