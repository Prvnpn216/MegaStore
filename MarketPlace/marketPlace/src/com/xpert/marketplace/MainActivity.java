package com.xpert.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.gotom);
        btn.setOnClickListener(new HandlerClass());
    }
    
private class HandlerClass implements OnClickListener{

    	@Override
    	public void onClick(View v) {
    		Intent i = new Intent("android.intent.action.stores");
			startActivity(i);
    	}
    	
    	
    	
    }

    
}

