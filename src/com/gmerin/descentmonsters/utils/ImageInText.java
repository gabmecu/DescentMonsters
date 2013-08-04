package com.gmerin.descentmonsters.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
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
}
