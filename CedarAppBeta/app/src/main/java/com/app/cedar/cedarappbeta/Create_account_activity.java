package com.app.cedar.cedarappbeta;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Create_account_activity extends Activity {

    int current_step;
    ParseDBCommunicator parseDBCommunicator;
    FragmentManager fm;
    String hu_id;
    TextView first_title, first_sub, second_title, second_sub;
    EditText su_idbox, f_namebox, gpa_box, password_box, confirm_pass_box;
    List<ParseUser> userholder;
    Boolean firstchar;
    LinearLayout firststepholder, secondstepholder, thirdstepholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_activity);

        this.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffa417")));
        current_step = 1;

        final ImageView background = (ImageView) findViewById(R.id.background);
        Picasso.with(this).load(R.drawable.images).centerCrop().fit().into(background);

        parseDBCommunicator = new ParseDBCommunicator(this);
        first_title = (TextView) findViewById(R.id.first_title);
        first_sub = (TextView) findViewById(R.id.first_sub_text);
        second_title = (TextView) findViewById(R.id.second_title);
        second_sub = (TextView) findViewById(R.id.second_sub_text);

        su_idbox = (EditText) findViewById(R.id.sign_up_id_box);
        f_namebox = (EditText) findViewById(R.id.f_name_verify_box);
        gpa_box = (EditText) findViewById(R.id.verify_gpa_box);
        password_box = (EditText) findViewById(R.id.password_box);
        confirm_pass_box = (EditText) findViewById(R.id.confirm_pass_box);

        thirdstepholder = (LinearLayout) findViewById(R.id.step_three_holder);
        secondstepholder = (LinearLayout) findViewById(R.id.step_two_holder);
        firststepholder = (LinearLayout) findViewById(R.id.step_one_holder);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            switch (current_step) {
                case 1:
                    hu_id = su_idbox.getText().toString();
                     userholder = parseDBCommunicator.getUser(hu_id, new ParseDBCommunicator.connectionFailedListener() {
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

                    if (userholder.size() == 0) {
                        Toast.makeText(this, "Student id doesn't exist :(", Toast.LENGTH_LONG).show();
                        su_idbox.setHint("Please re-enter id...");
                        su_idbox.setText("");


                    } else {

                        current_step = 2;

                        ObjectAnimator transAnimation = ObjectAnimator.ofFloat(firststepholder, "translationY", firststepholder.getTranslationY(), firststepholder.getTranslationY() - 550);
                        ObjectAnimator transAnimation2 = ObjectAnimator.ofFloat(secondstepholder, "translationY", secondstepholder.getTranslationY(),secondstepholder.getTranslationY() - 650);
                        //ObjectAnimator transAnimation5 = ObjectAnimator.ofFloat(thirdstepholder, "translationY", thirdstepholder.getTranslationY(), thirdstepholder.getTranslationY() - 200);
                        //ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(firststepholder, View.ALPHA, 1, 0);
                        ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(secondstepholder, View.ALPHA, 0, 1);
                        List<ObjectAnimator> objectAnimators = new ArrayList<ObjectAnimator>();
                        objectAnimators.add(transAnimation);
                        objectAnimators.add(transAnimation2);
                        //objectAnimators.add(alphaAnimation);
                        //objectAnimators.add(alphaAnimation2);
                        ObjectAnimator[] arrayObjectAnimators = objectAnimators.toArray(new ObjectAnimator[objectAnimators.size()]);
                        AnimatorSet animSet = new AnimatorSet();
                        animSet.playTogether(arrayObjectAnimators);
                        animSet.setDuration(300);//1sec
                        animSet.setInterpolator(new AccelerateInterpolator());
                        animSet.start();
                        secondstepholder.setVisibility(View.VISIBLE);
                        firststepholder.setVisibility(View.GONE);
                    }

                    break;
                case 2:
                    if((f_namebox.getText().toString().equals(userholder.get(0).getString("first_name"))) && (Float.parseFloat(gpa_box.getText().toString()) == userholder.get(0).getNumber("gpa").floatValue()))
                    {
                        current_step = 3;
                        //ObjectAnimator transAnimation = ObjectAnimator.ofFloat(firststepholder, "translationY", firststepholder.getTranslationY(), firststepholder.getTranslationY() - 100);
                        ObjectAnimator transAnimation2 = ObjectAnimator.ofFloat(secondstepholder, "translationY", secondstepholder.getTranslationY(),secondstepholder.getTranslationY() - 550);
                        ObjectAnimator transAnimation5 = ObjectAnimator.ofFloat(thirdstepholder, "translationY", thirdstepholder.getTranslationY(), thirdstepholder.getTranslationY() - 750);

                        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(secondstepholder, View.ALPHA, 1, 0);
                        ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(thirdstepholder, View.ALPHA, 0, 1);
                        List<ObjectAnimator> objectAnimators = new ArrayList<ObjectAnimator>();
                        //objectAnimators.add(transAnimation);
                        objectAnimators.add(transAnimation2);
                        objectAnimators.add(alphaAnimation);
                        objectAnimators.add(alphaAnimation2);
                        objectAnimators.add(transAnimation5);

                        ObjectAnimator[] arrayObjectAnimators = objectAnimators.toArray(new ObjectAnimator[objectAnimators.size()]);
                        AnimatorSet animSet = new AnimatorSet();
                        animSet.playTogether(arrayObjectAnimators);
                        animSet.setDuration(300);//1sec
                        animSet.setInterpolator(new AccelerateInterpolator());

                        animSet.start();

                        thirdstepholder.setVisibility(View.VISIBLE);
                        secondstepholder.setVisibility(View.GONE);


                    }
                    else
                    {
                        Toast.makeText(this, "We have different information in our system, try again :(", Toast.LENGTH_LONG).show();
                        f_namebox.setText("");
                        gpa_box.setText("");
                    }
                 break;
                case 3:

                    if(password_box.getText().toString().equals(confirm_pass_box.getText().toString()))
                    {
                        final String password = password_box.getText().toString();
                        parseDBCommunicator.UpdatePassword(userholder.get(0).getString("username"),password, new ParseDBCommunicator.connectionFailedListener() {
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

                        Toast.makeText(this, "Account created!", Toast.LENGTH_LONG).show();

                        current_step ++;

                        if(ParseUser.getCurrentUser() != null){
                            Intent intent = new Intent(getApplicationContext(), Home_activity.class);
                            intent.putExtra("name", (userholder.get(0).getString("first_name")));
                            startActivity(intent);
                        } else{
                            onNavigateUpFromChild(this);
                            Toast.makeText(getApplicationContext(), "LogIn failed!", Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Passwords dont match!", Toast.LENGTH_LONG).show();
                        confirm_pass_box.setText("");
                    }
                    break;


            }

            return true;
        }


        if(id == android.R.id.home) {
           onNavigateUpFromChild(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        onNavigateUpFromChild(this);
//        super.onBackPressed();
    }
}
