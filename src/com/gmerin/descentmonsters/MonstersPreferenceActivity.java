package com.gmerin.descentmonsters;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class MonstersPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	// Campos privados para indicar si hay que actualizar los listados de monstruos
	private static boolean updateGeneralMonsters = false;
	private static boolean updateAct1Monsters = false;
	private static boolean updateAct2Monsters = false;
	
	/**
	 * En función del cambio que se haya realizado sobre las preferencias, hace las acciones
	 * oportunas. En nuestro caso, se marcan las variables estáticas updateXXX a true para
	 * que sus respectivas actividades actualicen su contenido.
	 */
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		updateGeneralMonsters = true;
		updateAct1Monsters = true;
		updateAct2Monsters = true;		
	}
	
	/**
	 * Devuelve TRUE si el listado general de monstruos debe ser actualizado debido a un cambio
	 * en las preferencias. FALSE en cualquier otro caso.
	 * @return boolean
	 */
	public static boolean updateGeneralMonsters() {
		return updateGeneralMonsters;
	}
	
	/**
	 * Devuelve TRUE si el listado de monstruos del Acto 1 debe ser actualizado debido a un cambio
	 * en las preferencias. FALE en cualquier otro caso.
	 * @return boolean
	 */
	public static boolean updateAct1Monsters() {
		return updateAct1Monsters;
	}
	
	/**
	 * Devuelve TRUE si el listado de monstruos del Acto 2 debe ser actualizado debido a un cambio
	 * en las preferencias.
	 * @return
	 */
	public static boolean updateAct2Monsters() {
		return updateAct2Monsters;
	}
	
	/**
	 * Pone a FALSE el campo que especifica si debe actualizarse el listado general de monstruos.
	 */
	public static void resetGeneralMonsters() {
		updateGeneralMonsters = false;
	}
	
	/**
	 * Pone a FALSE el campo que especifica si debe actualizarse el listado de monstruos del Acto 1.
	 */
	public static void resetAct1Monsters() {
		updateAct1Monsters = false;
	}
	
	/**
	 * Pone a FALSE el campo que especifica si debe actualizarse el listado de monstruos del Acto 2.
	 */
	public static void resetAct2Monsters() {
		updateAct2Monsters = false;
	}
	
	/**
	 * Cuando la pantalla de preferencias se muestra, se registra el listener que escucha los
	 * cambios en las preferencias.
	 */
	@Override
	protected void onResume() {
	    super.onResume();
	    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
	}

	/**
	 * Cuando se oculta la pantalla de preferencias, se des-registra el listener que escucha los
	 * cambios en las preferencias.
	 */
	@Override
	protected void onPause() {
	    super.onPause();
	    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
	}
}
