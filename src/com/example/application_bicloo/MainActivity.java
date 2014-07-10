package com.example.application_bicloo;

import java.util.ArrayList;

import org.apache.http.protocol.ResponseServer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ArrayList<String> list = new ArrayList<String>();
        
        // CREATION DE L'ADAPTER
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        
        // RECUPERATION DE LA LISTVIEW
        ListView listView = (ListView) findViewById(R.id.listViewArret);
        listView.setAdapter(arrayAdapter);
        
        // REQUETE
        RequestQueue queue = Volley.newRequestQueue(this);
        
        // REQUETE DE L'API AVEC LES PARAMETRES
        String url = "https://api.jcdecaux.com/vls/v1/stations?contract=nantes&apiKey=d8cb851fb2471c0c4927e55405d96ded9ab1360f";
        
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				
			// BOUCLE D'AJOUT DES INFORMATIONS
				for (int i=0; i<response.length(); i++){
					
					try {
						
						// RECUPERATION DE L'OBJET ARRET
						JSONObject arret = response.getJSONObject(i);
						
						// RECUPERATION DES ATTRIBUTS DE L'OBJET
						String bikeStation = arret.getString("name");
						int availableBikeStands = arret.getInt("available_bike_stands");
						int bikeStands  = arret.getInt("bike_stands");
						
						//AJOUT DU NOM DE L'ARRET
						arrayAdapter.add(bikeStation);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}		
				
				// RAFRAICHISSEMENT DE LA LISTVIEW
				arrayAdapter.setNotifyOnChange(true);
														}
																										}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// MESSAGE EN CAS D'ERREUR DE NON RETOUR
				Log.d("ERROR API, REQUEST BAD", error.toString());
			}
		});
        
        queue.add(jsObjRequest);
        
        
        
        
        
    }
}
