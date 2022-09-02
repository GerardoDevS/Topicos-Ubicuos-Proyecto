package com.lics.proyectou2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText ncET;
    EditText passET;
    RadioGroup tipoRG;
    RadioButton aluRB;
    RadioButton docRB;
    Button entrarBtn;

    FirebaseDatabase fDatabase;
    DatabaseReference rDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase auth
        mAuth = FirebaseAuth.getInstance();
        //
        ncET = (EditText) findViewById(R.id.nCtrl);
        passET = (EditText) findViewById(R.id.pass);
        tipoRG = (RadioGroup) findViewById(R.id.tipoGroup);
        aluRB = (RadioButton) findViewById(R.id.alu);
        docRB = (RadioButton) findViewById(R.id.doc);
        entrarBtn = (Button) findViewById(R.id.entrar);



        entrarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(aluRB.isChecked()) autorizacion(true);else autorizacion(false);

            }
        });



    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //mAuth.updateCurrentUser(currentUser);
    }

    public void autorizacion(boolean alumno) {
        mAuth.signInWithEmailAndPassword("l" + ncET.getText().toString() + "@cdmadero.tecnm.mx", passET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, InicioAlu.class);
                            Bundle bundle = new Bundle();

                            bundle.putString("nc", user.getUid());
                            intent.putExtras(bundle);
                            if (alumno)
                                startActivity(intent);
                            else {
                                Intent intent1 = new Intent(MainActivity.this, Registros.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("uid", user.getUid());
                                intent1.putExtras(bundle1);
                                startActivity(intent1);
                            }
                            //mAuth.updateCurrentUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Unsucess", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Fallo en la autenticación",
                                    Toast.LENGTH_SHORT).show();
                            //mAuth.updateCurrentUser(null);
                        }

                        // ...
                    }

                });
    }
}