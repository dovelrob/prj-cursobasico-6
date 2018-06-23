package com.example1.imc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoIMC extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	}
	
	public void meterDatos (View view){
		Intent i = new Intent(this, IntroDatos.class);
		startActivity(i);
		
		
	}
}
	
