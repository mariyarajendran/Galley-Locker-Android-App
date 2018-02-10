package com.example.l;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity {
EditText editoldpass,editconfirm,editnewpass;
Button btnpasschange;

SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		editoldpass=(EditText)findViewById(R.id.editoldpass);
		editnewpass=(EditText)findViewById(R.id.editnewpass);
		editconfirm=(EditText)findViewById(R.id.editconfirm);
		btnpasschange=(Button)findViewById(R.id.btnpasschange);
		
		sharedPreferences=getApplicationContext().getSharedPreferences("Registeration", 0);
		
final String	txtpassdemo=	sharedPreferences.getString("Regpass", null);


		
btnpasschange.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	
String oldpass=editoldpass.getText().toString();
String newpass=editnewpass.getText().toString();
String confirmpass=editconfirm.getText().toString();


if(oldpass.equalsIgnoreCase("")||newpass.equalsIgnoreCase("")||confirmpass.equalsIgnoreCase("")){
	
	Toast.makeText(getApplicationContext(),"Fill all fields", Toast.LENGTH_LONG).show();
	
}


else if(oldpass.equals(txtpassdemo)&&newpass.equals(confirmpass)){
	
	Editor editor=sharedPreferences.edit();	
	editor.remove("Regpass");
	editor.clear();
	editor.commit();
	
	
	
	editor.putString("Regpass", newpass);
	editor.commit();

	Toast.makeText(getApplicationContext(),"Password changed successfully", Toast.LENGTH_LONG).show();
	
	
	
}
	
	else{
		
		if(!oldpass.equals(txtpassdemo)){
			editoldpass.setText("");
		Toast.makeText(getApplicationContext(),"Old pasword wrong", Toast.LENGTH_LONG).show();}
		
		else{
			editconfirm.setText("");
			Toast.makeText(getApplicationContext(),"Confirm pasword wrong", Toast.LENGTH_LONG).show();
		}
		
	}
	


		
	}
});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}

}
