package it.units.map00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import it.units.map00.model.Bacaro;

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

        // Ricevere i dati dell'intent ed estrarli con il metodo getExtras()
        Bundle b = getIntent().getExtras();
        String extra = b.getString("msg");
        mAuth = FirebaseAuth.getInstance();
        setTitle(mAuth.getCurrentUser().getDisplayName());


        // Presentare dati all'utente attraverso un Toast
        Toast.makeText(this,"Utente : "+ extra, Toast.LENGTH_SHORT).show();

        db = FirebaseFirestore.getInstance();

        CollectionReference myRef = db.collection("Bacari");

        myRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                Log.d(TAG, doc.getData().toString());
                bacari.add(doc.toObject(Bacaro.class));
            }

        });

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

            Log.i(TAG, "Logout selezionato");
            // TODO: Logout
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

    public void showMap2(View view) {
        Log.d("clicked", "clickato il pulsante mostra mappa");

        Intent expInt = new Intent(this, MapsActivity.class);
        startActivity(expInt);
    }

    public void showList(View view) {




        /*
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference dataRef = db.collection("Bacari").document(uid);
        dataRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Bacaro bacaro = documentSnapshot.toObject(Bacaro.class);
                //bacari.add(bacaro);
                //Log.i("click", bacaro.getName() + " " + bacaro.getLat() + " " + bacaro.getLng());
                //Log.i("click", bacaro.toString());
            }
        });

         */


/*
        db.collection("Bacari")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("click", document.getData().toString());
                                Gson gson = new Gson();
                                Bacaro target = gson.fromJson(document.getData().toString(), Bacaro.class);
                                bacari.add(target);

                            }
                        } else {
                            Log.i("click", "Error getting documents.", task.getException());
                        }
                    }
                });
        */

        Log.i("click","clickato il bottone, ora loggare i bacari");
        //Log.i("click", bacari.toString());

        for(Bacaro i : bacari) {
            Log.i("click", i.getName() + " " + i.getLat() + " " + i.getLng() + " " +i.getDescription() + " " + i.getImageUrl());
        }



    }
}