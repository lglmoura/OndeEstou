package pooa20171.iff.br.ondeestou.activity;

import android.Manifest;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import pooa20171.iff.br.ondeestou.R;

public class LocalizaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };


    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localiza);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        clMostra = (ConstraintLayout) findViewById(R.id.clMostra);


        tvCidade = (TextView) findViewById(R.id.tvCidade);
        tvEstado = (TextView) findViewById(R.id.tvEstado);
        tvPais = (TextView) findViewById(R.id.tvPais);
        tvRua = (TextView) findViewById(R.id.tvRua);
        tvCompleto = (TextView) findViewById(R.id.lbEndereco);

        clMostra.setVisibility(View.INVISIBLE);






    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
