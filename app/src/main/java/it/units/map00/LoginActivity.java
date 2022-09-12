package it.units.map00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText mNomeUtente;
    EditText mPassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        // Se l'utente è loggato andare in MainActivity

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url

            String email = user.getEmail();

            Intent intent3 = new Intent(this, MainActivity.class);
            intent3.putExtra("msg", email);
            finish();

            startActivity(intent3);

        }

    }


    public void btnLoginClick(View view) {
        Log.d("LoginActivity", "Login Button Click");

        // TODO 2: Collegare le variabili ai Widgets
        mNomeUtente = (EditText)findViewById(R.id.etRegName);
        mPassword = (EditText)findViewById(R.id.etRegPass);

        String nomeUtente = mNomeUtente.getText().toString();
        String password = mPassword.getText().toString();

        Log.d("LoginActivity",nomeUtente );
        Log.d("LoginActivity",password );

        if(!(nomeUtente.length()>7)||!(nomeUtente.contains("@"))) {
            Toast.makeText(this, "email non valida", Toast.LENGTH_LONG).show();
            return;
        }else if(!(password.length()>3)){
            Toast.makeText(this, "password non valida", Toast.LENGTH_LONG).show();
            return;

        }else {
            loginUser(nomeUtente, password);
        }

        /*
        if(nomeUtente.equals(mUtente) && password.equals(mPass) ){


            Intent intent3 = new Intent(this, MainActivity.class);
            intent3.putExtra("msg", nomeUtente);

            startActivity(intent3);
        }else{
            Toast.makeText(this, "Nome utente o Password non corretti", Toast.LENGTH_SHORT).show();
        }
        */



    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void tvRegistratiClick(View view) {

        Log.d("LoginActivity", "Registrati Click");

        Intent intent1 = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent1);

    }
}