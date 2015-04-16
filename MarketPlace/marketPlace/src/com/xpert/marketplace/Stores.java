package com.xpert.marketplace;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Stores extends Activity {

	String[] titlesArray;
	String[] descriptionArray;
	String [] Companynames;
	String [] Offers;
	int Ids[];
	
											//GETTING ALL THE IMAGES FROM THE DRAWABLE FOLDER
	
	int Images[]={
			R.drawable.admission,
			R.drawable.canteen,
			R.drawable.contacts,
			R.drawable.courses,
			R.drawable.events,
			R.drawable.faculties,
			R.drawable.feedback,
			R.drawable.fulsite,
			R.drawable.gallery,
			R.drawable.info,
			R.drawable.lib,
			R.drawable.login,
			R.drawable.loginig,
			R.drawable.news,
			R.drawable.settings,
			R.drawable.placement
			};
	
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stores);
		
		
		try {
			String UserArray = new RequestTask().execute("http://192.168.1.103/bulkoffers/index.php?r=site/getCompany").get();
			JSONArray json_Array=new JSONArray(UserArray);
			int length = json_Array.length();
			Companynames = new String[length];
			Offers = new String[length];
			Ids = new int[length];
			for(int i=0;i<length;i++){
			      JSONObject json_data = json_Array.getJSONObject(i);
			      String companyNames =  (String)json_data.getString("name").toString();
			      String offerDescription =  (String)json_data.getString("offertext").toString();
			      int currentId = Integer.parseInt(json_data.getString("id"));
			      Companynames[i] = companyNames;
			      Offers[i] = offerDescription;
			      Ids[i] = currentId; 
			      }
			 Log.i("Data In array", json_Array.toString());
			 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		list = (ListView)findViewById(R.id.listView1);
		
		//Making the connection of listView and single_Row Layout with help of adapter
		
		MyAdapter adapter= new MyAdapter(this,Companynames,Offers,Images);
		//Setting the adapter
		
		list.setAdapter(adapter);
		list.setOnItemClickListener(onItemClick);	
		
	}
	
	//handling Item Click 	
	private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Intent i = new Intent("android.intent.action.offers");
			i.putExtra("position", Integer.toString(position));
			i.putExtra("name", Companynames[position]);
			i.putExtra("id", Integer.toString(Ids[position]));
			startActivity(i);
		}
	};
	class RequestTask extends AsyncTask<String, String, String>{

	    @Override
	    protected String doInBackground(String... uri) {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        try {
	            response = httpclient.execute(new HttpGet(uri[0]));
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                responseString = out.toString();
	                out.close();
	            } else{
	                //Closes the connection.
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (ClientProtocolException e) {
	            //TODO Handle problems..
	        } catch (IOException e) {
	            //TODO Handle problems..
	        }
	        return responseString;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        //Do anything with response..
	    }
	}
	
}						//ARRAY ADAPTER CREATION 

						// Creating an adapter to help adapting listview to the single_row layout
				
class MyAdapter extends ArrayAdapter<String>
{
	int[] images;
	Context context;
	String[] TitleArray;
	String[] DescArray;
	//MyAdapter is a constructor
	
	MyAdapter(Context c,String[] LargeText,String[] Descriptions, int[] Icons)
	{
		super(c,R.layout.single_row,R.id.textView1,LargeText);
		this.context=c;
		this.TitleArray=LargeText;
		this.DescArray=Descriptions;
		this.images=Icons;
	}
					//Converting XML single_row layout to java object so as to place data in required areas
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//We need layoutInflater to bind the xml layout with java but this requires a CONTEXT which we don't have
							//so we created global Context context
		
		
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	
				//Converted java object is returned as a View(whole layout(relative here)) so we get it as 
	
	View row=inflater.inflate(R.layout.single_row, parent,false);
	
	ImageView pic= (ImageView)row.findViewById(R.id.imageView1);
	TextView title=(TextView)row.findViewById(R.id.textView1);
	TextView description=(TextView)row.findViewById(R.id.textView2);
	
	pic.setImageResource(images[position]);
	title.setText(TitleArray[position]);
	description.setText(DescArray[position]);
	return row;
	}
}