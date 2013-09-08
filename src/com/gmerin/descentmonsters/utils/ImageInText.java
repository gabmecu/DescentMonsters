package com.gmerin.descentmonsters.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class ImageInText {
	/**
	 * Busca, dentro del texto suministrado, el token que representa la imagen y lo sustituye
	 * por la imagen suministrada. Si no se encuentra el token, simplemente se asigna el texto al
	 * TextView pasado.
	 * 
	 * @param text Texto donde se encuentra un token
	 * @param imageToken Token que representa la imagen
	 * @param imageResourceId Identificador del recurso que representa la imagen
	 * @param txtView TextView donde vamos a realizar la inserción de la imagen
	 * @param context Contexto donde se realiza todo
	 */
	public static void setImageInText(String text, String imageToken, int imageResourceId, TextView txtView, Context context) {
		// Hay que encontrar donde comienza y donde acaba el token que representa la imagen
		// dentro del text
		int ini = text.indexOf(imageToken);
		
		// Si el token no se encuentra dentro de la cadena, no hacemos nada
		if (ini == -1) {
			txtView.setText(text);
			return;
		}
		
		int fin = ini+imageToken.length();
		
		SpannableStringBuilder ssb = new SpannableStringBuilder(text);
		ImageSpan imgSpan = new ImageSpan(context, imageResourceId, ImageSpan.ALIGN_BASELINE);
		ssb.setSpan(imgSpan, ini, fin, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		txtView.setText(ssb, BufferType.SPANNABLE);
	}
	
	
	public static void setImageInText(String text, TextView txtView, Context context) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(text);
		setImageInText(ssb, txtView, context);
	}
	
	/**
	 * Busca, dentro del texto suministrado, todos los posibles tokens de imagen y los sustituye
	 * por su correspondiente imagen. Si no se encuentra ningún token, simplemente se asigna el texto al
	 * TexView pasado.
	 * 
	 * @param text Texto donde se encuentran los tokens
	 * @param txtView Componente gráfico donde se va a mostrar el texto con las imágenes.
	 * @param context Contexto donde se realiza todo.
	 */
	public static void setImageInText(SpannableStringBuilder ssb, TextView txtView, Context context) {
		int ini = 0;
		int fin = 0;

		String text = ssb.toString();
		
		do {
			ini = text.indexOf(':', ini);
			fin = ini+4; // Asumimos que la estructura de los tokens es :xxx:
			
			// Si ya no hay más :, hemos acabado
			if (ini == -1 || fin>=text.length()) continue;
			
			// Si todo ha ido bien, nos quedamos con el token
			String token = text.substring(ini, fin+1);
			
			// Obtenemos la id del recurso gráfico
			int imageResourceId = DrawableHelper.getDrawableIDAbilityDesc(token);
			
			if (imageResourceId != -1) {
				// Creamos la imagen
				ImageSpan imgSpan = new ImageSpan(context, imageResourceId, ImageSpan.ALIGN_BASELINE);
				// Hacemos la sustitución del token por la imagen
				ssb.setSpan(imgSpan, ini, fin+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
			
			// Comenzamos donde hemos acabado
			ini = fin+1;
					
		} while (ini!=-1 && fin<text.length());
		
		// Ahora ya podemos asignar el texto al TextView
		txtView.setText(ssb, BufferType.SPANNABLE);
	}
}
