package com.example.l;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Videoview extends Activity {
	 
	
	Button unhidevid,audiounhide;
	File    vidpath,audipath,VideoFolderr,audiofolderr;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_view);
		
		unhidevid=(Button)findViewById(R.id.unhidevid);
		audiounhide=(Button)findViewById(R.id.audiounhide);
		
		
		
		
	  VideoFolderr = new File(Environment.getExternalStorageDirectory(), "PressureVideo");
			if (!VideoFolderr.exists()) {
				VideoFolderr.mkdirs();
			}
			
			
			audiofolderr = new File(Environment.getExternalStorageDirectory(), "PressureAudio");
				if (!audiofolderr.exists()) {
					audiofolderr.mkdirs();
				}
		
		 ContextWrapper cw = new ContextWrapper(this);
		    vidpath = cw.getDir("VideoFolder", Context.MODE_PRIVATE);
			   
			    if (!vidpath.exists()) {
			        try {
						vidpath.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        vidpath.mkdir();
			        
			    }
			      
			    ContextWrapper ca = new ContextWrapper(this);
			    audipath = ca.getDir("AudioFolder", Context.MODE_PRIVATE);
				   
				    if (! audipath.exists()) {
				        try {
				        	 audipath.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        audipath.mkdir();
				        
				    }
				      
					
			    
			    unhidevid.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						
						try {
							copyDirectoryOneLocationToAnotherLocation(vidpath, VideoFolderr);
							
							//addVideoGallery(VideoFolderr);
							
							
							 String[] children = vidpath.list();
							    for (int i = 0; i < children.length; i++)
							    { 
							       new File(vidpath, children[i]).delete();
							    }
							Toast.makeText(getApplicationContext(), "Moved", Toast.LENGTH_SHORT).show();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			    
			    
			    
			    
			    audiounhide.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						
						try {
							copyaudio(audipath, audiofolderr);
							
							//addVideoGallery(VideoFolderr);
							
							
							 String[] children = audipath.list();
							    for (int i = 0; i < children.length; i++)
							    { 
							       new File(audipath, children[i]).delete();
							    }
							Toast.makeText(getApplicationContext(), "audio Moved", Toast.LENGTH_SHORT).show();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			    
			
	
			   
		
				
	
	}
				
				
				
		
	

	public  void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
		    throws IOException {

		if (sourceLocation.isDirectory()) {
		    if (!targetLocation.exists()) {
		        targetLocation.mkdir();
		    }

		    String[] children = sourceLocation.list();
		    for (int i = 0; i < sourceLocation.listFiles().length; i++) {

		        copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
		                new File(targetLocation, children[i]));
		        
		        
		        addVideoGallery( new File(targetLocation, children[i]));
		        
		      
		    }
		} else {

		    InputStream in = new FileInputStream(sourceLocation);

		    OutputStream out = new FileOutputStream(targetLocation);

		    // Copy the bits from instream to outstream
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}

		}
	
	
	
	public  void copyaudio(File sourceLocation, File targetLocation)
		    throws IOException {

		if (sourceLocation.isDirectory()) {
		    if (!targetLocation.exists()) {
		        targetLocation.mkdir();
		    }

		    String[] children = sourceLocation.list();
		    for (int i = 0; i < sourceLocation.listFiles().length; i++) {

		        copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
		                new File(targetLocation, children[i]));
		        
		        
		        addAudioGallery( new File(targetLocation, children[i]));
		        
		      
		    }
		} else {

		    InputStream in = new FileInputStream(sourceLocation);

		    OutputStream out = new FileOutputStream(targetLocation);

		    // Copy the bits from instream to outstream
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}

		}
	
	
	@SuppressWarnings("unused")
	private void addVideoGallery( File file ) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Video.Media.MIME_TYPE, "video"); // setar isso
	    getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
	}
	  
	
	@SuppressWarnings("unused")
	private void addAudioGallery( File file ) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Audio.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3"); // setar isso
	    getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
	}

	
}
