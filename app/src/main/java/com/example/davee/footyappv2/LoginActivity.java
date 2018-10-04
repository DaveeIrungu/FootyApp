package com.example.davee.footyappv2;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    TextView register;
    CheckBox checkBox;

    JSONObject jsonObject;
    HttpURLConnection urlConnection;
    String query, results;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerNow);
        checkBox = findViewById(R.id.checkbox);

        //mode private means the data is only privy to this application
        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        //modifications to the data in the preferences and automatically commit those changes back to the SharedPreferences object
        editor = sharedPreferences.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                verifyLogin();

                if (checkBox.isChecked()) {
                    editor.putBoolean("saveDetails", true);
                    editor.putString("username", user);
                    editor.putString("password", pass);
                    editor.apply();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        //savelogin is a boolean
        saveLogin = sharedPreferences.getBoolean("saveDetails", false);
        if (saveLogin) {
            username.setText(sharedPreferences.getString("username", ""));
            password.setText(sharedPreferences.getString("password", ""));
            checkBox.setChecked(true);
        }
    }

    //checks if all formss have been filled
    private void verifyLogin() {
        String username_holder = username.getText().toString();
        String password_holder = password.getText().toString();

        if (TextUtils.isEmpty(username_holder) || TextUtils.isEmpty(password_holder))
            Toast.makeText(LoginActivity.this, "Please enter your username and password", Toast.LENGTH_SHORT).show();
        else
            new Login().execute(username_holder, password_holder);
    }

    class Login extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging in, please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", username.getText().toString().trim()).appendQueryParameter(
                        "password", password.getText().toString().trim());

                query = builder.build().getEncodedQuery();

                String link = "https://daveecom.000webhostapp.com/footyapp/login.php";
                URL url = new URL(link);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStreamWriter.write(query);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line).append("\n");
                results = stringBuilder.toString();
                bufferedReader.close();
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                jsonObject = new JSONObject(results);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Failed");
                    alert.setMessage("Invalid email or password. Please try again.");
                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    // Method to open RegisterActivity
    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
