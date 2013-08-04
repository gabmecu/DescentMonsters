package com.gmerin.descentmonsters.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmerin.descentmonsters.utils.PreferencesHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MonstersDBHelper {

	// Columnas para las consultas monSQL (General Monsters) y actXSQL (Act X Monsters)
	public static final int MON_COD_COL = 0;
	public static final int MON_NAME_COL = 1;
	public static final int MON_ATTACK_TYPE_COL = 2;
	public static final int MON_SIZE_COL = 3;
	public static final int MON_EXPANSION_COL = 4;
	public static final int MON_TRAIT1_COL = 5;
	public static final int MON_TRAIT2_COL = 6;
	public static final int MON_LEADER_COL = 7;
	public static final int MON_HEALTH_COL = 8;
	public static final int MON_SPEED_COL = 9;
	public static final int MON_DEFENSE_COL = 10;
	public static final int MON_ATTACK_COL = 11;
	public static final int MON_GRP_SIZE2H_COL = 12;
	public static final int MON_GRP_SIZE3H_COL = 13;
	public static final int MON_GRP_SIZE4H_COL = 14;
	public static final int MON_ACT_COL = 15;
	public static final int MON_ACT_COD_COL = 16;
	
	// Columnas para la consulta monAbsSQL (Monster's Abilities SQL)
	public static final int AB_ID_COL = 0;
	public static final int AB_COST_COL = 1;
	public static final int AB_MOD_COL = 2;
	public static final int AB_NAME_COL = 3;
	public static final int AB_LEVEL_COL = 4;
	
	public static final String TOKEN_TRAITS = ":trt:";
	public static final String TOKEN_ATTACKS = ":atk:";
	public static final String TOKEN_EXPANSIONS = ":exp:";
	public static final String TOKEN_ACT = ":act:";
	
	public static final String TOKEN_IDMONACT = ":id:";
			
	private static String monSQL = "SELECT MG.id AS _id, MG.nombre, MG.tipo_ataque, MG.tam_peana, E.cod, R1.id, R2.id "+
								   "FROM Expansiones AS E, Monstruos_Genericos AS MG, Rasgos AS R1, Rasgos AS R2 "+
								   "WHERE E.cod=MG.cod_expansion AND MG.rasgo1=R1.id AND MG.rasgo2=R2.id ";							   
	
	private static String actXSQL = "SELECT MG.id AS _id, MG.nombre, MG.tipo_ataque, MG.tam_peana, E.cod, R1.id, R2.id, "+ 
									"MA.lider, MA.vida, MA.velocidad, MA.defensa, MA.ataque, MA.grupo2h, MA.grupo3h, MA.grupo4h, MA.acto, MA.id "+
									"FROM Expansiones AS E, Monstruos_Genericos AS MG, Rasgos AS R1, Rasgos AS R2, Monstruos_Acto AS MA "+
									"WHERE E.cod=MG.cod_expansion AND MG.rasgo1=R1.id AND MG.rasgo2=R2.id "+
										"AND MA.id_mon=MG.id AND MA.acto="+TOKEN_ACT+" ";
	
	private static String filterSQL = 	"AND MG.tipo_ataque NOT IN ("+TOKEN_ATTACKS+") "+ 			// Filtro1: Tipo de ataque
	   									"AND (R1.id NOT IN ("+TOKEN_TRAITS+") OR R2.id NOT IN ("+TOKEN_TRAITS+")) "+ // Filtro2: Rasgos
	   									"AND MG.cod_expansion NOT IN ("+TOKEN_EXPANSIONS+") "; 			// Filtro3: Expansiones
	
	private static String monAbSQL = "SELECT CM.id_cap AS _id, CM.coste_cap, CM.mod_cap, C.nombre, CM.nivel_cap "+
									 "FROM Capacidades_Monstruos AS CM, Capacidades AS C "+
									 "WHERE CM.id_cap=C.id AND CM.id_mon_act="+TOKEN_IDMONACT+";";
	
	private static String orderSQL = "ORDER BY MG.nombre;";
	
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
		String auxSQL = filterSQL.replace(TOKEN_ATTACKS, prefHelp.getAttackTypesToFilter());
		auxSQL = auxSQL.replace(TOKEN_EXPANSIONS, prefHelp.getExpansionsToFilter());
		auxSQL = auxSQL.replace(TOKEN_TRAITS, prefHelp.getTraitsToFilter());
		// Construimos la consulta final
		String fullSQL = monSQL+auxSQL+orderSQL;
		return db.rawQuery(fullSQL, null);
	}
	
	public Cursor getAct1Monsters(PreferencesHelper prefHelp) {
		return getActXMonsters(prefHelp, 1);
	}
	
	public Cursor getAct2Monsters(PreferencesHelper prefHelp) {
		return getActXMonsters(prefHelp, 2);
	}
	
	private Cursor getActXMonsters(PreferencesHelper prefHelp, int act) {
		// Modificamos la consulta en función de los filtros y la configuración
		String auxSQL = filterSQL.replace(TOKEN_ATTACKS, prefHelp.getAttackTypesToFilter());
		auxSQL = auxSQL.replace(TOKEN_EXPANSIONS, prefHelp.getExpansionsToFilter());
		auxSQL = auxSQL.replace(TOKEN_TRAITS, prefHelp.getTraitsToFilter());
		// Nos quedamos con los mosntruos del acto correxpondiente
		String mainSQL = actXSQL.replace(TOKEN_ACT, Integer.toString(act));
		// Construimos la consulta final
		String fullSQL = mainSQL+auxSQL+orderSQL;
		return db.rawQuery(fullSQL, null);
	}
	
	public Cursor getMonActAbilities(int id_mon_act) {
		String fullSQL = monAbSQL.replace(TOKEN_IDMONACT, Integer.toString(id_mon_act));
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
