package com.example.hada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText CampoSerial, CampoPulso, CampoOxigeno, CampoFecha;

    RequestQueue request;
    TextView CmpSerial, CmpOxigeno, CmpPulso, CmpFecha;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button BtnEnviar = findViewById(R.id.BtnEnviar);
        CmpSerial = findViewById(R.id.CampoSerial);
        CmpOxigeno = findViewById(R.id.CampoOxigeno);
        CmpPulso = findViewById(R.id.CampoPulso);
        CmpFecha = findViewById(R.id.CampoFecha);
        request = Volley.newRequestQueue(getBaseContext());

        BtnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });


    }

    private void cargarWebService() {
        String url = "http://192.168.1.6/conexi%C3%B3n/envioDatos.php?serial="+CmpSerial.getText().toString()+"&pulso="+CmpPulso.getText().toString()+"&oxigeno="+CmpOxigeno.getText().toString()+"&fecha="+CmpFecha.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(), "No se pudo añadir el registro" +error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getBaseContext(), "Se ha añadido el registro con exito", Toast.LENGTH_SHORT).show();
        CmpSerial.setText("");
        CmpOxigeno.setText("");
        CmpPulso.setText("");
        CmpFecha.setText("");
    }
}