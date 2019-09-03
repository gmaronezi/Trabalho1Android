package pb.utfpr.edu.br.trabalho1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;
import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class CadastroActivity extends AppCompatActivity implements LocationListener {

    private EditText etTitulo;
    private EditText etDescricao;
    private TextView tvEndereco;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private Double latitude = 0.00;
    private Double longitude = 0.00;

    private DatabaseHandler dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_activity);

        etTitulo = findViewById(R.id.etTitulo);
        etDescricao = findViewById(R.id.etDescricao);
        tvEndereco = findViewById(R.id.tvEndereco);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //Verificar se o GPS está habilitado
        if ( ! lm.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) ) {

            AlertDialog.Builder alerta = new AlertDialog.Builder( this );
            alerta.setTitle( "Atenção" );
            alerta.setMessage( "GPS não habilitado. Deseja Habilitar??" );
            alerta.setCancelable( false );
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                    startActivity( i );
                }
            } );
            alerta.setNegativeButton( "Cancelar", null );
            alerta.show();

        }

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);

        ActivityCompat.requestPermissions( this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1 );

        dao = new DatabaseHandler( this );

    }

    public void btIncluirOnClick(View view) {
        PontosTuristicos pt = new PontosTuristicos();
        pt.setTitulo( etTitulo.getText().toString() );
        pt.setDescricao( etDescricao.getText().toString() );
        pt.setLatitude(Double.parseDouble(tvLatitude.getText().toString()));
        pt.setLongitude(Double.parseDouble(tvLongitude.getText().toString()));
        dao.incluir( pt );

        Toast.makeText( this, "Registro inserido com sucesso!!!", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onLocationChanged(Location location) {

         latitude = location.getLatitude();
         longitude = location.getLongitude();

        tvLatitude.setText( String.valueOf( latitude ) );
        tvLongitude.setText( String.valueOf( longitude ) );

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void btPegarLocalizacaoOnClick(View view) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {


            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            tvLatitude.setText( String.valueOf( latitude ) );
            tvLongitude.setText( String.valueOf( longitude ));
            tvEndereco.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
