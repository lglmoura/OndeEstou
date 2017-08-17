package pooa20171.iff.br.ondeestou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] activities = {"UltimaLocalizacaoActivity",
            "LocalizacaoAtualizada",
            "GeoEndereco"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);

        ListView listView = (ListView) findViewById(R.id.listaActivity);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        try {

            Class obj = Class.forName ("pooa20171.iff.br.exemplolocalizacao.activity."+activities[position]);


            intent = new Intent(this, obj);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        startActivity(intent);

    }
}
