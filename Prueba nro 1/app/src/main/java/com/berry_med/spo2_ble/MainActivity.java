package com.berry_med.spo2_ble;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.berry_med.spo2_ble.ble.BleController;
import com.berry_med.spo2_ble.data.Const;
import com.berry_med.spo2_ble.data.DataParser;
import com.berry_med.spo2_ble.dialog.DeviceListAdapter;
import com.berry_med.spo2_ble.dialog.SearchDevicesDialog;
import com.berry_med.spo2_ble.views.WaveformView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BleController.StateListener {

    EditText CmpSerial, CmpOxigeno, CmpPulso, CmpFecha;


    private final static String TAG = MainActivity.class.getSimpleName();

    private static final int PERMISSION_REQUEST_CODE_LOCATION = 100;

    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvParams)
    TextView tvResult;
    @BindView(R.id.wfvPleth)
    WaveformView wfvPleth;
    @BindView(R.id.etNewBtName)
    EditText etNewBtName;
    @BindView(R.id.llChangeName)
    LinearLayout llChangeName;

    private DataParser mDataParser;
    private BleController mBleControl;

    private SearchDevicesDialog mSearchDialog;
    private DeviceListAdapter   mBtDevicesAdapter;
    private ArrayList<BluetoothDevice> mBtDevices = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dividida);

        Button BtnEnviar = findViewById(R.id.btnEnviar);
        CmpSerial = findViewById(R.id.campoSerial);
        CmpOxigeno = findViewById(R.id.campoOxigeno);
        CmpPulso = findViewById(R.id.campoPulso);
        CmpFecha = findViewById(R.id.campoFecha);

        BtnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });


        ButterKnife.bind(this);

        mDataParser = new DataParser(new DataParser.onPackageReceivedListener() {
            @Override
            public void onOxiParamsChanged(final DataParser.OxiParams params) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText("SpO2: "+ params.getSpo2() + "   Pulse Rate:"+params.getPulseRate());
                    }
                });
            }
            @Override
            public void onPlethWaveReceived(final int amp) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wfvPleth.addAmp(amp);
                    }
                });
            }
        });
        mDataParser.start();

        mBleControl = BleController.getDefaultBleController(this);
        mBleControl.enableBtAdapter();
        mBleControl.bindService(this);

        mBtDevicesAdapter = new DeviceListAdapter(this,mBtDevices);
        mSearchDialog = new SearchDevicesDialog(this,mBtDevicesAdapter) {
            @Override
            public void onSearchButtonClicked() {
                mBtDevices.clear();
                mBtDevicesAdapter.notifyDataSetChanged();
                mBleControl.scanLeDevice(true);
            }

            @Override
            public void onClickDeviceItem(int pos) {
                BluetoothDevice device = mBtDevices.get(pos);
                tvStatus.setText("Name:"+device.getName()+"     "+"Mac:"+device.getAddress());
                mBleControl.connect(device);
                dismiss();
            }
        };
        checkLocationPermission();
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
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            String url = "https://mimundoclasico.000webhostapp.com/con/envioDatos.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    limpiar();
                    Toast.makeText(MainActivity.this, "Se ha añadido el registro con exito", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "No se pudo añadir el registro" +error.toString(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onResume() {
        super.onResume();
        mBleControl.registerBtReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBleControl.unregisterBtReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataParser.stop();
        mBleControl.unbindService(this);
        System.exit(0);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                if(!mBleControl.isConnected()){
                    mBleControl.scanLeDevice(true);
                    mSearchDialog.show();
                    mBtDevices.clear();
                    mBtDevicesAdapter.notifyDataSetChanged();
                }
                else {
                    mBleControl.disconnect();
                }
                break;
            case R.id.tvGetSource:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(Const.GITHUB_SITE)));
                break;
            case R.id.btnChangeName:
                mBleControl.changeBtName(etNewBtName.getText().toString());
                break;
        }
    }



    @Override
    public void onFoundDevice(final BluetoothDevice device) {
        if((!mBtDevices.contains(device))  &&  (device.getName() != null)){
            mBtDevices.add(device);
            mBtDevicesAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onConnected() {
        btnSearch.setText("Disconnect");
        Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(MainActivity.this, "Disconnected",Toast.LENGTH_SHORT).show();
        btnSearch.setText("Search");
        llChangeName.setVisibility(View.GONE);
    }

    @Override
    public void onReceiveData(byte[] dat) {
        mDataParser.add(dat);
    }

    @Override
    public void onServicesDiscovered() {
        llChangeName.setVisibility(mBleControl.isChangeNameAvailable() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onScanStop() {
        mSearchDialog.stopSearch();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkLocationPermission(){
        if((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE_LOCATION);
            btnSearch.setEnabled(false);
        }
        else{
            btnSearch.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGrantedLocation = true;
        if(requestCode == PERMISSION_REQUEST_CODE_LOCATION){
            for(int i : grantResults){
                if(i != PackageManager.PERMISSION_GRANTED){
                    isGrantedLocation = false;
                }
            }
        }
        if(!isGrantedLocation){
            tvStatus.setText("ERR: No location permissions.");
            Toast.makeText(this,"Permission Error !!!",Toast.LENGTH_SHORT).show();
            btnSearch.setEnabled(false);
        }
        else{
            btnSearch.setEnabled(true);
        }
    }
}
