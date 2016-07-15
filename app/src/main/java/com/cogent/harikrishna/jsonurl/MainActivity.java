package com.cogent.harikrishna.jsonurl;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText name,et1,et2;
    private JSONObject jsonObject1=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.name);
        et1 = (EditText) findViewById(R.id.email);
        et2 = (EditText) findViewById(R.id.pwd);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/registration");
            }
        });


    }

    public class JSONTask extends AsyncTask<String, String, String>{

        String email = et1.getText().toString().trim();
        String password = et2.getText().toString().trim();
        String fname = name.getText().toString().trim();


        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;

            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");

                jsonObject1 = new JSONObject();
                jsonObject1.put("Full Name",""+fname);
                jsonObject1.put("Email",""+email);
                jsonObject1.put("Password",""+password);
                jsonObject1.toString().trim();

                //Header
                connection.setRequestProperty("fname",""+jsonObject1);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = bufferedReader.readLine())!=null){
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null){
                    connection.disconnect();
                }
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


}
