package com.gmerin.descentmonsters.listeners;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gmerin.descentmonsters.MonsterAbilityDescActivity;
import com.gmerin.descentmonsters.db.MonstersDBHelper;

public class ActXListViewListener implements OnItemClickListener {
	
	private Activity parentActivity;
	
	public ActXListViewListener(Activity parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	public void onItemClick(AdapterView<?> listAdapter, View view, int position, long id) {
		// La view es el LinearLayout que contiene todos los elementos gráficos
		// Gracias al adaptador, puedo obtener también el cursor utilizado para crear la view
		Cursor cursor = (Cursor) listAdapter.getItemAtPosition(position);
		
		// Creo una nueva intención
		Intent intent = new Intent(parentActivity, MonsterAbilityDescActivity.class);
		
		// Obtengo del cursor la información que necesita la nueva actividad
		int acto = cursor.getInt(MonstersDBHelper.MON_ACT_COL);
		int idMon = cursor.getInt(MonstersDBHelper.MON_COD_COL);
		int idMonActo = cursor.getInt(MonstersDBHelper.MON_ACT_COD_COL);
		Boolean esLider = Boolean.parseBoolean(cursor.getString(MonstersDBHelper.MON_LEADER_COL));
		int tamGrupo2h = cursor.getInt(MonstersDBHelper.MON_GRP_SIZE2H_COL);
		int tamGrupo3h = cursor.getInt(MonstersDBHelper.MON_GRP_SIZE3H_COL);
		int tamGrupo4h = cursor.getInt(MonstersDBHelper.MON_GRP_SIZE4H_COL);
		
		// Guardo la información dentro de un bundle
		Bundle info = new Bundle();
		info.putInt(MonsterAbilityDescActivity.ACT_KEY, acto);
		info.putInt(MonsterAbilityDescActivity.ID_MON_KEY, idMon);
		info.putInt(MonsterAbilityDescActivity.ID_MON_ACT_KEY, idMonActo);
		info.putBoolean(MonsterAbilityDescActivity.IS_LEADER_KEY, esLider);
		info.putInt(MonsterAbilityDescActivity.GRP_SIZE_2h_KEY, tamGrupo2h);
		info.putInt(MonsterAbilityDescActivity.GRP_SIZE_3h_KEY, tamGrupo3h);
		info.putInt(MonsterAbilityDescActivity.GRP_SIZE_4h_KEY, tamGrupo4h);
		
		// Le paso el bundle a la intención
		intent.putExtras(info);
		
		// Lanzo la actividad
		parentActivity.startActivity(intent);
	}

}
