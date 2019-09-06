package pb.utfpr.edu.br.trabalho1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;
import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PontosTuristicos pontoT;

    private DatabaseHandler dao;
    Long id_ponto;

    public MapsActivity(GoogleMap mMap, PontosTuristicos pontoT) {
        this.mMap = mMap;
        this.pontoT = pontoT;
    }

    public MapsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dao = new DatabaseHandler( this );

        Intent i = getIntent();
        if(i != null){
            Bundle params = i.getExtras();
            if(params != null){
                id_ponto = params.getLong("id");
                dao.pesquisar(String.valueOf(id_ponto));
            }
        }

        pontoT = dao.pesquisar(String.valueOf(id_ponto));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng local = new LatLng(pontoT.getLatitude(), pontoT.getLongitude());

        mMap.addMarker(new MarkerOptions().snippet(pontoT.getEndereco()).position(local).title(pontoT.getTitulo()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local,50));
    }
}
