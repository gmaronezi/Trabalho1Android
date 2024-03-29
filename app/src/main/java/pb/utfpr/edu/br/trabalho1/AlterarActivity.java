package pb.utfpr.edu.br.trabalho1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;
import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class AlterarActivity extends AppCompatActivity implements LocationListener {

    private EditText etTituloAlterar;
    private EditText etDescricaoAlterar;
    private TextView tvEnderecoAlterar;
    private TextView tvLatitudeAlterar;
    private TextView tvLongitudeAlterar;
    private Double latitude = 0.00;
    private Double longitude = 0.00;

    private DatabaseHandler dao;

    private PontosTuristicos pontoT;
    Long id_ponto;

    private ImageView ivFoto;

    private Uri uri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        etTituloAlterar = findViewById(R.id.etTituloAlterar);
        etDescricaoAlterar = findViewById(R.id.etDescricaoAlterar);
        tvEnderecoAlterar = findViewById(R.id.tvEnderecoAlterar);
        tvLatitudeAlterar = findViewById(R.id.tvLatitudeAlterar);
        tvLongitudeAlterar = findViewById(R.id.tvLongitudeAlterar);

        Intent i = getIntent();

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

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, (LocationListener) this);

        ActivityCompat.requestPermissions( this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1 );

        dao = new DatabaseHandler( this );


        if(i != null){
            Bundle params = i.getExtras();
            if(params != null){
                id_ponto = params.getLong("id");
                dao.pesquisar(String.valueOf(id_ponto));
            }
        }

        ivFoto = findViewById(R.id.ivFotoAlterar);

        setaDadosTela();
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

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

    public void btAlterarOnclick(View view) {
        //PontosTuristicos pt = new PontosTuristicos();
        if(tvLatitudeAlterar.getText().toString().equals("0") || tvLongitudeAlterar.getText().toString().equals('0')){
            Toast.makeText( this, "Latitude ou Longitude inválida", Toast.LENGTH_LONG ).show();
        }else{
            pontoT = dao.pesquisar(String.valueOf(id_ponto));
            try {
                pontoT.setTitulo(etTituloAlterar.getText().toString());
                pontoT.setDescricao(etDescricaoAlterar.getText().toString());
                pontoT.setEndereco(tvEnderecoAlterar.getText().toString());
                pontoT.setLatitude(Double.parseDouble(tvLatitudeAlterar.getText().toString()));
                pontoT.setLongitude(Double.parseDouble(tvLongitudeAlterar.getText().toString()));

                if(bitmap != null){
                    String base64String = convertToBase64(bitmap);
                    pontoT.setImagem(base64String);
                }

                if(pontoT.getTitulo().isEmpty()){
                    Toast.makeText( this, "O Título é obrigatório.", Toast.LENGTH_LONG ).show();
                }else if(pontoT.getEndereco().isEmpty()){
                    Toast.makeText( this, "O Endereço é obrigatório.\n Utilize o botão Pegar Localização para obter seu endereço.", Toast.LENGTH_LONG ).show();
                }
                else if(pontoT.getImagem().isEmpty()){
                    Toast.makeText( this, "Ter uma imagem é obrigatório.", Toast.LENGTH_LONG ).show();
                }else{
                    dao.alterar( pontoT );
                    Toast.makeText( this, "Registro inserido com sucesso!!!", Toast.LENGTH_LONG ).show();
                    this.finish();
                }

            } catch(Exception ex){
                Toast.makeText( this, "Erro ao alterar", Toast.LENGTH_LONG ).show();
            }
        }



    }

    public void btPegarLocalizacaoAlterarOnClick(View view) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            tvLatitudeAlterar.setText( String.valueOf( latitude ) );
            tvLongitudeAlterar.setText( String.valueOf( longitude ));
            tvEnderecoAlterar.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setaDadosTela(){

       pontoT = dao.pesquisar(String.valueOf(id_ponto));

        etTituloAlterar.setText(pontoT.getTitulo());
        etDescricaoAlterar.setText(pontoT.getDescricao());
        tvEnderecoAlterar.setText(pontoT.getEndereco());
        tvLatitudeAlterar.setText(pontoT.getLatitude().toString());
        tvLongitudeAlterar.setText(pontoT.getLongitude().toString());

        if(pontoT.getImagem() != null){
            bitmap = convertToBitmap(pontoT.getImagem());
            ivFoto.setImageBitmap(bitmap);
        }

    }

    public void btTirarFotoOnClick(View view) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                ivFoto.setImageBitmap(bitmap);
            }
        }
    }

    public static Bitmap convertToBitmap(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convertToBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
