package las.thiagoadelino.com.las;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;

import java.util.ArrayList;
import java.util.List;

public class ListPlantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plants);

        DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
        Database database = null;


        try {
            System.out.println("creating database");
            database = new Database("myDB", config);


            System.out.println("database created");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.listviewplants);

        Query q = QueryBuilder.select(SelectResult.property("id"),SelectResult.property("commonName"),SelectResult.property("scientificName")).from(DataSource.database(database));
        final ArrayList<String> result = new ArrayList<String>();

        try {
            ResultSet res = q.execute();

            for(Result r : res){
                String scientificName = r.getString("commonName");
                System.out.println(scientificName);
                result.add((scientificName==null)?"":scientificName);
            }

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, result);

        listView.setAdapter(adapter);
    }
}
