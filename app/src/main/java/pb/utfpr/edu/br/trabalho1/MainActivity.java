package pb.utfpr.edu.br.trabalho1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btCadastrarOnClick(View view) {
        Intent i = new Intent(this, CadastroActivity.class);
        startActivity(i);
    }
}
