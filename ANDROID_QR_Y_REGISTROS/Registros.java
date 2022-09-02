package com.lics.proyectou2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registros extends AppCompatActivity {

    private TextView mTextViewData2;
    DatabaseReference mDatabase;
    FirebaseUser user;
    String uid;

    private Adapter adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Usuarios> usuariosList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getString("uid");

        mTextViewData2 = (TextView) findViewById(R.id.textViewData2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance("https://aplicacionubicua-default-rtdb.firebaseio.com/").getReference();

        //User
        user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    mTextViewData2.setBackgroundColor(Color.parseColor("#FFD200"));

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mTextViewData2.setBackgroundColor(Color.parseColor("#fa8072"));
                        }
                    }, 1000);

                    usuariosList.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        String apellidoM = ds.child("ApellidoM").getValue().toString();
                        String apellidoP = ds.child("ApellidoP").getValue().toString();
                        String nombre = ds.child("Nombre").getValue().toString();
                        String nc = ds.child("NC").getValue().toString();
                        String estadoActual = ds.child("EstadoActual").getValue().toString();
                        usuariosList.add(new Usuarios(apellidoM, apellidoP, nombre, nc, estadoActual));
                    }
                    adapter = new Adapter(usuariosList, R.layout.datos);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});
    }
}