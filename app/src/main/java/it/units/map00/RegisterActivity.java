package it.units.map00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = "RegisterActivity";

    private EditText mConfermaPassword;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mNome;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();

        mAuth = FirebaseAuth.getInstance();

    }

    private void initUI() {
        mEmail = findViewById(R.id.etRegEmail);
        mPassword = findViewById(R.id.etRegPass);
        mConfermaPassword = findViewById(R.id.etRegPassConf);
        mNome = findViewById(R.id.etRegMail);
    }

    private void createFirebaseUser(String email, String password, final String nome){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.i("Registration", "createUserWithEmail:success");

                            setNome(nome);

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            finish();
                            startActivity(intent);

                        } else {
                            Log.i("Registration", "createUserWithEmail:failure", task.getException());

                            showDialog("Errore nella Registrazione","Errore", android.R.drawable.ic_dialog_alert);

                        }

                    }
                });
    }


    private void setNome(String nome){
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();

        user.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("setNome", "Nome Caricato con successo");
                }else{
                    Log.i("setNome", "Errore nel caricamento del nome");
                }
            }
        });

    }

    private void showDialog(String message, String title, int icon){

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(icon)
                .show();

    }

    public void btnRegistratiClick(View view) {

        String nome = mNome.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(!nomeValido(nome) )
            Toast.makeText(getApplicationContext(),"Nome non Valido", Toast.LENGTH_SHORT).show();
        else if(!emailValida(email)){
            Toast.makeText(getApplicationContext(),"Email non Valida", Toast.LENGTH_SHORT).show();
        }
        else if(!passwordValida(password)){
            Toast.makeText(getApplicationContext(),"Password non Valida", Toast.LENGTH_SHORT).show();
        }else {
            createFirebaseUser(email, password, nome);
        }

    }

    public void tvLoginClick(View view) {

        Intent intent2 = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent2);
    }


    private boolean nomeValido(String nome){
        if(nome.length()>3)
            return true;
        else
            return false;
    }

    private boolean emailValida(String email){
        return email.contains("@");
    }

    private boolean passwordValida(String password){
        String confermaPassword = mConfermaPassword.getText().toString();
        return confermaPassword.equals(password) && password.length()>5;
    }
}