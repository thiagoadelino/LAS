package las.thiagoadelino.com.myapplication;

        import android.app.Activity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity implements TextWatcher{

        AutoCompleteTextView myAutoComplete;
        String item[]={
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            myAutoComplete = (AutoCompleteTextView)findViewById(R.id.myautocomplete);

            myAutoComplete.addTextChangedListener(this);
            myAutoComplete.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item));

        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }
    }