package com.lics.proyectou2lectorqr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button qrBtn, ntBtn, tmpBtn;
    TextView infoTV, teleIdTV;
    RadioGroup edoGP;
    RadioButton malRBtn, bienRBtn;

    DatabaseReference rDatabase;
    FirebaseDatabase database;

    String docenteID, alumnoID;
    String docenteTelegramID;
    String ncAlu, nombreCompleto, estado, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        docenteTelegramID = "";
        infoTV = findViewById(R.id.infoTV);
        teleIdTV = findViewById(R.id.teleID);
        edoGP = findViewById(R.id.edoGP);

        database = FirebaseDatabase.getInstance("https://aplicacionubicua-default-rtdb.firebaseio.com/");
        rDatabase = database.getReference();


        new IntentIntegrator(MainActivity.this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        alumnoID = result.getContents();
        leerDatos();

        Toast.makeText(MainActivity.this, "Base de datos actualizada", Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "Docente Notificado", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, MonitoringScreen.class);
                        startActivity(intent);
                    }
                }, 5000);
    }


    public void notifyTelegram(){
        MiPeticionREST obj = new MiPeticionREST(docenteTelegramID);
        //String text = ncAlu +"/"+nombreCompleto+"/"+estado;
        String text = nombreCompleto + "("+ncAlu +") entró con "+estado +".";
        obj.execute("GET-SEND", text);
    }

    public void leerDatos(){
        String userID = rDatabase.child("Usuarios").child(alumnoID).getKey();

        rDatabase.child("Usuarios").child(alumnoID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Bundle extras = getIntent().getExtras();
                String temperatura_main_activity = extras.getString("temperatura");

                rDatabase.child("Usuarios").child(alumnoID).child("Temperatura").setValue(Float.parseFloat(temperatura_main_activity));

                if(Float.parseFloat(temperatura_main_activity) > 38){
                    rDatabase.child("Usuarios").child(alumnoID).child("EstadoActual").setValue("Mal Estado");
                }
                else{
                    rDatabase.child("Usuarios").child(alumnoID).child("EstadoActual").setValue("Buen Estado");
                }
                if(dataSnapshot.exists()){
                    infoTV.setText("N. Control: "+ dataSnapshot.child("NC").getValue().toString()+"\n"+
                            "Nombre: "+dataSnapshot.child("Nombre").getValue().toString()+"\n"+
                            "Apellido Paterno: "+dataSnapshot.child("ApellidoP").getValue().toString()+"\n"+
                            "Apellido Materno: "+dataSnapshot.child("ApellidoM").getValue().toString()+"\n"+
                            "Estado: "+dataSnapshot.child("EstadoActual").getValue().toString() +"\n"+
                            "Temperatura: " + temperatura_main_activity);

                    rDatabase.child("UltimoUsuario").setValue(userID);

                    ncAlu = dataSnapshot.child("NC").getValue().toString();
                    nombreCompleto = dataSnapshot.child("Nombre").getValue().toString()+" "+dataSnapshot.child("ApellidoP").getValue().toString()+" "+dataSnapshot.child("ApellidoM").getValue().toString();
                    estado = dataSnapshot.child("EstadoActual").getValue().toString();
                    docenteID = dataSnapshot.child("ProfesoresAsignados").child("ID").getValue().toString();
                    docenteTelegramID = docenteID;
                }
                notifyTelegram();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }
}