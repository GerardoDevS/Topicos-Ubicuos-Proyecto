package com.lics.proyectou2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InicioAlu extends AppCompatActivity {

    TextView ncInicioTV, estadoActualTV;
    ImageView qrCode;
    String nc;

    FirebaseUser user;

    FirebaseDatabase fDatabase;
    DatabaseReference rDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Bundle bundle = this.getIntent().getExtras();
        nc = bundle.getString("nc");

        qrCode = (ImageView) findViewById(R.id.qrCode);
        ncInicioTV = (TextView) findViewById(R.id.ncInicioTV);
        estadoActualTV = (TextView) findViewById(R.id.estadoActualTV);

        qrCode.setImageBitmap(QR.createQR(nc));
        //User
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        //Database
        fDatabase = FirebaseDatabase.getInstance("https://aplicacionubicua-default-rtdb.firebaseio.com/");
        rDatabase = fDatabase.getReference();
        //
        if(user != null)
        leerDatos();
        //ncInicioTV.setText(rDatabase.get);
    }

    public void leerDatos(){
        rDatabase.child("Usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    ncInicioTV.setText("NC: " +dataSnapshot.child("NC").getValue().toString());
                    estadoActualTV.setText("Estado: "+dataSnapshot.child("EstadoActual").getValue().toString());
                }
                //Log.d("tag", "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }
}