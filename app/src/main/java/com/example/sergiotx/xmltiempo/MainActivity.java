package com.example.sergiotx.xmltiempo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String urlVitoria = "http://www.aemet.es/xml/municipios/localidad_01059.xml";

    private TextView text;
    private List<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);

        LoadXml loader = new LoadXml();
        loader.execute(urlVitoria);
    }

    class LoadXml extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            XmlParser parser = new XmlParser(params[0]);
            datos = parser.returnTemp();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            String maxTemp = "", minTemp = "";

            if (datos.size() > 0) {
                maxTemp = datos.get(0);
                minTemp = datos.get(1);
            }
            text.setText("Temperaturas en Vitoria para hoy:" + "\n\n"
                    + "Mínima: " + minTemp + "\n"
                    + "Máxima: " + maxTemp + "\n");
        }
    }
}
