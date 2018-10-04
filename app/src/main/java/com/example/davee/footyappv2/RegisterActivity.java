package com.example.davee.footyappv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

public class RegisterActivity extends AppCompatActivity {
    TextView login;
    EditText username, email, password;
    Button register;

    JSONObject jsonObject;
    HttpURLConnection urlConnection;
    String query, results;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.loginText);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRegistration();
            }
        });
    }

    private void checkRegistration() {
        String username_holder = username.getText().toString();
        String email_holder = email.getText().toString();
        String password_holder = password.getText().toString();

        if (TextUtils.isEmpty(username_holder) || TextUtils.isEmpty(email_holder) || TextUtils.isEmpty(password_holder))
            Toast.makeText(RegisterActivity.this, "Username, email and password required", Toast.LENGTH_SHORT).show();
        else
            new Register().execute(username_holder, email_holder, password_holder);
    }

    @SuppressLint("StaticFieldLeak")
    class Register extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Registering user, please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", username.getText().toString().trim()).appendQueryParameter("email",
                        email.getText().toString().trim()).appendQueryParameter("password", password.getText().toString().trim());

                query = builder.build().getEncodedQuery();

                String link = "https://daveecom.000webhostapp.com//footyapp/register.php";
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
                    String user = username.getText().toString();
                    String mail = email.getText().toString();
                    String pass = password.getText().toString();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("username", user);
                    intent.putExtra("email", mail);
                    intent.putExtra("password", pass);

                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                    alert.setTitle("Failed");
                    alert.setMessage("Failed to register user");
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

    // Method to open LoginActivity
    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}