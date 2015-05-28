package Extras;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.parse.ParseUser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ParseDBCommunicator.ParseDBCommunicator;
import SignUpViewPager.SignUpPagerAdapter;
import app.cedar.cedarconnect.HomeActivity;
import app.cedar.cedarconnect.R;


/**
 * Created by Saboor Salaam on 12/19/2014.
 */
public class SignUpDialogFragment extends DialogFragment {

    DialogStateListener dialogStateListener;
    ParseDBCommunicator parseDBCommunicator;
    String id, password;
    List<ParseUser> userHolder;


    public SignUpDialogFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        Typeface sans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OldSansBlack.ttf");
        View view = inflater.inflate(R.layout.sign_up_dialog_fragment_layout, container);

        parseDBCommunicator = new ParseDBCommunicator(getActivity());

        final Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);   //Instantiate error shake animation for Edit Text View

        final EditText id_text_view = (EditText) view.findViewById(R.id.id_edit_text);
        final Button continue_button = (Button) view.findViewById(R.id.continue_button);
        final ImageView back_button = (ImageView) view.findViewById(R.id.back_button);
        final ViewFlipper sign_up_view_flipper = (ViewFlipper) view.findViewById(R.id.sign_up_form_view_flipper);

        View child1 = inflater.inflate(R.layout.sign_up_screen1, container);
        View child2 = inflater.inflate(R.layout.sign_up_screen2, container);
        View child3 = inflater.inflate(R.layout.sign_up_screen3, container);
        View child4 = inflater.inflate(R.layout.sign_up_screen_error, container);
        sign_up_view_flipper.addView(child1);
        sign_up_view_flipper.addView(child2);
        sign_up_view_flipper.addView(child3);
        sign_up_view_flipper.addView(child4);


        final EditText verify_id_edit_text = (EditText) sign_up_view_flipper.getChildAt(0).findViewById(R.id.verify_id_edit_text);
        final EditText verify_name_edit_text = (EditText) sign_up_view_flipper.getChildAt(1).findViewById(R.id.verify_name_edit_text);
        final EditText verify_gpa_edit_text = (EditText) sign_up_view_flipper.getChildAt(1).findViewById(R.id.verify_gpa_edit_text);
        final EditText password_edit_text = (EditText) sign_up_view_flipper.getChildAt(2).findViewById(R.id.password_edit_text);
        final EditText confirm_password_edit_text = (EditText) sign_up_view_flipper.getChildAt(2).findViewById(R.id.confirm_password_edit_text);


        back_button.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {
            dismiss();
        }
        });

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = sign_up_view_flipper.getDisplayedChild();
                switch(position){
                    case 0:
                        String hu_id = verify_id_edit_text.getText().toString();
                            if(patternMatch("^(?!\\s*$)[0-9\\s]{8}$", hu_id )) // if it is a valid HU ID (8 digits only numeric)
                            {
                                    userHolder = parseDBCommunicator.getUser(hu_id, new ParseDBCommunicator.connectionFailedListener() { //Check Parse DB for ID
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

                                    if (userHolder.isEmpty()) {  //If the ID isn't in Parse DB show error
                                        verify_id_edit_text.setError("HU ID doesn't exist :(");
                                        verify_id_edit_text.startAnimation(shake);
                                    } else { //If ID is in parse go to next step
                                         sign_up_view_flipper.showNext();
                                    }
                            } else {
                                verify_id_edit_text.setError("Invalid HU ID");
                                verify_id_edit_text.startAnimation(shake);
                            }
                        break;
                    case 1:

                        if(!verify_gpa_edit_text.getText().toString().isEmpty() && !verify_name_edit_text.getText().toString().isEmpty()) // if it is a valid name
                        {
                            if((verify_name_edit_text.getText().toString().equals(userHolder.get(0).getString("first_name"))) && ((Float.parseFloat(verify_gpa_edit_text.getText().toString())) == userHolder.get(0).getNumber("gpa").floatValue())) {
                                sign_up_view_flipper.showNext();
                            }else{
                                verify_gpa_edit_text.setError("One of these doesn't match our records, try again :(");
                                verify_gpa_edit_text.startAnimation(shake);
                                verify_name_edit_text.setError("Try again :(");
                                verify_name_edit_text.startAnimation(shake);
                            }

                        } else {
                            verify_name_edit_text.setError("Please enter your name");
                            verify_name_edit_text.startAnimation(shake);
                            verify_gpa_edit_text.setError("Please enter your GPA");
                            verify_gpa_edit_text.startAnimation(shake);
                        }
                        break;
                    case 2:
                        String password = password_edit_text.getText().toString();
                        String confirmed_password = confirm_password_edit_text.getText().toString();

                        if(patternMatch("^[a-zA-Z0-9]*$" , password)) // if it is a valid password
                        {
                            if(password.equals(confirmed_password))
                            {
                               Toast.makeText(getActivity(), "Account created! Sign in to begin using CEDAR Connect", Toast.LENGTH_LONG).show();
                                dismiss();
                            }else{
                                confirm_password_edit_text.setError("Passwords do not match!");
                                confirm_password_edit_text.startAnimation(shake);
                            }
                        } else {
                            password_edit_text.setError("Password does not meet criteria :(");
                            password_edit_text.startAnimation(shake);
                        }
                        break;
                    default:
                        sign_up_view_flipper.setDisplayedChild(3);
                        break;
                }
            }
        });





        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(dialogStateListener != null){
            dialogStateListener.OnOpenDialog();
        }
        setRetainInstance(true);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(dialogStateListener != null) {
            dialogStateListener.OnCloseDialog();
        }
        super.onDismiss(dialog);
    }

    public void setDialogStateListener(DialogStateListener dialogStateListener)
    {
        this.dialogStateListener = dialogStateListener;
    }

    public boolean patternMatch(String expression, CharSequence inputStr ){

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        }
        else return false;
    }

}
