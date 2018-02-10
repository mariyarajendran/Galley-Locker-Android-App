package com.example.l;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {
EditText editcreate;
Button btncreate;
SharedPreferences sharedPreferences;
Editor editor;
TextView textView;
String password,txtpassdemo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		textView=(TextView)findViewById(R.id.textView1);
		
		editcreate=(EditText)findViewById(R.id.edtcreate);
		btncreate=(Button)findViewById(R.id.btncreate);
		
		
		sharedPreferences=getApplicationContext().getSharedPreferences("Registeration", 0);
		editor=sharedPreferences.edit();
		
		
		txtpassdemo=	sharedPreferences.getString("Regpass", null);
		
		textView.setText(txtpassdemo);
		final String txtpass=textView.getText().toString();
		
		
		if(txtpass.equals("")){
				
		btncreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				 password=editcreate.getText().toString();
   
				if(password.equalsIgnoreCase("")){
					
					Toast.makeText(getApplicationContext(), "Fill fields", Toast.LENGTH_LONG).show();
					
					
				}
				
				else{
					
				editor.putString("Regpass", password);
				editor.commit();
				
				
				
				editcreate.setText("");
				
		
		
		Toast.makeText(getApplicationContext(), "Locker Create", Toast.LENGTH_LONG).show();
				
	
				Intent intent=new Intent(Registration.this,Security.class);
				startActivity(intent);
				finish();
				
				}
				
			}
		});
		}
		
		else{
			Intent intent=new Intent(Registration.this,Login.class);
			startActivity(intent);
			finish();
			
		}
		
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
