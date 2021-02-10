package com.cynthia.examenandroid.app.menu.view;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cynthia.examenandroid.MainActivity;
import com.cynthia.examenandroid.R;
import com.cynthia.examenandroid.app.menu.MenuPresenterContract;
import com.cynthia.examenandroid.app.menu.MenuViewContract;
import com.cynthia.examenandroid.business.models.ExError;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONException;
import org.json.JSONObject;


public class MenuFragment extends Fragment implements MenuViewContract {
    private static final short REQUEST_CODE = 6545;

    private Button myCollaborators;
    private Button addCollaborators;
    private Button exit;
    private MenuPresenterContract presenter = null;

    private FirebaseAuth mAuth;
    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_menu, container, false);
        this.myCollaborators =  view.findViewById(R.id.MyCollaborators);
        this.addCollaborators = view.findViewById(R.id.AddCollaborators);
        this.exit= view.findViewById(R.id.Exit);

        myCollaborators.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              //  Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_form1Fragment);
            }
        });

        addCollaborators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
               // Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_infoSecos1Fragment);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() != null)
                    mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

       sendResponse();





        // Inflate the layout for this fragment

        return view;
    }

    public void sendResponse() {
        RequestQueue requestQueue  = Volley.newRequestQueue(getContext());
        final String url ="https://dl.dropboxusercontent.com/s/5u21281sca8gj94/getFile.json?dl=0";

        // Request a string response from the provided URL.
        final JsonObjectRequest jsObjectRequest  = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject  response) {

                        Log.i("Response",response.toString());

                        try {
                            String var=response.getString("data");
                            Log.i("var",var);
                            try {

                                JSONObject obj = new JSONObject(var);

                               String file=obj.getString("file");

                                Log.i("file",file);
                                processZip(file);

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + var + "\"");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error",error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
          requestQueue.add(jsObjectRequest);

    }

public  void processZip(String u){

}
    private void checkSelfPermission() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } else {

            //executeDownload();

        }
    }

    @Override
    public void onSuccessFiel() {


    }

    @Override
    public void onFailedFiel(ExError error) {

    }
}
