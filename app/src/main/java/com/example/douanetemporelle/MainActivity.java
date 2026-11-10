
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
    // Fonction nestaamlouha kifech nabaathou les données fel Intent
    public static String genererCle(String matricule, String nom) {
        String prefixe = matricule.substring(matricule.length() - 4);
        return prefixe + "_" + nom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nrbatou el java bel Interface eli fel XML
        setContentView(R.layout.activity_main);
        //Nrbatou el Composants el kol b el variables fel java
        EditText nom = findViewById(R.id.edit_nv_wk);
        EditText anneeNaissance = findViewById(R.id.edit_an_wk);
        EditText anneeDestination = findViewById(R.id.edit_ad_wk);

        RadioGroup groupeMotif = findViewById(R.id.rg_motif_wk);
        Button bouton = findViewById(R.id.btn_inspecter_wk);
        // Njibou el 3am sne bech nestaamlouh fel verif
        int anneeActuelle = Calendar.getInstance().get(Calendar.YEAR);
        // ki taamel clic aal boutons yemchi lel yaamel verif
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
            //nbadlou el annenaiss w annedest el entier w naamlou execption bech ngeriw el erreur w n'evite el crash
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

            int choix = groupeMotif.getCheckedRadioButtonId();
            // nthabtou idha nzel aala boutton radio wala lee
            if (choix == -1) {
                Toast.makeText(this,
                        "Veuillez choisir un motif de voyage !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            RadioButton motifChoisi = findViewById(choix);
            String motif = motifChoisi.getText().toString();
            //hawelet naamel passport bech kol wehed yebda aandou identifiant fel voyage bech n'eviti ken zouz aandohom nafes lesem
            String passport = "PT-" + UUID.randomUUID()
                    .toString().substring(0, 8).toUpperCase();
            // nhadhrou itent bech nabaathou les donnes el verificatinActivty.java
            Intent intent = new Intent(MainActivity.this, verificationActivity.class);
            // nabaathou les données li jebnehom w nestaamlou el fonction genererCle()
            intent.putExtra(genererCle("1903", "nom"), nomVoyageur);
            intent.putExtra(genererCle("1903", "naissance"), anneeNaiss);
            intent.putExtra(genererCle("1903", "destination"), anneeDest);
            intent.putExtra(genererCle("1903", "motif"), motif);
            intent.putExtra(genererCle("1903", "passport"), passport);

            startActivity(intent);
        });
    }
}
