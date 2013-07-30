package com.gmerin.descentmonsters.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {

	private Activity context;
	private SharedPreferences prefs;
	
	public PreferencesHelper(Activity context) {
		this.context = context;
		this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public String getExpansionsToFilter() {
		ArrayList<String> lista = new ArrayList<String>();

		if (!prefs.getBoolean("show_expan_JitD1", true)) lista.add("JitD1");
		if (!prefs.getBoolean("show_expan_WoD", true)) lista.add("WoD");
		if (!prefs.getBoolean("show_expan_AoD", true)) lista.add("AoD");
		if (!prefs.getBoolean("show_expan_ToI", true)) lista.add("ToI");
		if (!prefs.getBoolean("show_expan_JitD2", true)) lista.add("JitD2");
		if (!prefs.getBoolean("show_expan_LotW", true)) lista.add("LotW");
		if (!prefs.getBoolean("show_expan_LoR", true)) lista.add("LoR");
		
		return stringListToString(lista);
	}
	
	public String getTraitsToFilter() {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		
		// Hay 10 rasgos
		for(int i=1; i<=10; i++)
		{
			if(!prefs.getBoolean("show_trait_"+i, true)) lista.add(i);
		}
		
		return intListToString(lista);
	}
	
	public String getAttackTypesToFilter() {
		ArrayList<String> lista = new ArrayList<String>();
		
		if (!prefs.getBoolean("show_attack_cc", true)) lista.add("cc");
		if (!prefs.getBoolean("show_attack_ad", true)) lista.add("ad");
		
		return stringListToString(lista);
	}
	
	private static String stringListToString(ArrayList<String> lista) {
		String cad = "";
		if (lista == null) return cad;
		
		for(int i=0; i<lista.size(); i++)
		{
			cad+="'"+lista.get(i)+"'";
			if (i < lista.size()-1)
				cad+=",";
		}
		
		return cad;
	}
	
	private static String intListToString(ArrayList<Integer> lista) {
		String cad = "";
		if (lista == null) return cad;
		
		for(int i=0; i<lista.size(); i++)
		{
			cad+=lista.get(i);
			if (i < lista.size()-1)
				cad+=",";
		}
		
		return cad;
	}
}
