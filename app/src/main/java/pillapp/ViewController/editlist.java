package pillapp.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vyom.pillapp.R;

import java.util.List;

import pillapp.Model.DbHelper;
import pillapp.Model.Panic;

/**
 * Created by Vyom on 03-08-2016.
 */
public class editlist extends ActionBarActivity {

    private final int REQUEST_CODE=99;
    //private Intent data;
       EditText helper1,helper2,helper3,helper4,helper5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panicsms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Save Your List");
        helper1 = (EditText)findViewById(R.id.helper1);
         helper2 = (EditText)findViewById(R.id.helper2);
        helper3 = (EditText)findViewById(R.id.helper3);
        helper4 = (EditText)findViewById(R.id.helper4);
         helper5 = (EditText)findViewById(R.id.helper5);

    
        Button edit=(Button)findViewById(R.id.button);
        final DbHelper db=new DbHelper(getApplicationContext());
        List<Panic> pa=db.getPanicList();
        if(!pa.isEmpty()) {
            if(pa.get(0).getMobile1().equals("0"))
                pa.get(0).setMobile1("");
            if(pa.get(0).getMobile2().equals("0"))
                pa.get(0).setMobile2("");
            if(pa.get(0).getMobile3().equals("0"))
                pa.get(0).setMobile3("");
            if(pa.get(0).getMobile4().equals("0"))
                pa.get(0).setMobile4("");
            if(pa.get(0).getMobile5().equals("0"))
                pa.get(0).setMobile5("");

            helper1.setText(pa.get(0).getMobile1().toString());
            helper2.setText(pa.get(0).getMobile2().toString());
            helper3.setText(pa.get(0).getMobile3().toString());
            helper4.setText(pa.get(0).getMobile4().toString());
            helper5.setText(pa.get(0).getMobile5().toString());
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                                                   if (helper1.getText().toString().equals("") ) {
                                                                       Toast.makeText(getApplicationContext(), "Fill Atleast the Helper1 Contact", Toast.LENGTH_LONG).show();
                                                                   }

                                                                   else {
                                                                       /*if (helper2.getText().toString().equals("0") ) {
                                                                           helper2.setText("0");
                                                                       }
                                                                       else if (helper3.getText().toString().equals("") ) {
                                                                           helper3.setText("0");
                                                                       }
                                                                       else if (helper4.getText().toString().equals("") ) {
                                                                           helper4.setText("0");
                                                                       }
                                                                       else {// (helper5.getText().toString().equals("") ) {
                                                                           helper5.setText("0");
                                                                       }*/
                                                                       Panic pan = new Panic(helper1.getText().toString(), helper2.getText().toString(), helper3.getText().toString(), helper4.getText().toString(), helper5.getText().toString());
                                                                        db.panic_add(helper1.getText().toString(), helper2.getText().toString(), helper3.getText().toString(), helper4.getText().toString(), helper5.getText().toString());
                                                                       try {
                                                                           db.updatepanic(pan);
                                                                           Toast.makeText(getApplicationContext(), "List Updated", Toast.LENGTH_LONG).show();
                                                                           Intent intr = new Intent(getApplicationContext(), MainActivity.class);
                                                                           startActivity(intr);
                                                                       }
                                                                       catch(Exception e)
                                                                       {
                                                                           Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                                       }
                                                                       /*if (helper2.getText().toString().equals("0") ) {
                                                                           helper2.setText("");
                                                                       }
                                                                       else if (helper3.getText().toString().equals("0") ) {
                                                                           helper3.setText("");
                                                                       }
                                                                       else if (helper4.getText().toString().equals("0") ) {
                                                                           helper4.setText("");
                                                                       }
                                                                       else if (helper5.getText().toString().equals("") ) {
                                                                           helper5.setText("0");
                                                                       }*/

                                                                        //List<Panic> pa= db.getPanicList();
                                                                      // String mobile = pa.get(0).getMobile1() + ";" + pa.get(0).getMobile2() + ";" + pa.get(0).getMobile3() + ";" + pa.get(0).getMobile4() + ";" + pa.get(0).getMobile5();
                                                                      // Toast.makeText(getApplicationContext(), mobile, Toast.LENGTH_LONG).show();


                                                                                                                                          }
                //Toast.makeText(getApplicationContext(), "Fill All the Helpers Contact", Toast.LENGTH_LONG).show();


            }
        });



    }


    @Override
    /** Inflate the menu; this adds items to the action bar if it is present */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent returnHome = new Intent(getBaseContext(), MainActivity.class);
        startActivity(returnHome);
        finish();
        return super.onOptionsItemSelected(item);
    }
    /*@Override
    public void onBackPressed() {
        Intent returnHome = new Intent(getBaseContext(), MainActivity.class);
        startActivity(returnHome);
        finish();
    }*/
}
