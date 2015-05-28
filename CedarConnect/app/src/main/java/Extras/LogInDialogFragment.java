package Extras;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ParseDBCommunicator.ParseDBCommunicator;
import app.cedar.cedarconnect.HomeActivity;
import app.cedar.cedarconnect.R;


/**
 * Created by Saboor Salaam on 12/19/2014.
 */
public class LogInDialogFragment extends DialogFragment {

    DialogStateListener dialogStateListener;
    ParseDBCommunicator parseDBCommunicator;
    Button sign_in_button;
    TextView title_text_view, sub_title_text_view,create_account_button;
    EditText id_text_view, password_text_view;
    ImageView back_button;

    final int VALIDATION_EMAIL = 3250;
    final int VALIDATION_URL = 3251;
    final int VALIDATION_NUMBER = 3252;
    final int VALIDATION_HU_ID = 3253;
    final static int VALIDATION_PASSWORD = 3254;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;

        parseDBCommunicator = new ParseDBCommunicator(getActivity());

        Typeface sans=Typeface.createFromAsset(getActivity().getAssets(), "fonts/OldSansBlack.ttf");
        View view = inflater.inflate(R.layout.log_in_dialog_fragment_layout, container);

        id_text_view = (EditText) view.findViewById(R.id.id_edit_text);
        password_text_view = (EditText) view.findViewById(R.id.confirm_password_edit_text);
        sign_in_button = (Button) view.findViewById(R.id.signin_button);
        back_button = (ImageView) view.findViewById(R.id.back_button);
        title_text_view = (TextView) view.findViewById(R.id.title_text_view);
        sub_title_text_view = (TextView) view.findViewById(R.id.sub_title_text_view);
        create_account_button = (TextView) view.findViewById(R.id.create_account_button);

        title_text_view.setTypeface(sans);
        //sub_title_text_view.setTypeface(sans);

        final Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);

        create_account_button.setOnClickListener(new View.OnClickListener() {// WORK ON THIS CODE!!!!!!
            @Override
            public void onClick(View v) {
                SignUpDialogFragment sign_up_dialog_fragment = new SignUpDialogFragment();
                sign_up_dialog_fragment.show(getFragmentManager(), "dialog");
                sign_up_dialog_fragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);


                if(dialogStateListener != null){
                    dialogStateListener.OnOpenDialog();
                }

                dismiss();


            }
        });

        back_button.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {dismiss();}
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!id_text_view.getText().toString().isEmpty() && !password_text_view.getText().toString().isEmpty()) {//Check if either field is empty

                    if (patternMatch("^(?!\\s*$)[0-9\\s]{8}$", id_text_view.getText().toString())) // check if it is a valid HU ID (8 digits only numeric)
                    {
                        String id = id_text_view.getText().toString();
                        String password = password_text_view.getText().toString();
                        ParseUser parseUser = parseDBCommunicator.logIn(id, password, new ParseDBCommunicator.connectionFailedListener() {
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

                        if (parseUser == null) {
                            id_text_view.setError("Information incorrect! Try again :( ");
                            password_text_view.setError("Information incorrect! Try again :( ");
                        } else {
                            Toast.makeText(getActivity(), "Welcome, " + parseUser.getString("first_name"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.putExtra("name", parseUser.getString("first_name") + " " + parseUser.getString("last_name"));
                            startActivity(intent);
                        }


                    }else {
                        id_text_view.setError("Invalid HU ID");
                        id_text_view.startAnimation(shake);
                    }
            } else { //If id or password field are empty
                    password_text_view.setError("Enter HU ID and password");
                    password_text_view.startAnimation(shake);
                }

                }
        });



        id_text_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!patternMatch("^(?!\\s*$)[0-9\\s]{8}$", id_text_view.getText().toString())) {
                        id_text_view.setError("Invalid HU ID");
                        id_text_view.startAnimation(shake);
                    }

                }
            }
        });

        password_text_view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!patternMatch("^(?!\\s*$)[0-9\\s]{8}$" , password_text_view.getText().toString()))
                    {
                        password_text_view.setError("Password must be atleast 6 characters long [a-z,0-9]");
                        password_text_view.startAnimation(shake);
                    }

                }
            }
        });




        return view;
    }



    public LogInDialogFragment() {
    }

    public void setDialogStateListener(DialogStateListener dialogStateListener)
    {
        this.dialogStateListener = dialogStateListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(dialogStateListener != null) {
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

    public boolean patternMatch(String expression, CharSequence inputStr ){

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        }
        else return false;
    }


}
