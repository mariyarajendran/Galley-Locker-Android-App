package com.example.l;

import java.security.PublicKey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	SharedPreferences sharedPreferences,preferences;
	Editor editor;
	EditText editlogin;
	Button btnlogin,btnchangepa;
	int btncount=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		editlogin=(EditText)findViewById(R.id.editlogin);
		btnlogin=(Button)findViewById(R.id.btnlogin);
		btnchangepa=(Button)findViewById(R.id.btnchangepa);
		
		
		
		sharedPreferences=getApplicationContext().getSharedPreferences("Registeration", 0);
		//editor=sharedPreferences.edit();
		preferences=getApplicationContext().getSharedPreferences("Security", 0);
		
		
final String	txtpassdemo=	sharedPreferences.getString("Regpass", null);
final String phon1=preferences.getString("cell1", null);
final String phone2=preferences.getString("cell2", null);





btnlogin.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		String editlog=editlogin.getText().toString();
		
		btncount=btncount+1;
		
		if(editlog.equalsIgnoreCase("")){
			
			Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_LONG).show();

		}
		
		
		else if(editlog.equals(txtpassdemo)){
			
			editlogin.setText("");
			Intent intent=new Intent(Login.this,L.class);
		startActivity(intent);
		finish();
			//Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
			
			
		}
		
		
		
		
		else{
			
			if(btncount==3){
			try{
				SmsManager sms = SmsManager.getDefault();
				String message = "Someone try to login in your gallery locker.so be carefull";
				String numbers[] = {phon1, phone2};
				for(String number : numbers) {
					  sms.sendTextMessage(number, null, message, null, null);
					}
		
			}
			
			catch(Exception e){
				e.printStackTrace();
			}}
			
			else{
				Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
				
			}
		}
		
		
		
		
	}
});




btnchangepa.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
		Intent intent=new Intent(Login.this,ChangePassword.class);
		startActivity(intent);
		
	}
});
		

		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	
	
	
}
