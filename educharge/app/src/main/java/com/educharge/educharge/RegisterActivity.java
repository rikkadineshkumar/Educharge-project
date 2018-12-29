package com.educharge.educharge;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.educharge.educharge.MailSender.MailSender;
import com.educharge.educharge.Models.Profile;
import com.educharge.educharge.SpringRestServices.HttpRequestPostProfile;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class RegisterActivity extends AppCompatActivity implements View.OnTouchListener{
    //layout components
    private TextView ttvSignIn;
    private EditText edtemail,edtrepass,edtpass;
    private EditText edtfname,edtlname,edtmobile,edtDOB,edtrole;
    private EditText edtotpcode;
    private Spinner spinner_phone,spinner_gender;
    private Button btnRegister;
    private ProgressDialog progressDialog ;
    private Dialog dialog;
    //extra variables for data manipulation
    private String fname,lname,email,password,gender,mobile,birthday,countryCode,uid;
    private int flag_gender = 0,flag_cuontryCode = 0;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //linking layout element with corresponding variables
        edtfname = (EditText) findViewById(R.id.edtfname);
        edtlname = (EditText) findViewById(R.id.edtlname);
        edtmobile = (EditText) findViewById(R.id.edtmobile);
        edtDOB = (EditText) findViewById(R.id.edtdob);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtpass = (EditText) findViewById(R.id.edtpass);
        edtrepass = (EditText) findViewById(R.id.edtrepass);
        edtrole = (EditText) findViewById(R.id.edtrole);

        spinner_phone = (Spinner) findViewById(R.id.country_code_spinner);
        spinner_gender = (Spinner) findViewById(R.id.gender_spinner);

        ttvSignIn = (TextView) findViewById(R.id.ttvSignIn);

        btnRegister = (Button) findViewById(R.id.btRegister);
        //END of linking layout element with corresponding variables

        //awesome validation
        awesomeValidation.addValidation(this,R.id.edtfname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this,R.id.edtlname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this,R.id.edtmobile,"^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this,R.id.edtdob,"^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
        awesomeValidation.addValidation(this,R.id.edtemail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this,R.id.edtrole, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.roleerror);
        awesomeValidation.addValidation(this,R.id.edtfname,"",R.string.nameerror);
        //awesomeValidation.addValidation(this,R.id.edtpass,, R.string.passworderror);
        //end of awesome validation

        //setting-up onClick & onTouch listeners to components
        ttvSignIn.setOnTouchListener(this);

        spinner_phone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryCode = spinner_phone.getItemAtPosition(position).toString();
                flag_cuontryCode = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = spinner_gender.getItemAtPosition(position).toString();
                flag_gender = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //progressDialog.setMessage("Sending OTP to your mail address");
                //progressDialog.show();
                //MailSender mailSender = new MailSender(getApplicationContext(),edtemail.toString().trim(),"Educharge Email verification","your OTP for Email Verification number is :: 8880");
                //mailSender.execute();
                //Toast.makeText(RegisterActivity.this, "mail sent ! ", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
/*
                dialog.setTitle("Enter the OTP");
                dialog.setContentView(R.layout.otp_verification_dialog_layout);
                edtotpcode = (EditText) findViewById(R.id.edtOtpCode);
                if(edtotpcode.getText().toString().equals("8880")){
*/
                    Profile p = new Profile();

                    String date=edtDOB.getText().toString().trim();
                    Date d =new Date(Integer.parseInt(date.split("/")[2]),Integer.parseInt(date.split("/")[1]),Integer.parseInt(date.split("/")[0]),0,0,0);

                    p.setFname(edtfname.getText().toString().trim());
                    p.setLname(edtlname.getText().toString().trim());
                    p.setPhone(edtmobile.getText().toString().trim());
                    p.setDob(d);
                    p.setEmail(edtemail.getText().toString().trim());
                    p.setPassword(edtpass.getText().toString().trim());
                    p.setRole(edtrole.getText().toString().trim());

                    boolean flag = awesomeValidation.validate()&&edtpass.getText().toString().equals(edtrepass.getText().toString());

                if( flag ){
                        // Add the request to the RequestQueue.
                    HttpRequestPostProfile httpRequestPostProfile = new HttpRequestPostProfile();
                    httpRequestPostProfile.profile=p;
                    httpRequestPostProfile.execute();

                    while(httpRequestPostProfile.res.equals("0"));

                    Toast.makeText(RegisterActivity.this,httpRequestPostProfile.res.toString(), Toast.LENGTH_SHORT).show();

                    if(httpRequestPostProfile.res.equals("Registration Successful")) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }

                    }
                else {
                    edtrepass.setError("Password not matching");
                }
            }
        });
        //END of setting-up onClick & onTouch listeners to components
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if( v == ttvSignIn){
            finish();
            Intent toSignIn = new Intent(this,MainActivity.class);
            startActivity(toSignIn);
        }
        return false;
    }
}

///////////////////////////////////////////////////////////old codes//////////////////////////////////////////////////////////////////////
/*  Gson gson = new Gson();
                    final String jsonString = gson.toJson(p);
                    try {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        JSONObject jsonObject = new JSONObject(jsonString);

                        String url = "http://52.14.15.227/Profiles/createProfile";
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        Toast.makeText(RegisterActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Toast.makeText(RegisterActivity.this,volleyError.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                        queue.add(jsonObjectRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
