package com.gmerin.descentmonsters.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmerin.descentmonsters.R;
import com.gmerin.descentmonsters.db.MonstersDBHelper;
import com.gmerin.descentmonsters.reflective.ReflectiveHelper;

public class MonstersListViewAdapter extends CursorAdapter {


	public MonstersListViewAdapter(Context context, Cursor c,
			boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Obtenemos una referencia a todas las vistas
		ImageView tipoAtaque = (ImageView) view.findViewById(R.id.monsters_list_txt);
		TextView nombre = (TextView) view.findViewById(R.id.mon_name);
		ImageView rasgo1 = (ImageView) view.findViewById(R.id.mon_trait1);
		ImageView rasgo2 = (ImageView) view.findViewById(R.id.mon_trait2);
		TextView expansion = (TextView) view.findViewById(R.id.mon_expansion);
		TextView tam_peana = (TextView) view.findViewById(R.id.mon_size);
		
		// Rellenamos la imagen del tipo de ataque
		String ataque = cursor.getString(MonstersDBHelper.MON_ATTACK_TYPE_COL);
		int ataqueDrawableID = getDrawableIDAttackType(ataque);
		if(ataqueDrawableID != -1) tipoAtaque.setImageResource(ataqueDrawableID);
		
		// Rellenamos el nombre del monstruo
		String nomMonBD = cursor.getString(MonstersDBHelper.MON_NAME_COL);
		int codMonBD = cursor.getInt(MonstersDBHelper.MON_COD_COL);
		// Utilizando la reflexión de java, obtendremos el nombre del monstruo
		// en el idioma actual
		Resources res = view.getResources();
		int id = ReflectiveHelper.getRStringID("mon_name_"+codMonBD);
		if (id != -1) 
			nombre.setText(res.getString(id));
		else
			nombre.setText(nomMonBD);
		
		// Rellenamos los rasgos
		int r1 = cursor.getInt(MonstersDBHelper.MON_TRAIT1_COL);
		int r2 = cursor.getInt(MonstersDBHelper.MON_TRAIT2_COL);
		
		int r1DrawableID = getDrawableIDTrait(r1);
		int r2DrawableID = getDrawableIDTrait(r2);
		
		if(r1DrawableID != -1) rasgo1.setImageResource(r1DrawableID);
		if(r2DrawableID != -1) rasgo2.setImageResource(r2DrawableID);
		
		// Rellenamos el tamaño
		tam_peana.setText(cursor.getString(MonstersDBHelper.MON_SIZE_COL));
		
		// Rellenamos la expansión
		expansion.setText("("+cursor.getString(MonstersDBHelper.MON_EXPANSION_COL)+")");
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.monsters_listview_row, parent, false);
		return retView;
	}
	
	private int getDrawableIDAttackType(String attack) {
		// Cuerpo a cuerpo
		if (attack.compareToIgnoreCase("cc")==0) return R.drawable.attack_type_melee;
		if (attack.compareToIgnoreCase("ad")==0) return R.drawable.attack_type_distance;
		return -1;
	}
	
	private int getDrawableIDTrait(int traitID) {
		switch(traitID) {
			case 1: return R.drawable.trait_wilderness; // Bosque
			case 2:	return R.drawable.trait_water; // Agua
			case 3: return R.drawable.trait_hot; // Calor
			case 4: return R.drawable.trait_civilized; // Civilizado
			case 5: return R.drawable.trait_cave; // Cueva
			case 6: return R.drawable.trait_building; // Edificio
			case 7: return R.drawable.trait_cold; // Frío
			case 8: return R.drawable.trait_cursed; // Maldito
			case 9: return R.drawable.trait_mountain; // Montaña
			case 10: return R.drawable.trait_dark; // Oscuridad
		}
		return -1;
	}

}
