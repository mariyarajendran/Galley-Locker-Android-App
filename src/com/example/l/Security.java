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
import android.widget.Toast;

public class Security extends Activity {
EditText editphone1,editphone2;
Button btnsave;
SharedPreferences preferences;
Editor editorsecure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security);
		editphone1=(EditText)findViewById(R.id.editphone1);
		editphone2=(EditText)findViewById(R.id.editphone2);
		btnsave=(Button)findViewById(R.id.btnsave);
		
		
		preferences=getApplicationContext().getSharedPreferences("Security", 0);
		editorsecure=preferences.edit();
		
		btnsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String phone1=editphone1.getText().toString();
				String phone2=editphone2.getText().toString();
				if(phone1.equalsIgnoreCase("")&&phone2.equalsIgnoreCase("")){
					
					Toast.makeText(getApplicationContext(), "Fill atleast one mobile number", Toast.LENGTH_LONG).show();
					
				}
				
				else{
					editorsecure.putString("cell1", phone1);
					editorsecure.putString("cell2", phone2);
					editorsecure.commit();
						
					
					editphone1.setText("");
					editphone2.setText("");
					
					//Toast.makeText(getApplicationContext(), "Shared complete", Toast.LENGTH_LONG).show();
					
					Intent intent=new Intent(Security.this,L.class);
					startActivity(intent);
					finish();
						
					
				}
				
			
				
				
			}
		});
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.security, menu);
		return true;
	}

}
