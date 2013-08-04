package com.gmerin.descentmonsters.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmerin.descentmonsters.R;
import com.gmerin.descentmonsters.db.MonstersDBHelper;
import com.gmerin.descentmonsters.reflective.ReflectiveHelper;
import com.gmerin.descentmonsters.utils.DrawableHelper;
import com.gmerin.descentmonsters.utils.Constants;
import com.gmerin.descentmonsters.utils.ImageInText;

public class ActXListViewAdapter extends CursorAdapter {

	public ActXListViewAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Obtenemos una referencia a todas las vistas
		ImageView tipoAtaque = (ImageView) view.findViewById(R.id.mon_attack_type);
		
		ImageView ataque1 = (ImageView) view.findViewById(R.id.mon_attack_dice1);
		ImageView ataque2 = (ImageView) view.findViewById(R.id.mon_attack_dice2);
		ImageView ataque3 = (ImageView) view.findViewById(R.id.mon_attack_dice3);
		
		ImageView defensa1 = (ImageView) view.findViewById(R.id.mon_defense_dice1);
		ImageView defensa2 = (ImageView) view.findViewById(R.id.mon_defense_dice2);
		
		TextView velocidad = (TextView) view.findViewById(R.id.mon_speed_value);
		
		TextView vida = (TextView) view.findViewById(R.id.mon_health_value);
		
		TextView nombre = (TextView) view.findViewById(R.id.mon_act_name);
		
		TextView tamGrupo = (TextView) view.findViewById(R.id.mon_group_value);
		
		ImageView cap1Coste = (ImageView) view.findViewById(R.id.mon_ab1_cost);
		ImageView cap2Coste = (ImageView) view.findViewById(R.id.mon_ab2_cost);
		ImageView cap3Coste = (ImageView) view.findViewById(R.id.mon_ab3_cost);
		ImageView cap4Coste = (ImageView) view.findViewById(R.id.mon_ab4_cost);
		
		TextView cap1Nombre = (TextView) view.findViewById(R.id.mon_ab1_name);
		TextView cap2Nombre = (TextView) view.findViewById(R.id.mon_ab2_name);
		TextView cap3Nombre = (TextView) view.findViewById(R.id.mon_ab3_name);
		TextView cap4Nombre = (TextView) view.findViewById(R.id.mon_ab4_name);
		
		// Si el monstruos es líder, entonces cambiamos el color de fondo de la fila
		Boolean esLider = Boolean.parseBoolean(cursor.getString(MonstersDBHelper.MON_LEADER_COL));
		if (esLider)
			view.setBackgroundColor(Color.argb(100, 244, 199, 199));
		else
			view.setBackgroundColor(Color.argb(0, 255, 255, 255));
		
		// Rellenamos el tipo de ataque
		String auxTipoAtaque = cursor.getString(MonstersDBHelper.MON_ATTACK_TYPE_COL);
		int tipoAtaqueDrawableID = DrawableHelper.getDrawableIDAttackType(auxTipoAtaque);
		if(tipoAtaqueDrawableID != -1) tipoAtaque.setImageResource(tipoAtaqueDrawableID); 
		
		// Si estamos en el acto 2, añadimos la imagen del tercer dado transparente.
		// Si al final el monstruo no tiene tercer dado, al menos todo quedará tabulado.
		if (cursor.getInt(MonstersDBHelper.MON_ACT_COL) == 2)
			ataque3.setImageResource(R.drawable.dice_span);
		
		// Rellenamos los dados de ataque
		String auxAtaque = cursor.getString(MonstersDBHelper.MON_ATTACK_COL);
		for(int i=0; i<auxAtaque.length(); i++)
		{
			int auxID = DrawableHelper.getDrawableIDDice(auxAtaque.charAt(i));
			switch(i) {
			case 0:
				if(auxID != -1) ataque1.setImageResource(auxID);
				break;
			case 1:
				if(auxID != -1) ataque2.setImageResource(auxID);
				break;
			case 2:
				if(auxID != -1) ataque3.setImageResource(auxID);
				break;
			}
		}
		
		// Rellenamos los dados de defensa
		String auxDefensa = cursor.getString(MonstersDBHelper.MON_DEFENSE_COL);
		for(int i=0; i<auxDefensa.length(); i++)
		{
			int auxID = DrawableHelper.getDrawableIDDice(auxDefensa.charAt(i));
			switch(i) {
			case 0:
				if(auxID != -1) defensa1.setImageResource(auxID);
				break;
			case 1:
				if(auxID != -1) defensa2.setImageResource(auxID);
				break;
			}
		}
		
		// Rellenamos el valor de la velocidad
		velocidad.setText(cursor.getString(MonstersDBHelper.MON_SPEED_COL));
		
		// Rellenamos el valor de la vida
		vida.setText(cursor.getString(MonstersDBHelper.MON_HEALTH_COL));
		
