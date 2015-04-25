package er.nec.prueba2;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * clase para las acciones que se deben de tomar cuando se juega
 * @author Ellioth R & Gerald M
 *
 */
public class Juego extends Activity implements OnItemSelectedListener, SensorEventListener, constantes{
	
	
	private StringBuilder sb =new StringBuilder(); //variable para crear mensaje de salida
	private String MensajeOUT="",MensajeIN ="";//contiene los mensajes que salen y entran
	private String frSpaces="N", ScdSpaces="N", shoot="f",bala="1";//contiene las acciones tomadas en el juego
	private double ActX, ActZ, ActY;
    private MediaPlayer sonido;
    private Intent ScreenChange;
	
    /**
     * inicializador de la clase de juego
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_juego1);
		Spinner spinner = (Spinner) findViewById(R.id.tipeShoot);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.shoots, android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	/**
	 * metodo para detectar si se a apretado
	 * @param v
	 */
	public void onClick(View v){
        sonido = MediaPlayer.create(Juego.this, R.raw.disparo_1);

        sonido.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            };
        });



        System.out.println("***********************************************************");
        sonido.start();
		if(v.getId()==R.id.btnShoot){
			shoot="t";
            sonido.start();
			builderStr();
			cliente.sendMsj(MensajeOUT);
			shoot="false";
			//sonido = MediaPlayer.create(Juego.this, R.raw.disparo_1);
			//sonido.start();

		}


	}

	
	/**
	 * metodo para desarrollar la accion cuando se toca el boton atras
	 */
	@Override
	public void onBackPressed(){
		cliente.salir();
		MensajeOUT="";
		bala="1";
		frSpaces="N";
		ScdSpaces="N";
		shoot="f";
		sb.delete(0,sb.length());
		finish();
		//ScreenChange= new Intent(this, MainActivity.class);
		//startActivity(ScreenChange);
		overridePendingTransition(R.animator.transtion1, R.animator.transition2);
	}
	
	/**
	 * metodo para hacer que se forme el string del mensaje de salida 
	 */
 	private void builderStr(){
		sb.append(frSpaces);
		sb.append(",");
		sb.append(ScdSpaces);
		sb.append(",");
		sb.append(shoot);
		sb.append(",");
		sb.append(bala);
		MensajeOUT=sb.toString();
		sb.delete(0,sb.length());
		System.out.println(MensajeOUT);
	}
	
 	/**
 	 * metodo que recibi que opcion de disparo se a seleccionado
 	 */
	public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
		TextView weaponChoose= (TextView) view;
		if((weaponChoose.getText().toString()).equals("Normal"))
			bala="1";
		else if((weaponChoose.getText().toString()).equals("Pro"))
			bala="2";
		else if((weaponChoose.getText().toString()).equals("Difusion"))
			bala="3";
		else if((weaponChoose.getText().toString()).equals("Mayhem"))
			bala="4";
    }

	/**
	 * que hacer cuando no haya nada seleccionado
	 */
    public void onNothingSelected(AdapterView<?> parent){ 
    
    }

    /**
     * metodo sbreescrito para saber que hacer cuando de el sensor del acelerometro
     * se  reciben los datos
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
    	synchronized (this) {
    		ActX = event.values[0];
		    //ActY = event.values[1];
		    ActZ = event.values[2];
		    if((ActX - ConsX)>1)
		    	ScdSpaces="i";
		    else if ((ActX - ConsX)<-1)
		    	ScdSpaces="d";
            else
                ScdSpaces="null";
		    if((ActZ - ConsZ)<-0.5)
		    	frSpaces="a";
		    else if((ActZ - ConsZ)>0.5)
		    	frSpaces="A";
            else
                frSpaces="null";
		    builderStr();
		    cliente.sendMsj(MensajeOUT);
		}
    }

    /**
     * metodo para saber que se hace cuando esta en resumen la app
     */
	@Override
    protected void onResume(){
        super.onResume();
        SensorManager sm= (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors= sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size()>0){
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
            
        }
    }
	
	/**
	 * metodo para detener la optencion de datos
	 */
    @Override
    protected void onStop() {

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE); 
        sm.unregisterListener(this);
        super.onStop();
    }
    

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
