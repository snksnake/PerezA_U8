package org.example.permisosenmarshmallow;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends Activity {

    private Button btnMapActivity;
    private Button arrancar;
    private Button detener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        arrancar = findViewById(R.id.boton_arrancar);
        detener = findViewById(R.id.boton_detener);

        btnMapActivity = findViewById(R.id.mapActivity);

        btnMapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setClass(
                        Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        arrancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startService(new Intent(Main2Activity.this,
                        ServicioMusica.class));
            }
        });

        detener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(Main2Activity.this,
                        ServicioMusica.class));
            }
        });
    }
}
