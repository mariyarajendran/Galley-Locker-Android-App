package com.example.l;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.R.integer;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.OnMenuVisibilityListener;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

public class LL extends Activity {
Button image,pick,videoll,audio;
ImageView imageview1;
String folder="NewFolder";
File[] allFile;
ArrayList<String> f = new ArrayList<String>();

ArrayList<String> fake = new ArrayList<String>();
File ImageFolder,VideoFolder;
int c=0;
GridView gridView;
private ImageAdapter imageAdapter;
private Bitmap[] thumbnails;
Bitmap bitmap;
int count=0;


private static final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ll);
		
		image=(Button)findViewById(R.id.image);
		pick=(Button)findViewById(R.id.pick);
		imageview1=(ImageView)findViewById(R.id.imageView1);
		videoll=(Button)findViewById(R.id.videoll);
		audio=(Button)findViewById(R.id.audio);
		
	gridView=(GridView)findViewById(R.id.gridView1);
	
  
	
    
	 ImageFolder = new File(Environment.getExternalStorageDirectory(), "HideImage");
	if (!ImageFolder.exists()) {
		ImageFolder.mkdirs();
	}
	
	/*VideoFolder = new File(Environment.getExternalStorageDirectory(), "HideVideo");
	if (!VideoFolder.exists()) {
		VideoFolder.mkdirs();
	}*/
	
		sd();
		imageAdapter = new ImageAdapter();
	    gridView.setAdapter(imageAdapter);
	  
	  
	    
	    gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
	    gridView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
				
				MenuInflater inflater=arg0.getMenuInflater();
				inflater.inflate(R.menu.menucustom, arg1);
				
				
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
				// TODO Auto-generated method stub
				
			 switch (arg1.getItemId()) {
			      case R.id.hide:
			       
			    	  for(String msg:fake){
			    		  File source = new File(msg);
			    		  Uri imageUri = Uri.fromFile(source);
			    		  String filenames=imageUri.getLastPathSegment();
			    		  File destination = new File(ImageFolder, filenames);
			               destination.list();
			               addImageGallery(destination);
			                      try {
					    		 	move(source, destination);					
								    source.delete();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), "Exception1", Toast.LENGTH_SHORT).show();
							}
			                 	    	  }
			    	 
			    	  Toast.makeText(getApplicationContext(),count+ " Image Unhide Successfully", Toast.LENGTH_LONG).show();
			    	  count=0;
			    	  arg0.finish();
			        return true;
			      			      default:
			    	  		        return false;
			    }
							}
			
			
			@Override
			public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2,
					boolean arg3) {
			
				count=count+1;
				
			arg0.setTitle(count +" Image Selected");
				
				fake.add(f.get(arg1));
				
			}
			
	
		});
	    
		
		pick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(LL.this,L.class);
				startActivity(i);
			}
		});
		
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			}
		});
			   
					
			
		/*	videoll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					
					Intent intent=new Intent(LL.this,Videoview.class);
					startActivity(intent);
				}
			});
			
			    
			    audio.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(LL.this,Videoview.class);
						startActivity(intent);
					}
				});*/
		
		 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ll, menu);
		
		
		return true;
	}
	
	
	private void addImageGallery( File file ) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // setar isso
	    getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	
	@SuppressWarnings("unused")
	private void addVideoGallery( File file ) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp3"); // setar isso
	    getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
	}
	
	
	public class ImageAdapter extends BaseAdapter {
	    private LayoutInflater mInflater;
	    private Context mContext;

	    public ImageAdapter() {
	        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	
	    	//mContext = c;
	    }

	    public int getCount() {
	        return f.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	        if (convertView == null) {
	            holder = new ViewHolder();
	            convertView = mInflater.inflate(
	                    R.layout.galleryitem, null);
	            holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);

	            convertView.setTag(holder);
	        }
	        else {
	            holder = (ViewHolder) convertView.getTag();
	        }

	        
	    // Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
	        
	        Bitmap myBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(f.get(position)),200, 200);
	        
	        holder.imageview.setImageBitmap(myBitmap);
	        
	    	
	        
	        return convertView;
	    }
	}
	class ViewHolder {
	    ImageView imageview;


	}
	
	
	
	
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
	
	public void sd(){
		
		 ContextWrapper cw = new ContextWrapper(this);
		File    path = cw.getDir("ImageFolder", Context.MODE_PRIVATE);
		   
		    if (!path.exists()) {
		        try {
					path.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        path.mkdir();
		        
		    }
		   
		
	      allFile = path.listFiles();
	      
	      for (int i = 0; i < allFile.length; i++)
          {
	    	 
						
              f.add(allFile[i].getAbsolutePath());

          }
		}
	


	
	/*public void video(){
		
		 ContextWrapper cw = new ContextWrapper(this);
		File    path = cw.getDir("VideoFolder", Context.MODE_PRIVATE);
		   
		    if (!path.exists()) {
		        try {
					path.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        path.mkdir();
		        
		    }
		    
		    
		
		      allFile = path.listFiles();
	      
	      for (int i = 0; i < allFile.length; i++)
         {
	    	 
						
             f.add(allFile[i].getAbsolutePath());

         }
		}
	*/
	
	
	}

