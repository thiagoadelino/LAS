package las.thiagoadelino.com.las;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Expression;
import com.couchbase.lite.Function;
import com.couchbase.lite.Meta;
import com.couchbase.lite.Ordering;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;
import java.util.List;

import las.thiagoadelino.com.domain.Plant;

public class ListPlantsActivity extends AppCompatActivity {

    final ArrayList<Plant> result = new ArrayList<Plant>();

    PlantMainListAdapter adapter = null;

    AlertDialog alertDialog = null;

    Database database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plants);

        DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());

        try {
            System.out.println("creating database");
            database = new Database("myDB", config);


            System.out.println("database created");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.listviewplants);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    result.clear();
                    loadPlantData(database,s.toString());
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadPlantData(database,null);

        adapter = new PlantMainListAdapter(this,
                android.R.layout.simple_list_item_2, result);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Plant plant = adapter.getItem(position);
                Dialog dialog = new Dialog(ListPlantsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.detail_plant_dialog);


                TextView nomeCientifico = dialog.findViewById(R.id.nome_cientifico);
                TextView nomeComum = dialog.findViewById(R.id.nome_comum);
                TextView nomeIngles = dialog.findViewById(R.id.nome_ingles);
                TextView classe = dialog.findViewById(R.id.classe);

                nomeCientifico.setText(plant.getScientificName());
                nomeComum.setText(plant.getCommonName());
                nomeIngles.setText(plant.getEnglishName());
                classe.setText(plant.getClasse());
                dialog.show();
            }
        });
    }

    private void loadPlantData(Database database, String queryText) {
        Query q = null;
        if(queryText==null||queryText.isEmpty())
            q = QueryBuilder.select(SelectResult.property("id"),SelectResult.property("commonName"),SelectResult.property("scientificName"),SelectResult.property("englishName"),SelectResult.property("classe")).from(DataSource.database(database)).orderBy(Ordering.expression(Expression.property("scientificName")));
        else
            q = QueryBuilder.select(SelectResult.property("id"),SelectResult.property("commonName"),SelectResult.property("scientificName"),SelectResult.property("englishName"),SelectResult.property("classe"))
                            .from(DataSource.database(database))
                            .where(Function.lower(Expression.property("scientificName")).like(Expression.string("%"+queryText.toLowerCase()+"%"))).orderBy(Ordering.expression(Expression.property("scientificName")));

        try {
            ResultSet res = q.execute();

            for(Result r : res){

                Plant plant = new Plant();
                String id = r.getString("id");
                String scientificName = r.getString("scientificName");
                String commonName = r.getString("commonName");
                String englishName = r.getString("englishName");
                String classe = r.getString("classe");

                plant.setId(id);
                plant.setScientificName((scientificName==null||scientificName.isEmpty())?"-":scientificName);
                plant.setCommonName((commonName==null ||commonName.isEmpty())?"-":commonName);
                plant.setEnglishName((englishName==null||englishName.isEmpty())?"-":englishName);
                plant.setClasse((classe == null||classe.isEmpty())?"-":classe);

                result.add(plant);
            }

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
