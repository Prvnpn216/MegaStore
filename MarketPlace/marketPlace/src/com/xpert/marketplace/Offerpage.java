package com.xpert.marketplace;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Offerpage extends Activity {

	
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
	
	String O_T,O_N;
	
	
	ImageView iv;
	TextView Company, offerName, offerText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offerpage);
		Company = (TextView) findViewById(R.id.textView2);
		offerName = (TextView) findViewById(R.id.textView3);
		offerText = (TextView) findViewById(R.id.textView1);
		iv =(ImageView) findViewById(R.id.imageView1);
		String position = getIntent().getStringExtra("position");
		String name = getIntent().getStringExtra("name");
		String id = getIntent().getStringExtra("id");
		iv.setImageResource(Images[Integer.parseInt(position)]);
		
		try {
			String OfferDetails = new RequestText().execute("http://192.168.1.103/bulkoffers/index.php?r=site/getOffers&dumb="+id).get();
			 JSONObject jsonObj = new JSONObject(OfferDetails);
			
			
			O_T = new String();
			O_N = new String(); 
			
				
			      O_N =  (String)jsonObj.getString("NAME").toString();
			      O_T =  (String)jsonObj.getString("OFFER_TEXT").toString();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		Company.setText(name);
		offerName.setText(O_N);
		offerText.setText(O_T);
	}
	
	class RequestText extends AsyncTask<String, String, String>{

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
		       
		    }
		}
}
