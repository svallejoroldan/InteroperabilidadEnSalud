package com.example.bluetooth3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendInfo extends Activity {



        EditText CmpSerial, CmpOxigeno, CmpPulso, CmpFecha;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.send_info);


            Button BtnEnviar = findViewById(R.id.BtnEnviar);
            CmpSerial = findViewById(R.id.CampoSerial);
            CmpOxigeno = findViewById(R.id.CampoOxigeno);
            CmpPulso = findViewById(R.id.CampoPulso);
            CmpFecha = findViewById(R.id.CampoFecha);

            BtnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cargarWebService();
                }
            });

        }

        private void cargarWebService() {
            String serial = CmpSerial.getText().toString().trim();
            String oxigeno = CmpOxigeno.getText().toString().trim();
            String pulso = CmpPulso.getText().toString().trim();
            String fecha = CmpFecha.getText().toString().trim();

            if(serial.isEmpty()){
                CmpSerial.setError("Complete los campos");
            }else if(oxigeno.isEmpty()){
                CmpOxigeno.setError("Complete los campos");
            }else if(pulso.isEmpty()){
                CmpPulso.setError("Complete los campos");
            }else if(fecha.isEmpty()){
                CmpFecha.setError("Complete los campos");
            }else{
                RequestQueue requestQueue = Volley.newRequestQueue(SendInfo.this);
                String url = "https://mimundoclasico.000webhostapp.com/con/envioDatos.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        limpiar();
                        Toast.makeText(SendInfo.this, "Se ha añadido el registro con exito", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SendInfo.this, "No se pudo añadir el registro" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String, String> parametros = new HashMap<String, String>();
                        parametros.put("serial",serial);
                        parametros.put("pulso",pulso);
                        parametros.put("oxigeno",oxigeno);
                        parametros.put("fecha",fecha);

                        return parametros;
                    }
                };
                requestQueue.add(request);
            }
        }

        private void limpiar() {
            CmpSerial.setText("");
            CmpOxigeno.setText("");
            CmpPulso.setText("");
            CmpFecha.setText("");
        }
    }




