package com.educharge.educharge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import static android.R.attr.data;

public class LoginActivity extends AppCompatActivity implements OnTouchListener, View.OnClickListener{

    //Layout elements
    private EditText edtusername;
    private EditText edtpass;
    private TextView ttvSignup,ttvFogetpass;
    private Button btSignIn;
    private ImageView in_loginButton,twtr_loginButton,fb_loginButton,g_loginButton;

    //facebook
    private LoginButton fb_loginButton_root;
    //callback managers for facebook
    private CallbackManager callbackManager;
    //list of fields for user permission
    //required fields form user for registration
    String name = "data not found",fname = "data not found",lname = "data not found",email = "data not found",phone = "data not found",profile_picture_Url = "data not found",gender = "data not found",birthday = "data not found",password = "data not found";

    //dialog box
    private ProgressDialog progressDialog;

    //google variables
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    SignInButton g_loginButton_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtusername = (EditText)findViewById(R.id.edtusername) ;
        edtpass = (EditText)findViewById(R.id.edtpassword) ;
        btSignIn = (Button)findViewById(R.id.btSignIn);
        ttvSignup = (TextView)findViewById(R.id.ttvSignup);
        ttvFogetpass = (TextView)findViewById(R.id.ttvForgetpass);

        btSignIn.setOnClickListener(this);
        ttvSignup.setOnTouchListener(this);
        ttvFogetpass.setOnTouchListener(this);

        //methode and buttons for facebook
        fb_loginButton_root=(LoginButton)findViewById(R.id.fb_login_button_root);
        fb_loginButton=(ImageView)findViewById(R.id.fb_loginButton);
        fb_loginButton.setOnClickListener(this);
        initialize_facebook();

        //linkedin buttons and methodes
        in_loginButton=(ImageView) findViewById(R.id.in_loginButton);
        in_loginButton.setOnClickListener(this);
        //linkedin initialization called in the onclick methode

        // Google login button and methodes
        g_loginButton_root = (SignInButton) findViewById(R.id.sign_in_button);
        //Set the dimensions of the sign-in button.
        g_loginButton_root.setSize(SignInButton.SIZE_STANDARD);
        g_loginButton = (ImageView) findViewById(R.id.g_loginButton);
        g_loginButton.setOnClickListener(this);
        initialize_google_mail();

   }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //OnTouching the SignUp TextView following Action will be Performed
        if( v == ttvSignup){
            Intent toRegisterActivity = new Intent(this,RegisterActivity.class);
            startActivity(toRegisterActivity);
        }
        else if( v == ttvFogetpass){
            Intent toForgetActivity = new Intent(this, ForgetPassActivity.class);
            startActivity(toForgetActivity);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if( v == btSignIn ){
            String username = edtusername.getText().toString();
            String pass = edtpass.getText().toString();

            boolean flag = loginValidation(username,pass);
            if( flag ) {

                String email = edtusername.getText().toString().trim();
                String password = edtpass.getText().toString().trim();

                Intent i= new Intent(this,HomeActivity.class);
                startActivity(i);
            }
        }else if(v.getId() == R.id.in_loginButton){
            initializelinkedin();
        }else if (v.getId() ==  R.id.fb_loginButton){
            fb_loginButton_root.performClick();
        }else if (v.getId() ==  R.id.g_loginButton){
            g_loginButton_root.performClick();
            signIn();
            //Toast.makeText(this,"Signing in to yoyr gmail account..", Toast.LENGTH_SHORT).show();
        }
    }

    //  [ Form Validation ]
    boolean loginValidation(String username, String pass){
        //[For Email]
        Boolean flag=true;
        edtusername.setError(null);
        edtpass.setError(null);

        if(username.equals("")){
            edtusername.setError("Please enter email");
            flag=false;
        }
        if( username.indexOf('@') == -1 ){
            if(edtusername.getError()==null)edtusername.setError("Enter a vaild email");
            flag=false;
        }
        if( username.indexOf('.') == -1 ){
            if(edtusername.getError()==null)edtusername.setError("Enter a vaild email");
            flag=false;
        }
        // [ Password Validation ]
        if( pass.equals("")){
            edtpass.setError("Please enter password");
            flag=false;
        }
        if( pass.length()<6){
            if(edtpass.getError()==null)edtpass.setError("Password should be of minimum 6 characters");
            flag=false;
        }
        return flag;
    }//[ regFormValidation ]


//[Google Login]
    private void initialize_google_mail() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    private void signIn()
    {
        Toast.makeText(this,"signIn()", Toast.LENGTH_SHORT).show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }
    private void handleSignInResult(GoogleSignInResult result)
    {

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        Toast.makeText(this,"handleSignInResult:" +result.isSuccess(), Toast.LENGTH_SHORT).show();
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            Toast.makeText(this, personName+","+personEmail+","+personFamilyName+","+personGivenName , Toast.LENGTH_SHORT).show();

        } else
        {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
            signOut();
        }
    }
//END[Google Login]

