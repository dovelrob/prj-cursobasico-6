package com.example1.imc;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Inicio extends Activity {
	//private Button bInfo;
	private Button bSalir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio);
		//creamos escuchador de eventos para el boton salir
		bSalir = (Button) findViewById(R.id.salir);
		bSalir.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
		
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	//metodo para lanzar la actividad de información sobre IMC
	public void lanzarInfo (View view){
		Intent i = new Intent(this, InfoIMC.class);
		startActivity(i);
		
	}
	
	public void meterDatos (View view){
		Intent i = new Intent(this, IntroDatos.class);
		startActivity(i);
		
		
	}
	
}