		// Rellenamos el nombre del monstruo
		String nomMonBD = cursor.getString(MonstersDBHelper.MON_NAME_COL);
		int codMonBD = cursor.getInt(MonstersDBHelper.MON_COD_COL);
		// Utilizando la reflexión de java, obtendremos el nombre del monstruo
		// en el idioma actual
		Resources res = view.getResources();
		int idMon = ReflectiveHelper.getRStringID(Constants.SUFFIX_NAME_MONSTERS+codMonBD);
		if (idMon != -1) 
			nombre.setText(res.getString(idMon));
		else
			nombre.setText(nomMonBD);
		
		// Rellenamos el tamaño del grupo de monstruos
		String valorGrupo = cursor.getString(MonstersDBHelper.MON_GRP_SIZE2H_COL)+"|";
		valorGrupo += cursor.getString(MonstersDBHelper.MON_GRP_SIZE3H_COL)+"|";
		valorGrupo += cursor.getString(MonstersDBHelper.MON_GRP_SIZE4H_COL);
		tamGrupo.setText(valorGrupo);
		 
		// Rellenamos las habilidades (Máximo 4)
		int id_mon_act = cursor.getInt(MonstersDBHelper.MON_ACT_COD_COL);
		MonstersDBHelper monDB = new MonstersDBHelper(context);
		Cursor capCursor = monDB.getMonActAbilities(id_mon_act);
		
		// NOTA: Vamos a inicializar el valor de las cadenas de las habilidades
		// y el del icono de los costes ya que, al no tener un valor por defecto,
		// Puede llegar a utilizarse algún valor anterior, cosa que no es deseable.
		// Inicializamos las cadenas 
		cap1Nombre.setText("");
		cap2Nombre.setText("");
		cap3Nombre.setText("");
		cap4Nombre.setText("");
		
		// Inicializamos el coste
		cap1Coste.setImageResource(-1);
		cap2Coste.setImageResource(-1);
		cap3Coste.setImageResource(-1);
		cap4Coste.setImageResource(-1);
		
		// Recorremos el cursor con las capacidades del monstruo actual
		int i = 1;
		while(capCursor.moveToNext()) {
			// Una capacidad puede tener un modificador o un nivel determinado
			String modCap = "";
			String nivelCap = "";
			
			// Si el valor no es nulo, nos lo quedamos
			if (!capCursor.isNull(MonstersDBHelper.AB_MOD_COL))
				modCap = "+"+capCursor.getString(MonstersDBHelper.AB_MOD_COL)+" ";
			if (!capCursor.isNull(MonstersDBHelper.AB_LEVEL_COL))
				nivelCap = " "+capCursor.getString(MonstersDBHelper.AB_LEVEL_COL);
 
			// Nos quedamos con el nombre de la capacidad en el idioma actual
			// gracias a la reflexión de java. Si no encontramos la cadena, usamos la que
			// hay en la base de datos.
			String nomCap = capCursor.getString(MonstersDBHelper.AB_NAME_COL);
			int idCap = ReflectiveHelper.getRStringID(Constants.SUFFIX_NAME_ABILITIES+capCursor.getInt(MonstersDBHelper.AB_ID_COL));
			if (idCap != -1)
				nomCap = res.getString(idCap);
			
			// Construimos el text de la capacidad gracias al modificador, nombre y nivel
			String textoCap = modCap+nomCap+nivelCap;
			
			// Si la habilidad tiene un coste, nos quedamos con el icono que lo representa
			String costeAb = capCursor.getString(MonstersDBHelper.AB_COST_COL);
			int iconID = DrawableHelper.getDrawableIDAbilityCost(costeAb);
			
			switch(i) {
			case 1:
				ImageInText.setImageInText(textoCap, Constants.TOKEN_DAMAGE_ICON, R.drawable.icon_damage, cap1Nombre, context);
				if (iconID != -1) cap1Coste.setImageResource(iconID);
				break;
			case 2:
				ImageInText.setImageInText(textoCap, Constants.TOKEN_DAMAGE_ICON, R.drawable.icon_damage, cap2Nombre, context);
				if (iconID != -1) cap2Coste.setImageResource(iconID);
				break;
			case 3:
				ImageInText.setImageInText(textoCap, Constants.TOKEN_DAMAGE_ICON, R.drawable.icon_damage, cap3Nombre, context);
				if (iconID != -1) cap3Coste.setImageResource(iconID);
				break;
			case 4:
				ImageInText.setImageInText(textoCap, Constants.TOKEN_DAMAGE_ICON, R.drawable.icon_damage, cap4Nombre, context);
				if (iconID != -1) cap4Coste.setImageResource(iconID);
				break;
			}
			i++;
		}
		capCursor.close();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.actx_listview_row, parent, false);
		return retView;
	}
	
}
