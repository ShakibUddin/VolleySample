package com.vs.volleysample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView stuname;
    private TextView stuid;
    private Button post;
    private Button fetch;
    private Button fetchbyid;
    private String name="";
    private String id="";
    private String POST_URL = "http://192.168.0.102/VolleySample/mssqlPOST.php";
    private String FETCH_URL = "http://192.168.0.102/VolleySample/mssqlGET.php";
    private String FETCH_BY_ID_URL="http://192.168.0.102/VolleySample/mssqlGETbyParam.php";
    private int i=0;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stuname = (EditText) findViewById(R.id.nameinputid);
        stuid = (EditText) findViewById(R.id.idinputid);
        post = (Button) findViewById(R.id.postbutton);
        fetch = (Button) findViewById(R.id.fetchbutton);
        fetchbyid = (Button) findViewById(R.id.fetchbyidbutton);
        result = (TextView)findViewById(R.id.resultid);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = stuname.getText().toString();
                id = stuid.getText().toString();
                uploadData();
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = stuname.getText().toString();
                id = stuid.getText().toString();
                fetchData();
            }
        });
        fetchbyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = stuid.getText().toString();
                fetchDataById();
            }
        });
    }
    private void  uploadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(MainActivity.this, Response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("pass", id);
                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }
    private void  fetchData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonArray = jsonobject.getJSONArray("retrieved_data");

                    for (i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final String name = jsonObject.getString("name");
                        final String id = jsonObject.getString("pass");
                        result.append(name+", "+id+"\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Check your internet connection",Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }
    private void  uploadParameters() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_BY_ID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(MainActivity.this, Response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);

                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }
    public void fetchDataById(){
        //##### IF YOU ARE GETTING DATA USE POST INSTEAD OF GET...just SEND PARAMETERS USING OVERRIDDEN getParams()
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_BY_ID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonArray = jsonobject.getJSONArray("retrieved_data");

                    for (i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final String name = jsonObject.getString("name");
                        final String id = jsonObject.getString("pass");
                        result.append(name+", "+id+"\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pass", id);

                return params;
            }};
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,1,2));
        MySingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }
}