//facebook login
    private void initialize_facebook() {
            //sdk needs tobe initialize before using any of its method
            FacebookSdk.sdkInitialize(getApplicationContext());

            callbackManager=CallbackManager.Factory.create();
            fb_loginButton_root.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

            fb_loginButton_root.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken()
                            ,new GraphRequest.GraphJSONObjectCallback()
                                {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        // Application code
                                        // Log.v("LoginActivity", response.toString());
                                        try {
                                            //Toast.makeText(MainActivity.this,"json : "+object.toString(), Toast.LENGTH_LONG).show();
                                            // Toast.makeText(getApplicationContext(),"graph : "+response.toString(), Toast.LENGTH_LONG).show();

                                            name=object.getString("name");
                                            fname=object.getString("first_name");
                                            lname=object.getString("last_name");
                                            gender=object.getString("gender");
                                            //email=object.getString("email");
                                            birthday=object.getString("birthday");
                                            profile_picture_Url=object.getJSONObject("picture").getJSONObject("data").getString("url");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        Toast.makeText(getApplicationContext(),"Welcome To Educharge\n"+fname,Toast.LENGTH_LONG).show();

                                        Toast.makeText(LoginActivity.this,name+","+fname+","+lname+","+gender+","+birthday, Toast.LENGTH_SHORT).show();

                                        /* Intent i =new Intent(getApplicationContext(),StartPage.class);
                                        i.putExtra("site","facebook");
                                        i.putExtra("name",name);
                                        i.putExtra("fname",fname);
                                        i.putExtra("lname",lname);
                                        i.putExtra("picUrl",profile_picture_Url);
                                        i.putExtra("birthday",birthday);
                                        // i.putExtra("email",email);
                                        i.putExtra("gender",gender);
                                        startActivity(i);*/
                                        }
                                });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields","name,email,gender,birthday,first_name,last_name,picture");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(),"Login Canceled by user", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(),"Facebook Login Failed"+exception.toString(), Toast.LENGTH_LONG).show();
            }
            });
    }//end of facebook init.

    //Initialize LinkedIn Login
    private void initializelinkedin() {
    final Activity thisActivity = this;

            //Step 1 - Initialize a connection to LinkedIn
            /*
            The LISessionManager class is the heart of the Mobile SDK for Android.  It provides all of the necessary functionality to create and manage the LISession object used to perform the additional Mobile SDK functions.
            There are two ways to initialize a LinkedIn session using the overloaded init() method:
            -Without an existing access token <-
            -With a previously acquired access token
            */

            //Initializing by requesting a new access token
            LISessionManager.getInstance(getApplicationContext()).init(thisActivity, buildScope(), new AuthListener() {
                @Override
                public void onAuthSuccess() {
                    // Authentication was successful. You can now do other calls with the SDK.
                    //generating url to fetch personal Information ie. id, first-name, last-name, email-address, headline, picture-urls

                    String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address,headline,picture-urls::(original),picture-url)?format=json";

                    APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                    apiHelper.getRequest(thisActivity, url, new ApiListener() {
                        @Override
                        public void onApiSuccess(ApiResponse apiResponse) {
                            // Success!
                            //getting response as JSON Object
                            JSONObject jsonObject=apiResponse.getResponseDataAsJson();
                            //retrieving required data (value) from JSON Object by giving the key name
                            // ie. { key : "value" } <- JSON Object
                            try {
                            //Toast.makeText(thisActivity,jsonObject.toString(), Toast.LENGTH_LONG).show();
                            fname=jsonObject.getString("firstName");
                            lname=jsonObject.getString("lastName");
                            email=jsonObject.getString("emailAddress");
                            Toast.makeText(thisActivity,apiResponse.toString(), Toast.LENGTH_SHORT).show();
                            //gender
                            //birthday
                            //mobile
                            profile_picture_Url=jsonObject.getString("pictureUrl");
                            }
                            catch(JSONException liApiError){
                            Toast.makeText(thisActivity,"JSON Error :"+liApiError.toString(), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(getApplicationContext(),"Welcome To Educharge\n"+fname,Toast.LENGTH_LONG).show();

                           /*  Intent i =new Intent(getApplicationContext(),StartPage.class);
                            i.putExtra("site","linkedin");
                            i.putExtra("fname",fname);
                            i.putExtra("lname",lname);
                            i.putExtra("picUrl",profile_picture_Url);
                            i.putExtra("email",email);
                            startActivity(i);*/
                            }
                        @Override
                        public void onApiError(LIApiError liApiError) {
                                // Error making GET request!
                                Toast.makeText(thisActivity,"Api Error :"+liApiError.toString(), Toast.LENGTH_SHORT).show();
                                }
                    });
                }

                @Override
                public void onAuthError(LIAuthError error) {
                    // Handle authentication errors
                    Toast.makeText(thisActivity,"Login Failed\nerror:"+error.toString(), Toast.LENGTH_SHORT).show();
                }
            }, true);
    }//end of LinkedIn init.

    //Scope of access for linkedin
    private static Scope buildScope() {
            return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE,Scope.R_EMAILADDRESS);
    }

    //handel incoming login results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //Handle responses from Facebook mobile app(to handel callback)
            callbackManager.onActivityResult(requestCode,resultCode,data);

            //Handle responses from the LinkedIn mobile app(to handel callback)
            LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Toast.makeText(this,requestCode+",(request code)", Toast.LENGTH_SHORT).show();
                Toast.makeText(this,resultCode+",(result code)", Toast.LENGTH_SHORT).show();
                Toast.makeText(this,data+",(data)", Toast.LENGTH_SHORT).show();
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }

    }

    //methode for Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.menu_item_about):    Toast.makeText(getApplicationContext(), "...Educharge...", Toast.LENGTH_LONG).show();
                                            return true;

            case (R.id.menu_item_exit):     //firebaseAuth.signOut();
                                            Toast.makeText(getApplicationContext(), "Signed out!", Toast.LENGTH_LONG).show();
                                            signOut();
                                            System.exit(0);
                                            return true;

            default:                        return super.onOptionsItemSelected(item);
        }
    }

}
