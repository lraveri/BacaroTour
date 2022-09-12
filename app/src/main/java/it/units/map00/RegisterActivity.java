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

    // Costanti
    static final String CHAT_PREFS = "ChatPrefs";
    static final String NOME_KEY = "username";

    EditText mConfermaPassword;
    EditText mEmail;
    EditText mPassword;
    EditText mNome;


    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this,"Utente già loggato", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();



        mAuth = FirebaseAuth.getInstance();


    }

    private void initUI() {
        mEmail = (EditText)findViewById(R.id.etRegEmail);
        mPassword =(EditText)findViewById(R.id.etRegPass);
        mConfermaPassword = (EditText)findViewById(R.id.etRegPassConf);
        mNome = (EditText)findViewById(R.id.etRegName);
    }

    private void createFirebaseUser(String email, String password, final String nome){



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("ChatUPRegistration", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //showDialog("Registrazione effettuata con successo", "Successo", android.R.drawable.ic_dialog_info);

                            // TODO: Caricare nome in Firebase
                            setNome(nome);

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            finish();
                            startActivity(intent);

                            //Toast.makeText(RegisterActivity.this, "Authentication success.",
                            //Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("ChatUPRegistration", "createUserWithEmail:failure", task.getException());

                            // Chiamare l'alert dialog
                            showDialog("Errore nella Registrazione","Errore", android.R.drawable.ic_dialog_alert);

                            //Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
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




    // Creare un alert dialog da mostrare in caso di registration failed
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

        // Validazioni Dati
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
        return confermaPassword.equals(password) && password.length()>2;
    }
}