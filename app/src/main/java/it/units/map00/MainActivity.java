package it.units.map00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import it.units.map00.entities.Bacaro;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<Bacaro> bacari = new ArrayList<>();



    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser().getDisplayName() == null) {
            setTitle("Benvenuto!");
        } else {
            setTitle("Ciao " + mAuth.getCurrentUser().getDisplayName() + "!");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.layout_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logoutItem){

            // Logout
            mAuth.signOut();
            updateUI();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent intToLogin = new Intent(this, LoginActivity.class);
            finish();

            startActivity(intToLogin);
        }

    }

    public void showMap(View view) {

        Intent expInt = new Intent(this, MapsActivity.class);
        startActivity(expInt);
    }

}