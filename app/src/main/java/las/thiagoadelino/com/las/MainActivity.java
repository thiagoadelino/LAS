package las.thiagoadelino.com.las;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.MutableDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import las.thiagoadelino.com.domain.Plant;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends AppCompatActivity {


    public void onButtonClick(View v){
        Intent intent = new Intent(getBaseContext(), ListPlantsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
        Database database = null;
        try {
            System.out.println("creating database");
            database = new Database("myDB", config);
            System.out.println("database created");
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        String json = null;
        try {
            System.out.println("retrieving json");
            InputStream is = getAssets().open("db_plants.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GsonBuilder b = new GsonBuilder();
        Gson gson = b.create();

        System.out.println(json);
        System.out.println("json retrievied");

        List<Plant> result = Arrays.asList(gson.fromJson(json, Plant[].class));
        System.out.println("saving");
        for(Plant p: result) {
            MutableDocument doc = new MutableDocument();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", p.getId());
            map.put("scientificName", p.getScientificName());
            map.put("commonName", p.getCommonName());
            map.put("englishName", p.getEnglishName());
            map.put("classe", p.getClasse());
            doc.setData(map);
            try {

                database.save(doc);

            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
        System.out.println("saved");
        //Manager manager = new Manager(new JavaContext(), Manager.DEFAULT_OPTIONS);





    }
    /*

    // (1) Get the database or create it if it doesn’t already exist.

Manager manager = new Manager(new JavaContext(),Manager.DEFAULT_OPTIONS);

Database db = manager.getDatabase("couchdB");

// (2) Create a new document (a record) in the database.

Document doc = db.createDocument();

Map properties = new HashMap();

properties.put("firstName", "OSFY");

doc.putProperties(properties);

// (3) Update a document.

doc.update(new Document.DocumentUpdater() {

@Override

public boolean update(UnsavedRevision newRevision) {

Map properties = newRevision.getUserProperties();

properties.put("firstName", "Johnny");

newRevision.setUserProperties(properties);

return true;

}

});

// (4) Delete a document.

doc.delete();





     */
}
