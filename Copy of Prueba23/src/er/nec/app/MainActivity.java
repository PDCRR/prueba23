package er.nec.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onClick(View v){
		System.out.println("prueba de boton");
		/**
		if(v.getId()==R.id.btnLinkScreen){
			Screen = new Intent(this, Conexion.class );
			startActivity(Screen);
			overridePendingTransition(R.animator.transtion1, R.animator.transition2);
		}
		else if(v.getId()== R.id.btnHelp){
			Screen = new Intent(this, HelpScreen.class);
			startActivity(Screen);
			overridePendingTransition(R.animator.transtion1, R.animator.transition2);
		}
		*/
	}
}
