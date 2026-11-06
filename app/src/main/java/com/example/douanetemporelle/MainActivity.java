package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1- Génération des clés avec les 4 derniers chiffres du matricule
    public static String genererCle(String matricule, String nom) {
        String prefixe = matricule.substring(matricule.length() - 4);
        return prefixe + "_" + nom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2- Récupération des éléments
        EditText nom = findViewById(R.id.edit_nv_wk);
        EditText anneeNaissance = findViewById(R.id.edit_an_wk);
        EditText anneeDestination = findViewById(R.id.edit_ad_wk);

        RadioGroup groupeMotif = findViewById(R.id.rg_motif_wk);
        Button bouton = findViewById(R.id.btn_inspecter_wk);

        // 3- Action du bouton
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomVoyageur = nom.getText().toString().trim();
                String naissance = anneeNaissance.getText().toString().trim();
                String destination = anneeDestination.getText().toString().trim();

                int choix = groupeMotif.getCheckedRadioButtonId();

                if (nomVoyageur.isEmpty() || naissance.isEmpty()
                        || destination.isEmpty() || choix == -1) {
                    Toast.makeText(MainActivity.this,
                            "Veuillez remplir tous les champs",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton motifChoisi = findViewById(choix);
                String motif = motifChoisi.getText().toString();

                // 4- Création de l'Intent explicite
                Intent intent = new Intent(MainActivity.this,
                        verificationActivity.class);

                intent.putExtra(genererCle("9007", "nom"), nomVoyageur);
                intent.putExtra(genererCle("9007", "naissance"), Integer.parseInt(naissance));
                intent.putExtra(genererCle("9007", "destination"), Integer.parseInt(destination));
                intent.putExtra(genererCle("9007", "motif"), motif);

                startActivity(intent);
            }
        });
    }
}