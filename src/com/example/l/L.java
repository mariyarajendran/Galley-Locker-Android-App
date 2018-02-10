package com.example.l;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class L extends Activity {
	 private static final int SELECT_PICTURE = 1;
	 private static final int SELECT_AUDIO = 2;
	 private static final int SELECT_VIDEO = 3;
	 private static final int SELECT_DOCU = 4;
	    private String selectedImagePath;
	    String folder_main = "NewFolder";
	    Button images,audio,video,doc;
	    Context context;
	    int count=0,c=0;
	   
	    ImageView image;
	   File path,audiopath,videopath,docpath,dbimage;
	 
	    private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_l);
		images=(Button)findViewById(R.id.image);
	//	image=(ImageView)findViewById(R.id.imagee);
		audio=(Button)findViewById(R.id.audio);
		video=(Button)findViewById(R.id.video);
		doc=(Button)findViewById(R.id.docu);
		
		
		
		 dbimage = new File(Environment.getExternalStorageDirectory(), "ImageDb");
			if (!dbimage.exists()) {
				dbimage.mkdirs();
			}
			
		   image();
    // audio();
     //    video();
      //   document();
      
		
		images.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);                   // EXTERNAL_CONTENT_URI
								startActivityForResult(i, SELECT_PICTURE);
			
			  
			}
		});
		
		
	/*	audio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);                   // EXTERNAL_CONTENT_URI
								startActivityForResult(i, SELECT_AUDIO);
				
			}
		});*/
		
		
   /*doc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
		//Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	           // intent.addCategory(Intent.CATEGORY_OPENABLE);
	Intent	 intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        
	          //  Intent i = Intent.createChooser(intent, "File");
	            startActivityForResult(intent, SELECT_DOCU); 
			}
		});
		
		
		video.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);                   // EXTERNAL_CONTENT_URI
							startActivityForResult(i, SELECT_VIDEO);
				
			
				
			}
		});
	
		*/
		
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
            	
            	
                Uri selectedImageUri = data.getData();
                            //  image.setImageURI(selectedImageUri);
              Toast.makeText(getApplicationContext(), "Image Picked", Toast.LENGTH_SHORT).show();
           
      	    String   src = getPath(data.getData());
                                         
              
               File source = new File(src);
             
               String filename = selectedImageUri.getLastPathSegment();
               
             
                File destination = new File(path, filename+".jpg");
                File dbdestination = new File(dbimage, filename+".txt");
               destination.list();
           
		    	//Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
		    	try {
		    		
		    		// Reading a Image file from file system
					
					
		    		
					move(source, destination);
					move(source,dbdestination );
					
					ContentResolver contentResolver = getContentResolver();
					contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					            MediaStore.Images.ImageColumns.DATA + "=?" , new String[]{ src });
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					Toast.makeText(getApplicationContext(), "Exception1", Toast.LENGTH_SHORT).show();
				}
			  
            }
            
            
            
        /*    if(requestCode==SELECT_AUDIO){
            	
            	 Uri audiouri = data.getData();
            	 
            	  String   src = getPathaudio(data.getData());
                  
                  
                  File source = new File(src);
                
                  String filename = audiouri.getLastPathSegment();
                  
                
                   File destination = new File(audiopath, filename+".mp3");
                  destination.list();
              
   		    	Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
   		    	try {
   		    		
   					move(source, destination);
   					ContentResolver contentResolver = getContentResolver();
   					contentResolver.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
   					            MediaStore.Audio.AudioColumns.DATA + "=?" , new String[]{ src });
   					
   				} catch (IOException e) {
   					// TODO Auto-generated catch block
   					
   					Toast.makeText(getApplicationContext(), "Exception1", Toast.LENGTH_SHORT).show();
   				}
            }
            	 
            	 
            	 if(requestCode==SELECT_VIDEO){
            		 
            		 Uri selectedImageUri = data.getData();
                     image.setImageURI(selectedImageUri);
            		 
            		 Uri videouri = data.getData();
                	 
               	  String   src = getPathvideo(data.getData());
                     
                     
                     File source = new File(src);
                   
                     String filename = videouri.getLastPathSegment();
                     
                   
                      File destination = new File(videopath, filename+".mp4");
                     destination.list();
                 
      		    	Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
      		    	try {
      		    		
      					move(source, destination);
      					ContentResolver contentResolver = getContentResolver();
      					contentResolver.delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
      					            MediaStore.Video.VideoColumns.DATA + "=?" , new String[]{ src });
      					
      				} catch (IOException e) {
      					// TODO Auto-generated catch block
      					
      					Toast.makeText(getApplicationContext(), "Exception1", Toast.LENGTH_SHORT).show();
      				}
            		 
            		 
            	 }
            	 
            	
            if(requestCode==SELECT_DOCU){
            	
            	
            	 Uri docuri = data.getData();
            	 
           	  String   src = getPathdocument(data.getData());
                 
                 
                 File source = new File(src);
               
                 String filename = docuri.getLastPathSegment();
                 
               
                  File destination = new File(docpath, filename);
                 destination.list();
             
  		    	Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
  		    	try {
  		    		
  					move(source, destination);
  					ContentResolver contentResolver = getContentResolver();
  					contentResolver.delete(MediaStore.Files.getContentUri(src),
					            MediaStore.Files.FileColumns.DATA + "=?" , new String[]{ src });
  					
  				} catch (IOException e) {
  					// TODO Auto-generated catch block
  					
  					Toast.makeText(getApplicationContext(), "Exception1", Toast.LENGTH_SHORT).show();
  				}
            	
            	
            }*/
        }
    }
	
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		c+=1;
		
		if(c==1){

	Toast.makeText(getApplicationContext(), "Back pressed", Toast.LENGTH_SHORT).show();}
		
		else if(c==2){
			finish();
		}
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	if(item.getItemId()==R.id.pick){
	Intent intent=new Intent(L.this,LL.class);
	//finish();
	startActivity(intent);}
	return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.l, menu);
		return true;
	}
	
	
	public String getPath(Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
}
	
	
	
	/*public String getPathaudio(Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Audio.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
}
	
	
	public String getPathvideo(Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
}
	
	public String getPathdocument(Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
}*/
	
	
	

	
	public void move(File sr,File ds) throws IOException{
		try {
			InputStream  		inStream = new FileInputStream(sr);
			OutputStream     outStream = new FileOutputStream(ds);
			
			 byte[] buffer = new byte[1024];
	    		
	    	    int length;
	    	    //copy the file content in bytes 
	    	    while ((length = inStream.read(buffer)) > 0){
	    	  
	    	    	outStream.write(buffer, 0, length);}
	    	    
		 
   	 
	    inStream.close();
	    outStream.close();
	     
	       System.out.println("File is copied successful!");
	    }
	    	    
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}
		
	
	
	
	public void image(){
		
		 ContextWrapper cw = new ContextWrapper(this);
		    path = cw.getDir("ImageFolder", Context.MODE_PRIVATE);
		   
		    if (!path.exists()) {
		        try {
					path.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        path.mkdir();
		        
		    }}
	
	
		    
		 /*   public void audio(){
				
				 ContextWrapper cw = new ContextWrapper(this);
				    audiopath = cw.getDir("AudioFolder", Context.MODE_PRIVATE);
				   
				    if (!audiopath.exists()) {
				        try {
							audiopath.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        audiopath.mkdir();
				        
				    }}
		    
				    
				    public void video(){
						
						 ContextWrapper cw = new ContextWrapper(this);
						   videopath = cw.getDir("VideoFolder", Context.MODE_PRIVATE);
						   
						    if (!videopath.exists()) {
						        try {
									videopath.createNewFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        videopath.mkdir();
						        
						    }}
				    
		    
				    public void document(){
						
						 ContextWrapper cw = new ContextWrapper(this);
						   docpath = cw.getDir("DocumentFolder", Context.MODE_PRIVATE);
						   
						    if (!docpath.exists()) {
						        try {
									docpath.createNewFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        docpath.mkdir();
						        
						    }}*/

	
	
	
	


}
