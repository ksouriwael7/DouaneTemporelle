
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class verificationActivity extends AppCompatActivity {

    public static String genererCle(String matricule, String nom) {
        String prefixe = matricule.substring(matricule.length() - 4);
        return prefixe + "_" + nom;
    }

    public boolean estPremier(int nombre) {
        if (nombre <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        String nom = getIntent().getStringExtra(
                genererCle("1903", "nom"));

        int naissance = getIntent().getIntExtra(
                genererCle("1903", "naissance"), -1);

        int destination = getIntent().getIntExtra(
                genererCle("1903", "destination"), -1);

        String motif = getIntent().getStringExtra(
                genererCle("1903", "motif"));

        String passport = getIntent().getStringExtra(
                genererCle("1903", "passport"));

        if (nom == null || motif == null || passport == null ||
                naissance < 1 || destination < 1) {

            Toast.makeText(this,
                    "Informations invalides !",
                    Toast.LENGTH_LONG).show();

            finish();
            return;
        }

        int ageTemporel = destination - naissance;

        TextView tvNom = findViewById(R.id.tv_nom_wk);
        TextView tvAge = findViewById(R.id.tv_age_wk);
        TextView tvMotif = findViewById(R.id.tv_motif_wk);
        TextView tvPassport = findViewById(R.id.tv_passport_wk);

        tvNom.setText(nom);
        tvAge.setText(ageTemporel + " ans");
        tvMotif.setText(motif);
        tvPassport.setText("Passeport : " + passport);

        Button btnApprouver =
                findViewById(R.id.btn_approuver_wk);

        Button btnRefuser =
                findViewById(R.id.btn_refuser_wk);

        // Vérification du motif et du nombre premier
        String motifNettoye = motif.trim();

        boolean estModificationPasse =
                motifNettoye.equalsIgnoreCase("Modification du passé");

        boolean modificationInterdite =
                estModificationPasse && estPremier(ageTemporel);

        // Affichage du résultat de vérification


        final String raisonRefus = modificationInterdite
                ? "Risque de paradoxe temporel : la modification du passé est interdite."
                : "";

        // Désactivation du bouton si le voyage est interdit
        if (modificationInterdite) {
            btnApprouver.setEnabled(false);
            btnApprouver.setClickable(false);
            btnApprouver.setAlpha(0.4f);
        }

        btnApprouver.setOnClickListener(v -> {
            if (modificationInterdite) {
                return;
            }

            ouvrirResultat(true, nom, "",passport);

        });

        btnRefuser.setOnClickListener(v -> {
            String raison = raisonRefus.isEmpty()
                    ? "Décision de refus de la douane temporelle."
                    : raisonRefus;

            ouvrirResultat(false, nom, raison,passport);
        });
    }

    private void ouvrirResultat(boolean decision, String nom,
                                String raison, String passport) {

        Intent intent = new Intent(
                verificationActivity.this, ResultActivity.class);

        intent.putExtra(genererCle("1903", "decision"), decision);
        intent.putExtra(genererCle("1903", "nom"), nom);
        intent.putExtra(genererCle("1903", "raison"), raison);
        intent.putExtra(genererCle("1903", "passport"), passport);

        startActivity(intent);
    }
}
