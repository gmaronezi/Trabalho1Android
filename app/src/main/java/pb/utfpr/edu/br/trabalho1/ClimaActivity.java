package pb.utfpr.edu.br.trabalho1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pb.utfpr.edu.br.trabalho1.dao.DatabaseHandler;
import pb.utfpr.edu.br.trabalho1.entidade.PontosTuristicos;

public class ClimaActivity extends AppCompatActivity {

    private PontosTuristicos pontoT;

    private DatabaseHandler dao;
    Long id_ponto;

    private String URLline = "";

    private String temperatura, data, hora, descricao, turno, cidade, umidade, velocVento, horaAmanhacer, horaPorDoSol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clima);

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

        getVolley();
    }

    private void getVolley() {

        URLline = "https://api.hgbrasil.com/weather?key=a9f269a4&lat="+pontoT.getLatitude()+"log=" + pontoT.getLongitude()+"&user_ip=remote";

        Log.d("GET --> ", URLline);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("resposta ->>", ""+response);
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parseData(String response) {
        try {

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("valid_key").equals("true")) {

                JSONObject dataObject = jsonObject.getJSONObject("results");

                    temperatura = dataObject.getString("temp");
                    data = dataObject.getString("date");
                    hora = dataObject.getString("time");
                    descricao = dataObject.getString("description");
                    turno = dataObject.getString("currently");
                    cidade = dataObject.getString("city");
                    umidade = dataObject.getString("humidity");
                    velocVento = dataObject.getString("wind_speedy");
                    horaAmanhacer = dataObject.getString("sunrise");
                    horaPorDoSol = dataObject.getString("sunset");
            }

        } catch (JSONException e) {
            e.getMessage();
        }
    }
}
