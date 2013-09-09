package com.gmerin.descentmonsters;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmerin.descentmonsters.db.MonstersDBHelper;
import com.gmerin.descentmonsters.reflective.ReflectiveHelper;
import com.gmerin.descentmonsters.utils.Constants;
import com.gmerin.descentmonsters.utils.DrawableHelper;
import com.gmerin.descentmonsters.utils.ImageInText;

public class MonsterAbilityDescActivity extends Activity {
	public static final String ACT_KEY = "act";
	public static final String ID_MON_KEY = "idMon";
	public static final String ID_MON_ACT_KEY = "idMonAct";
	public static final String IS_LEADER_KEY = "isLeader";
	public static final String GRP_SIZE_2h_KEY = "grp2h";
	public static final String GRP_SIZE_3h_KEY = "grp3h";
	public static final String GRP_SIZE_4h_KEY = "grp4h";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abilities_description);
		
		Resources res = getResources();
		
		// Me quedo con la información que me han pasado en forma de Bundle
		Bundle bundle = this.getIntent().getExtras();
		
		// Si no me han pasado nada, no hay nada que hacer
		if (bundle == null) return;
		
		// Obtenemos los elementos gráficos
		TextView txtActo = (TextView) findViewById(R.id.txt_act);
		TextView txtMonsterName = (TextView) findViewById(R.id.txt_monster_name);
		TextView txtAb1Desc = (TextView) findViewById(R.id.txt_ab1_desc);
		TextView txtAb2Desc = (TextView) findViewById(R.id.txt_ab2_desc);
		TextView txtAb3Desc = (TextView) findViewById(R.id.txt_ab3_desc);
		TextView txtAb4Desc = (TextView) findViewById(R.id.txt_ab4_desc);
		ImageView imgMonIcon = (ImageView) findViewById(R.id.img_monster_icon);
		
		// TODO Falta el tamaño del grupo
		
		// Sacamos la información del bundle
		int acto = bundle.getInt(ACT_KEY);
		int idMon = bundle.getInt(ID_MON_KEY);
		int idMonActo = bundle.getInt(ID_MON_ACT_KEY);
		Boolean esLider = bundle.getBoolean(IS_LEADER_KEY);
		int tamGrupo2h = bundle.getInt(GRP_SIZE_2h_KEY);
		int tamGrupo3h = bundle.getInt(GRP_SIZE_3h_KEY);
		int tamGrupo4h = bundle.getInt(GRP_SIZE_4h_KEY);
		
		// Necesitamos un cursor para recorrer las habilidades del monstruo en cuestión
		MonstersDBHelper monDB = new MonstersDBHelper(this);
		Cursor capCursor = monDB.getMonActAbilities(idMonActo);
		
		// Rellenamos el acto
		String actoNumRom = " I";
		if (acto == 2) actoNumRom = " II";
		txtActo.setText(txtActo.getText().toString()+actoNumRom); // TODO Utilizar una cadena que dependa del idioma
		
		// Rellenamos el nombre del monstruo
		int idMonProperty = ReflectiveHelper.getRStringID(Constants.SUFFIX_NAME_MONSTERS+idMon);
		if (idMonProperty != -1) 
			txtMonsterName.setText(res.getString(idMonProperty));
		
		// Rellenamos el icono del monstruo
		if(esLider) {
			int idImgProperty = ReflectiveHelper.getRDrawableID(Constants.SUFFIX_IMG_LEADER_MONSTERS+idMon);
			if (idImgProperty != -1)
				imgMonIcon.setImageResource(idImgProperty);
		} else {
			int idImgProperty = ReflectiveHelper.getRDrawableID(Constants.SUFFIX_IMG_MINION_MONSTERS+idMon);
			if (idImgProperty != -1)
				imgMonIcon.setImageResource(idImgProperty);
		}
		
		// Rellenamos las habilidades
		// Inicializamos los TextView a cadena vacía y las ocultamos
		txtAb1Desc.setText("");
		txtAb1Desc.setVisibility(ImageView.GONE);
		txtAb2Desc.setText("");
		txtAb2Desc.setVisibility(ImageView.GONE);
		txtAb3Desc.setText("");
		txtAb3Desc.setVisibility(ImageView.GONE);
		txtAb4Desc.setText("");
		txtAb4Desc.setVisibility(ImageView.GONE);
		
		// Recorremos el cursor con las capacidades del monstruo actual
		int i = 1;
		while(capCursor.moveToNext()) {
			// Una capacidad puede tener un modificador o un nivel determinado
			String modCap = ""; // Modificador de la capacidad
			String nvlCap = ""; // Nivel de la capacidad
			String nomCap = ""; // Nombre de la capacidad
			String desCap = ""; // Descripción de la capacidad
			
			// Si el valor no es nulo, nos lo quedamos
			if (!capCursor.isNull(MonstersDBHelper.AB_MOD_COL))
				modCap = "+"+capCursor.getString(MonstersDBHelper.AB_MOD_COL)+" ";
			if (!capCursor.isNull(MonstersDBHelper.AB_LEVEL_COL))
				nvlCap = " "+capCursor.getString(MonstersDBHelper.AB_LEVEL_COL);
 
			// Nos quedamos con el nombre de la capacidad en el idioma actual
			// gracias a la reflexión de java. Si no encontramos la cadena, usamos la que
			// hay en la base de datos.
			nomCap = capCursor.getString(MonstersDBHelper.AB_NAME_COL);
			int idCap = ReflectiveHelper.getRStringID(Constants.SUFFIX_NAME_ABILITIES+capCursor.getInt(MonstersDBHelper.AB_ID_COL));
			if (idCap != -1)
				nomCap = res.getString(idCap);
			
			// Obtenemos la descripción de la capacidad
			int idDesCap = ReflectiveHelper.getRStringID(Constants.SUFFIX_DESC_ABILITIES+capCursor.getInt(MonstersDBHelper.AB_ID_COL));
			if (idDesCap != -1)
				desCap = res.getString(idDesCap);
			
			// Si la habilidad tiene un coste, nos quedamos con él
			String codCosteAb = capCursor.getString(MonstersDBHelper.AB_COST_COL);
			// El código del coste de la BD es diferente al aquí utilizado
			String tokenCosteAb = DrawableHelper.transformBDActionCostToToken(codCosteAb);
			
			// TODO: Quizá sea conveniente utilizar la misma sintaxis de tokens tanto en la
			// BD como en el programa para ahorrarnos la transformación anterior.
			
			// Construimos el texto de la capacidad gracias al modificador, nombre y nivel
			String nombreYCosteCap = tokenCosteAb+" "+modCap+nomCap+nvlCap;
			String textoCap = nombreYCosteCap+": "+desCap;
			
			// Construimos un SpannableStringBuilder
			SpannableStringBuilder ssb = new SpannableStringBuilder(textoCap);
			// Ponemos el nombre y coste de la capacidad en negrita
			ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, nombreYCosteCap.length(), 0);
			
			switch(i) {
			case 1:
				ImageInText.setImageInText(ssb, txtAb1Desc, this);
				txtAb1Desc.setVisibility(ImageView.VISIBLE);
				break;
			case 2:
				ImageInText.setImageInText(ssb, txtAb2Desc, this);
				txtAb2Desc.setVisibility(ImageView.VISIBLE);
				break;
			case 3:
				ImageInText.setImageInText(ssb, txtAb3Desc, this);
				txtAb3Desc.setVisibility(ImageView.VISIBLE);
				break;
			case 4:
				ImageInText.setImageInText(ssb, txtAb4Desc, this);
				txtAb4Desc.setVisibility(ImageView.VISIBLE);
				break;
			}
			i++;
		}
		capCursor.close();
	}
}
