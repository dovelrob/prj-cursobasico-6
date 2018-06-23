package com.example1.imc;

import com.example1.imc.IntroDatos.ReceptorCalculadora;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Calculadora extends IntentService{
	int edad_usuario;
	float altura_usuario;
	float peso_usuario;
	int imc;
	int resultado;
	
	
	public Calculadora() {
		super("Calculadora");
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onHandleIntent(Intent i) {
		edad_usuario = i.getExtras().getInt("edad_usu");          
        altura_usuario = i.getExtras().getFloat("altura_usu");           
        peso_usuario = i.getExtras().getFloat("peso_usu");
        imc = (int) (peso_usuario/(altura_usuario*altura_usuario));
        
        //averiguamos si el imc esta dentro de los valores normales segun la edad
        if (edad_usuario>=19&&edad_usuario<=24){
        	if (imc>=19&&imc<=24){
        		resultado=0;
        	}
        	else if (imc<19){
        		resultado=1;
        	}
        	else{
        		resultado=2;
        	}
        }
        else if (edad_usuario>=25&&edad_usuario<=34){
        	if (imc>=20&&imc<=25){
        		resultado=0;
        	}
        	else if (imc<20){
        		resultado=1;
        	}
        	else{
        		resultado=2;
        	}
        }
        else if (edad_usuario>=35&&edad_usuario<=44){
        	if (imc>=21&&imc<=26){
        		resultado=0;
        	}
        	else if (imc<21){
        		resultado=1;
        	}
        	else{
        		resultado=2;
        	}
        }
        else if (edad_usuario>=45&&edad_usuario<=54){
        	if (imc>=22&&imc<=27){
        		resultado=0;
        	}
        	else if (imc<22){
        		resultado=1;
        	}
        	else{
        		resultado=2;
        	}
        }
        else if (edad_usuario>=55&&edad_usuario<=64){
        	if (imc>=23&&imc<=28){
        		resultado=0;
        	}
        	else if (imc<23){
        		resultado=1;
        	}
        	else{
        		resultado=2;
        	}
        }
        else if (edad_usuario>=65){
        	if (imc>=24&&imc<=29){
        		resultado=0;
        	}
        	else if (imc<24){
        		resultado=1;
        	}
        	else{
        		resultado=2;
        	}
        }
        
        //Enviamos el resultado a la actividad    
        Intent intent = new Intent();
        intent.setAction(ReceptorCalculadora.ACTION_RESP);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("imc", imc);
        intent.putExtra("resultado", resultado);
        sendBroadcast(intent);
	   }
	}

	
	