package com.app.cedar.cedarappbeta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;


public class SignIn_activity extends Activity {

    ParseDBCommunicator parseDBCommunicator;
    String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parseDBCommunicator = new ParseDBCommunicator(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.overridePendingTransition(
                R.anim.in_from_left, R.anim.out_to_right);


        Button signin_button = (Button) findViewById(R.id.signin);
        final TextView id_box = (TextView) findViewById(R.id.id_box);
        final TextView password_box = (TextView) findViewById(R.id.password_box);
        final ImageView background = (ImageView) findViewById(R.id.background);
        final LinearLayout ca_button = (LinearLayout) findViewById(R.id.ca_holder);
        Picasso.with(this).load(R.drawable.images).centerCrop().fit().into(background);

        if(ParseUser.getCurrentUser() != null) {
            Toast.makeText(getApplicationContext(), "Welcome, " + ParseUser.getCurrentUser().getString("first_name"), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), Home_activity.class);
            intent.putExtra("name", ParseUser.getCurrentUser().getString("first_name") + " " + ParseUser.getCurrentUser().getString("last_name"));
            startActivity(intent);
        }



                ca_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Create_account_activity.class);
                        startActivity(intent);
                    }
                });


                signin_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        id = id_box.getText().toString();
                        password = password_box.getText().toString();
                        ParseUser pu = parseDBCommunicator.logIn(id, password, new ParseDBCommunicator.connectionFailedListener() {
                            @Override
                            public void onConnectionFailed() {

                            }

                            @Override
                            public void onConnectionSuccessful() {

                            }

                            @Override
                            public void onCannotConnectToParse() {

                            }
                        });

                        //THIS HAS TO RETURN ERROR CODE TO TELL WHAT HAPPEN IF LOGIN DOESNT WORK!!!!!!!!

                        if (pu == null) {
                            Toast.makeText(getApplicationContext(), "Sorry, you info is incorrect :(", Toast.LENGTH_LONG).show();
                            id_box.setText("");
                            password_box.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Welcome, " + pu.getString("first_name"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Home_activity.class);
                            intent.putExtra("name", pu.getString("first_name") + " " + pu.getString("last_name"));
                            startActivity(intent);
                        }
                    }
                });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
