package com.cynthia.examenandroid.app.menu.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cynthia.examenandroid.app.menu.MenuPresenterContract;
import com.cynthia.examenandroid.app.menu.MenuViewContract;

import org.json.JSONObject;


public class MenuPresenter implements MenuPresenterContract {

public  static String URL="https://dl.dropboxusercontent.com/s/5u21281sca8gj94/getFile.json?dl=0";


    private MenuViewContract view;
    private Context context;
    private RequestQueue requestQueue;

    public MenuPresenter(MenuViewContract view, Context context) {
        this.view = view;
        this.context = context;

    }

    @Override
    public void doFile() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://dl.dropboxusercontent.com/s/5u21281sca8gj94/getFile.json?dl=0";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       // textView.setText("Response is: "+ response.substring(0,500));
                        Log.i("Response",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  textView.setText("That didn't work!");
                Log.i("Error",error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
