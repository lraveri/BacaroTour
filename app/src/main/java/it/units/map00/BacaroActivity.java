package it.units.map00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import it.units.map00.entities.Bacaro;

public class BacaroActivity extends AppCompatActivity {

    private static final String TAG = "BacaroActivity";

    String name;
    double lat;
    double lng;

    FirebaseStorage storage;

    FirebaseFirestore db;

    ImageView rImage;
    TextView tvTitle;
    TextView tvDescription;
    RatingBar tvFood;

    Bacaro bacaro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacaro);

        name = getIntent().getStringExtra("name");

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Bacari").document(name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bacaro = document.toObject(Bacaro.class);
                        showContent(bacaro);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public void showContent(Bacaro bacaro) {
        rImage = findViewById(R.id.rImage);
        Picasso.get().load(bacaro.getImageUrl()).into(rImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(bacaro.getName());
        tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setText(bacaro.getDescription());
        tvFood = findViewById(R.id.tvFood);
        tvFood.setRating((float) bacaro.getAverageRate());

    }
}