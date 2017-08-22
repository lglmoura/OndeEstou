package pooa20171.iff.br.ondeestou.activity;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

import pooa20171.iff.br.ondeestou.R;
import pooa20171.iff.br.ondeestou.util.PermissionUtils;

public class LocalizaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };


    private EditText etLatitude;
    private EditText etLongitude;
    private EditText etNomeRua;
    private Button btBuscar;
    private ConstraintLayout clMostra;

    private TextView tvRua;
    private TextView tvCidade;
    private TextView tvEstado;
    private TextView tvPais;
    private TextView tvCompleto;


    private Address endereco;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localiza);

        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        clMostra = (ConstraintLayout) findViewById(R.id.clMostra);
        btBuscar = (Button) findViewById(R.id.btBusca);

        tvCidade = (TextView) findViewById(R.id.tvCidade);
        tvEstado = (TextView) findViewById(R.id.tvEstado);
        tvPais = (TextView) findViewById(R.id.tvPais);
        tvRua = (TextView) findViewById(R.id.tvRua);
        tvCompleto = (TextView) findViewById(R.id.lbEndereco);

        clMostra.setVisibility(View.INVISIBLE);

        btBuscar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buscar();
            }
        });
        callConnection();
        PermissionUtils.validate(this, permissoes);


        googleApiClient.connect();
    }

    private synchronized void callConnection() {
        Log.i("LOG", "callConnection()");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

    }

    private void buscar(){

        if((etLatitude.getText() == null) &&
                (etLongitude.getText() == null)&&
                (etNomeRua.getText() == null)){
            Toast.makeText(this,"OS campos deve ser preenchidos",Toast.LENGTH_LONG);

        }
        Log.i("LOG", "Busca");

        Double latPoint = Double.parseDouble(etLatitude.getText().toString());
        Double lngPoint = Double.parseDouble(etLongitude.getText().toString());

        String resultAddress = "";

        clMostra.setVisibility(View.VISIBLE);

        try {
            endereco = getEndereco(latPoint,lngPoint);
            Log.i("LOG","Atualizar "+endereco.getThoroughfare());

            for(int i = 0, tam = endereco.getMaxAddressLineIndex(); i < tam; i++){
                resultAddress += endereco.getAddressLine(i);
                resultAddress += i < tam - 1 ? ", " : "";
                Log.i("LOG","Result "+resultAddress);
            }
            tvRua.setText("Rua: "+endereco.getThoroughfare());

            tvCidade.setText("Cidade: "+endereco.getLocality());
            tvEstado.setText("Estado: "+endereco.getAdminArea());
            tvPais.setText("Pais:"+endereco.getCountryName());
            tvCompleto.setText("Completo: "+resultAddress);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Address getEndereco(double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        Address endereco= null;
        List<Address> enderecos;
        geocoder = new Geocoder(getApplicationContext());
        enderecos = geocoder.getFromLocation(latitude,longitude,1);
        if(enderecos.size()> 0)
            Log.i("LOG","Endereços ---> "+String.valueOf(enderecos.size()));
            endereco = enderecos.get(0);
        return endereco;
    }



    public void onResume() {
        super.onResume();

        if (googleApiClient != null && googleApiClient.isConnected())
            startLocationUpdate();

    }


    @Override
    public void onPause() {
        super.onPause();

        if (googleApiClient != null) {
            stopLocationUpdate();
        }
    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void startLocationUpdate(){
        initLocationRequest();

        //LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }


    private void stopLocationUpdate(){
        //LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override

    public void onConnected(@Nullable Bundle bundle) {
        Log.i("LOG", "UpdateLocationActivity.onConnected(" + bundle + ")");

        Location l = LocationServices
                .FusedLocationApi
                .getLastLocation(googleApiClient); // PARA JÁ TER UMA COORDENADA PARA O UPDATE FEATURE UTILIZAR

        startLocationUpdate();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "UpdateLocationActivity.onConnectionSuspended(" + i + ")");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("LOG", "UpdateLocationActivity.onConnectionFailed(" + connectionResult + ")");

    }



}
