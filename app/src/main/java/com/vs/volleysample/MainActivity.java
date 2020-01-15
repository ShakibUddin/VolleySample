package com.vs.volleysample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView stuname;
    private TextView stuid;
    private Button fetch;
    private String name="";
    private String id="";
    private String POST_URL = "https://192.168.88.208/Student/post.php";
    private Integer i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stuname = (EditText) findViewById(R.id.nameinputid);
        stuid = (EditText) findViewById(R.id.idinputid);
        fetch = (Button) findViewById(R.id.fetchbutton);


        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = stuname.getText().toString().trim();
                id = stuid.getText().toString().trim();
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                //startActivity(browserIntent);
                uploadData();
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("id", id);
                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }
}
