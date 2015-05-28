package app.cedar.cedarconnect;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.marvinlabs.widget.slideshow.SlideShowView;
import com.marvinlabs.widget.slideshow.adapter.ResourceBitmapAdapter;
import com.marvinlabs.widget.slideshow.playlist.SequentialPlayList;
import com.marvinlabs.widget.slideshow.transition.ZoomTransitionFactory;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import Extras.DialogStateListener;
import Extras.LogInDialogFragment;
import Extras.SignUpDialogFragment;
import ParseDBCommunicator.ParseDBCommunicator;


public class LogInActivity extends FragmentActivity implements DialogStateListener {

    private final static int LOGIN_DIALOG = 0;
    private final static int SIGNUP_DIALOG = 1;
    Button logIn_Button;
    Button signUp_Button;
    TextView title_text_view, sub_title_text_view;
    SlideShowView slideShowView;
    ResourceBitmapAdapter resourceBitmapAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseDBCommunicator parseDBCommunicator = new ParseDBCommunicator(this); //Create instance of parse communicator to initialize parse

        if(ParseUser.getCurrentUser() != null) {  //Check to see if user is cached
            Toast.makeText(getApplicationContext(), "Welcome, " + ParseUser.getCurrentUser().getString("first_name"), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("name", ParseUser.getCurrentUser().getString("first_name") + " " + ParseUser.getCurrentUser().getString("last_name"));
            startActivity(intent);
        }


        setContentView(R.layout.activity_log_in_layout);

        logIn_Button = (Button) findViewById(R.id.log_in_button);
        signUp_Button = (Button) findViewById(R.id.sign_up_button);
        title_text_view = (TextView) findViewById(R.id.title_textview);
        slideShowView = (SlideShowView) findViewById(R.id.slideshow);
        sub_title_text_view = (TextView) findViewById(R.id.sub_title_text_view);



        Typeface oldsans_typeface = Typeface.createFromAsset(getAssets(), "fonts/OldSansBlack.ttf");
        title_text_view.setTypeface(oldsans_typeface);


        ResourceBitmapAdapter adapter = new ResourceBitmapAdapter(this, new int[]{
                R.drawable.hu_tower_tinted, R.drawable.old_howardstudents_tinted, R.drawable.hu_graduation_tinted});

        SequentialPlayList sequentialPlayList = new SequentialPlayList();
        sequentialPlayList.setSlideDuration(5000);
        sequentialPlayList.setLooping(true);
        slideShowView.setAdapter(adapter);
        slideShowView.setPlaylist(sequentialPlayList);
        slideShowView.setTransitionFactory(new ZoomTransitionFactory());
        slideShowView.play();







        signUp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment(SIGNUP_DIALOG);
            }
        });

        logIn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment(LOGIN_DIALOG);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in_activity, menu);
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


    public void showDialogFragment(int type) {

        switch (type) {
            case LOGIN_DIALOG:
                LogInDialogFragment log_in_dialog_fragment = new LogInDialogFragment();
                log_in_dialog_fragment.setDialogStateListener(this);
                log_in_dialog_fragment.show(getSupportFragmentManager(), "dialog");
                log_in_dialog_fragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
                break;
            case SIGNUP_DIALOG:
                SignUpDialogFragment sign_up_dialog_fragment = new SignUpDialogFragment();
                sign_up_dialog_fragment.setDialogStateListener(this);
                sign_up_dialog_fragment.show(getSupportFragmentManager(), "dialog");
                sign_up_dialog_fragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
                break;
            default:
                break;
        }

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(logIn_Button, View.ALPHA, 1, 0);
        ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(signUp_Button, View.ALPHA, 1, 0);
        ObjectAnimator alphaAnimation3 = ObjectAnimator.ofFloat(title_text_view, View.ALPHA, 1, 0);
        ObjectAnimator alphaAnimation4 = ObjectAnimator.ofFloat(sub_title_text_view, View.ALPHA, 1, 0);



        List<ObjectAnimator> objectAnimators = new ArrayList<ObjectAnimator>();
        objectAnimators.add(alphaAnimation);
        objectAnimators.add(alphaAnimation2);
        objectAnimators.add(alphaAnimation3);
        objectAnimators.add(alphaAnimation4);

        ObjectAnimator[] arrayObjectAnimators = objectAnimators.toArray(new ObjectAnimator[objectAnimators.size()]);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(arrayObjectAnimators);
        animSet.setDuration(200);//1sec
        animSet.setInterpolator(new AccelerateInterpolator());
        animSet.start();
    }

    @Override
    public void OnOpenDialog() {
    }

    @Override
    public void OnCloseDialog() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(logIn_Button, View.ALPHA, 0, 1);
        ObjectAnimator alphaAnimation2 = ObjectAnimator.ofFloat(signUp_Button, View.ALPHA, 0, 1);
        ObjectAnimator alphaAnimation3 = ObjectAnimator.ofFloat(title_text_view, View.ALPHA, 0, 1);
        ObjectAnimator alphaAnimation4 = ObjectAnimator.ofFloat(sub_title_text_view, View.ALPHA, 0, 1);

        List<ObjectAnimator> objectAnimators = new ArrayList<ObjectAnimator>();
        objectAnimators.add(alphaAnimation);
        objectAnimators.add(alphaAnimation2);
        objectAnimators.add(alphaAnimation3);
        objectAnimators.add(alphaAnimation4);

        ObjectAnimator[] arrayObjectAnimators = objectAnimators.toArray(new ObjectAnimator[objectAnimators.size()]);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(arrayObjectAnimators);
        animSet.setDuration(150);//1sec
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.start();
    }


}



