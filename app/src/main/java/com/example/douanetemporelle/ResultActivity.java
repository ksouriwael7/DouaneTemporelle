
package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//takra resultat mel verificationActivity w nbadlou loun taa background w zid el boutton taati voyage jdid
public class ResultActivity extends AppCompatActivity {

    public static String genererCle(String matricule, String nom) {
        String prefixe = matricule.substring(matricule.length() - 4);
        return prefixe + "_" + nom;
    }
    //narbtou el java bel XML activity_result.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //naamlou recuperartion lel les données li jeyiina menl verificationActivity
        boolean decision = getIntent().getBooleanExtra(
                genererCle("1903", "decision"), false);

        String nom = getIntent().getStringExtra(
                genererCle("1903", "nom"));

        String raison = getIntent().getStringExtra(
                genererCle("1903", "raison"));
        String passport = getIntent().getStringExtra(
                genererCle("1903", "passport"));
        //nthabtou fel esm ken wsel w ken ma wselech nsakrou el page w nwakfou
        if (nom == null || nom.trim().isEmpty()) {
            Toast.makeText(this,
                    "Informations du voyageur introuvables !",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        LinearLayout root = findViewById(R.id.root_result_wk);
        TextView message = findViewById(R.id.tv_resultat_wk);
        message.setTextColor(Color.WHITE);
        TextView stamp = findViewById(R.id.tv_stamp_wk);
        TextView tvPassport = findViewById(R.id.tv_passport_result_wk);
        TextView tvRaison = findViewById(R.id.tv_raison_wk);
        Button nouveau = findViewById(R.id.btn_nouveau_wk);
        //n'affichiw el num taa el passport
        tvPassport.setText("Passeport : " + passport);
        if (decision) {
            //ken voyage makboul
            root.setBackgroundColor(Color.rgb(46, 125, 50));
            message.setText("Bon voyage " + nom + " !");
            stamp.setText("ACCEPTÉ");
            stamp.setTextColor(Color.WHITE);
            tvRaison.setText("Votre voyage temporel est autorisé.");
        } else {
            //w ken ma tekbelch
            root.setBackgroundColor(Color.rgb(183, 28, 28));
            message.setText(nom + " est en état d'arrestation temporelle !");
            stamp.setText("REFUSÉ");
            stamp.setTextColor(Color.WHITE);

            if (raison == null || raison.trim().isEmpty()) {
                raison = "Décision de refus de la douane temporelle.";
            }

            tvRaison.setText("Motif du refus : " + raison);
        }

        nouveau.setOnClickListener(v -> {
            //nhadhrou iten bech yarjaa lel main bch ynajem yaawed
            Intent intent = new Intent(
                    ResultActivity.this, MainActivity.class);
            //nfaskhou les pages li amalnehom kdom bech ynajem yaawed
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
        });
    }
}
