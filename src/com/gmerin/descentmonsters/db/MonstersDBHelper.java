package com.gmerin.descentmonsters.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmerin.descentmonsters.utils.PreferencesHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MonstersDBHelper {

	public static int MON_COD_COL = 0;
	public static int MON_NAME_COL = 1;
	public static int MON_ATTACK_TYPE_COL = 2;
	public static int MON_SIZE_COL = 3;
	public static int MON_EXPANSION_COL = 4;
	public static int MON_TRAIT1_COL = 5;
	public static int MON_TRAIT2_COL = 6;
	
	public static String TOKEN_TRAITS = ":trt:";
	public static String TOKEN_ATTACKS = ":atk:";
	public static String TOKEN_EXPANSIONS = ":exp:";
			
	private static String monSQL = "SELECT MG.id AS _id, MG.nombre, MG.tipo_ataque, MG.tam_peana, E.cod, R1.id, R2.id "+
								   "FROM Expansiones AS E, Monstruos_Genericos AS MG, Rasgos AS R1, Rasgos AS R2 "+
								   "WHERE E.cod=MG.cod_expansion AND MG.rasgo1=R1.id AND MG.rasgo2=R2.id "+
								   		"AND MG.tipo_ataque NOT IN ("+TOKEN_ATTACKS+") "+ 			// Filtro1: Tipo de ataque
								   		"AND (R1.id NOT IN ("+TOKEN_TRAITS+") OR R2.id NOT IN ("+TOKEN_TRAITS+")) "+ // Filtro2: Rasgos
								   		"AND MG.cod_expansion NOT IN ("+TOKEN_EXPANSIONS+") "+ 			// Filtro3: Expansiones
								   "ORDER BY MG.nombre;";
	
	private static final String DB_NAME = "monsters";
	private static final int DB_VERSION = 1;
	
	private DBOpenHelper dbHelper;
	private SQLiteDatabase db;
	
	public MonstersDBHelper(Context context) {
		dbHelper = new DBOpenHelper(context);
		db = dbHelper.getReadableDatabase();
	}
	
	public Cursor getMonsters(PreferencesHelper prefHelp) {
		// Modificamos la consulta en función de los filtros y la configuración
		String fullSQL = monSQL.replace(TOKEN_ATTACKS, prefHelp.getAttackTypesToFilter());
		fullSQL = fullSQL.replace(TOKEN_EXPANSIONS, prefHelp.getExpansionsToFilter());
		fullSQL = fullSQL.replace(TOKEN_TRAITS, prefHelp.getTraitsToFilter());
		return db.rawQuery(fullSQL, null);
	}
	
	private class DBOpenHelper extends SQLiteAssetHelper {

		public DBOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// En el caso que sea necesario actualizar la BD
		}
		
	}

}
