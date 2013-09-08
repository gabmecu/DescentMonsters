package com.gmerin.descentmonsters.reflective;

import java.lang.reflect.Field;

import com.gmerin.descentmonsters.R;

public class ReflectiveHelper {

	public static int getRStringID(String fieldName) {
		int id;
		try {
			// Nos quedamos con el campo solicitado
			Field campo = R.string.class.getField(fieldName);
			// Si el campo es privado, lo hacemos accesible
			campo.setAccessible(true);
			// Obtenemos el valor del campo que es un entero
			id = campo.getInt(new R.string());
		} catch (Exception e) {
			// Si no se ha encontrado el campo o el valor no era entero, devolvemos -1
			id = -1;
		}
		
		return id;
	}
	
	public static int getRDrawableID(String fieldName) {
		int id;
		try {
			// Nos quedamos con el campo solicitado
			Field campo = R.drawable.class.getField(fieldName);
			// Si el campo es privado, lo hacemos accesible
			campo.setAccessible(true);
			// Obtenemos el valor del campo que es un entero
			id = campo.getInt(new R.string());
		} catch (Exception e) {
			// Si no se ha encontrado el campo o el valor no era entero, devolvemos -1
			id = -1;
		}
		
		return id;		
	}
}
