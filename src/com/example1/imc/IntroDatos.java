package com.example1.imc;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IntroDatos extends Activity{
	public static TextView resultados, datos;	
	public int IMC=0;
	public Button calcular, consejo;
	public ImageView figura;
	public EditText pes, alt, eda;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datos);
		//asociamos las vistas
		resultados=(TextView)findViewById(R.id.resultado_calculos);
		datos=(TextView)findViewById(R.id.datos_usuario);
		calcular=(Button)findViewById(R.id.calculadora);
		consejo=(Button)findViewById(R.id.solucion);
		figura = (ImageView) findViewById(R.id.silueta); 
		pes = (EditText)findViewById(R.id.peso_usuario);
    	alt = (EditText)findViewById(R.id.altura_usuario);
    	eda = (EditText)findViewById(R.id.edad_usuario);
		
		
		//Aviso de que la app solo es fiable con edades superiores a 19 años
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.importante);
        builder.setMessage(R.string.mensaje);
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();  
		
		
		//asociamos un anuncio broadcast a nuestro receptor de anuncios
		 IntentFilter filtro = new IntentFilter(ReceptorCalculadora.ACTION_RESP);
		 filtro.addCategory(Intent.CATEGORY_DEFAULT);
		 registerReceiver(new ReceptorCalculadora(), filtro);
		
		
	}
	
	/*tenemos que crear un método para que al pulsar el bton calcular
	 * se llame al servicio pasandole los valores insertados por el usuario
	 */
	
	public void calculoImc (View view){
		//ponemos a 0 los datos		
		datos.setText("");
		resultados.setText("");
		//comprobamos que ha escrito algo
		String peso_texto, altura_texto, edad_texto;
		peso_texto=pes.getText().toString();
		altura_texto=alt.getText().toString();
		edad_texto=eda.getText().toString();
		//Si no se ha rellenado alguno saldrá de la funcion
		if (peso_texto.trim().equals("")||altura_texto.trim().equals("")||edad_texto.trim().equals("")){
			Toast.makeText(IntroDatos.this, R.string.error, Toast.LENGTH_SHORT).show();
			return;
		}
    	
    	//guardamos en variables los datos introducidos por el usuario
    	int edad = Integer.parseInt(eda.getText().toString());  
    	int altura = Integer.parseInt(alt.getText().toString());
    	//convertimos la altura a float
    	float altura_m = (float) (altura/100.00);   	
    	float peso = Float.parseFloat(pes.getText().toString());

    	//Escondemos el teclado
    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(calcular.getWindowToken(), 0);
    	
    	//Creamos una nueva intención con nuestro contexto y la clase Calculadora
    	Intent intent = new Intent (IntroDatos.this, Calculadora.class);
    	//incluimos los datos que enviaremos al servicio
    	intent.putExtra("edad_usu", edad);
    	intent.putExtra("altura_usu", altura_m);
    	intent.putExtra("peso_usu", peso);
    	//Arrancamos servicio
    	startService(intent);
    	
    	//Borramos los datos introducidos
    	pes.setText("");
    	alt.setText("");
    	eda.setText("");
    	datos.append("Edad: "+edad+" años\n"+"Peso: "+peso+" kg.\n"+"Altura: "+altura_m+" m.\n");
    				
	}
	
	/*Una vez que el servicio ha concluido su trabajo queremos que avise a esta actividad
	 *  y le devolva el valor resultante. Lo haremos medio de un anuncio broadcast.
	 */
	public class ReceptorCalculadora extends BroadcastReceiver {

	      public static final String ACTION_RESP=  
	     "com.example1.imc.intent.action.RESPUESTA_OPERACION";

	      @Override
	      public void onReceive(Context context, Intent intent) {
	         //recibimos el resultado del cálculo
	         int imc_usuario = intent.getIntExtra("imc", 0);
	         int resultado_usuario = intent.getIntExtra("resultado", 3);
	         
	         if(resultado_usuario==0){
	        	 resultados.append("Su Índice de masa corporal es: "+ imc_usuario +", y está dentro de los límites normales");	      
	        	 figura.setVisibility(View.VISIBLE);
	        	 figura.setImageResource(R.drawable.normal);
	        	 
	        	 
	         }
	         else if (resultado_usuario==1){
	        	 resultados.append("Su Índice de masa corporal es: "+ imc_usuario + ", está por debajo de los límites normales. Necesita ganar peso");
	        	 //activar boton que lo lleve a dietas hipercalóricas	        	 
	        	 consejo.setVisibility(View.VISIBLE);	
	        	 consejo.setText("Recomendaciones");
	        	 figura.setVisibility(View.VISIBLE);
	        	 figura.setImageResource(R.drawable.infrapeso);
	        	 
	         }
	         else if(resultado_usuario==2){
	        	 resultados.append("Su Índice de masa corporal es: "+ imc_usuario +", está por encima de los límites normales. Necesita perder peso");
	        	 //activar boton con dietas hipocalóricas
	        	 consejo.setVisibility(View.VISIBLE);
	        	 consejo.setText("Dietas hipocalóricas");
	        	 figura.setVisibility(View.VISIBLE);
	        	 figura.setImageResource(R.drawable.sobrepeso);
	         }
	         

	      }
	      	      	      
	      	      
	 }
	
	
	//Creamos un método que nos permita mostrar una página web al usuario
    public void mostrarWeb (View view){
  	  if(consejo.getText().equals(R.string.recomendaciones)){
  		  Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://www.fisterra.com/salud/2dietas/dietaHipercalorica.asp"));
  		  startActivity(intent);
  	  }
  	  else{
  		  Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://www.fisterra.com/salud/2dietas/dieta_1500.asp"));
  		  startActivity(intent);
  	  }
    }  
}
