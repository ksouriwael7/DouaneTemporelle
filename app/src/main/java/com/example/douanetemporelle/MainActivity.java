
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static String genererCle(String matricule, String nom) {
        String prefixe = matricule.substring(matricule.length() - 4);
        return prefixe + "_" + nom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nom = findViewById(R.id.edit_nv_wk);
        EditText anneeNaissance = findViewById(R.id.edit_an_wk);
        EditText anneeDestination = findViewById(R.id.edit_ad_wk);

        RadioGroup groupeMotif = findViewById(R.id.rg_motif_wk);
        Button bouton = findViewById(R.id.btn_inspecter_wk);

        int anneeActuelle = Calendar.getInstance().get(Calendar.YEAR);

        bouton.setOnClickListener(v -> {

            String nomVoyageur = nom.getText().toString().trim();
            String naissance = anneeNaissance.getText().toString().trim();
            String destination = anneeDestination.getText().toString().trim();
            // 1. nthabet fe Nom
            if (nomVoyageur.isEmpty()) {
                nom.setError("Veuillez saisir votre nom !");
                nom.requestFocus();
                return;
            }
            //nthabet fel anneNaissance
            if (naissance.isEmpty()) {
                anneeNaissance.setError("Veuillez saisir votre année de naissance !");
                anneeNaissance.requestFocus();
                return;
            }

                //nthabet fel annedestination
                if (destination.isEmpty()) {
                anneeDestination.setError("Veuillez saisir l'année de destination !");
                anneeDestination.requestFocus();
                return;
            }

            int anneeNaiss;
            int anneeDest;

            try {
                anneeNaiss = Integer.parseInt(naissance);
                anneeDest = Integer.parseInt(destination);
            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Veuillez saisir des années valides !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (anneeNaiss < 1 || anneeNaiss > anneeActuelle) {
                anneeNaissance.setError("Année de naissance invalide !");
                anneeNaissance.requestFocus();
                return;
            }

            if (anneeDest < anneeNaiss) {
                anneeDestination.setError("Destination temporelle impossible !");
                anneeDestination.requestFocus();
                return;
            }

            int choix = groupeMotif.getCheckedRadioButtonId();

            if (choix == -1) {
                Toast.makeText(this,
                        "Veuillez choisir un motif de voyage !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            RadioButton motifChoisi = findViewById(choix);
            String motif = motifChoisi.getText().toString();

            String passport = "PT-" + UUID.randomUUID()
                    .toString().substring(0, 8).toUpperCase();

            Intent intent = new Intent(MainActivity.this, verificationActivity.class);

            intent.putExtra(genererCle("9007", "nom"), nomVoyageur);
            intent.putExtra(genererCle("9007", "naissance"), anneeNaiss);
            intent.putExtra(genererCle("9007", "destination"), anneeDest);
            intent.putExtra(genererCle("9007", "motif"), motif);
            intent.putExtra(genererCle("9007", "passport"), passport);
            startActivity(intent);
        });
    }
}
