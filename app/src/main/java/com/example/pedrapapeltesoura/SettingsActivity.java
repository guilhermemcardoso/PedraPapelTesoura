package com.example.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pedrapapeltesoura.databinding.ActivitySettingsBinding;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySettingsBinding activitySettingsBinding;

    int QTDE_JOGADORES;
    int QTDE_RODADAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());

        int jogadores = getIntent().getIntExtra(MainActivity.JOGADORES, 2);
        int rodadas = getIntent().getIntExtra(MainActivity.RODADAS, 1);
        QTDE_JOGADORES  = jogadores;
        QTDE_RODADAS = rodadas;

        setInitialValues(QTDE_JOGADORES, QTDE_RODADAS);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Configurações");

        activitySettingsBinding.salvarBt.setOnClickListener(this);
        activitySettingsBinding.cancelarBt.setOnClickListener(this);
    }

    private void setInitialValues(int jogadores, int rodadas) {
        if(jogadores == 2) {
            activitySettingsBinding.doisJogadoresRb.setChecked(true);
            activitySettingsBinding.tresJogadoresRb.setChecked(false);
        } else {
            activitySettingsBinding.doisJogadoresRb.setChecked(false);
            activitySettingsBinding.tresJogadoresRb.setChecked(true);
        }

        switch (rodadas) {
            case 1:
                activitySettingsBinding.umaRodadaRb.setChecked(true);
                activitySettingsBinding.tresRodadasRb.setChecked(false);
                activitySettingsBinding.cincoRodadasRb.setChecked(false);
                break;
            case 3:
                activitySettingsBinding.umaRodadaRb.setChecked(false);
                activitySettingsBinding.tresRodadasRb.setChecked(true);
                activitySettingsBinding.cincoRodadasRb.setChecked(false);
                break;
            default:
                activitySettingsBinding.umaRodadaRb.setChecked(false);
                activitySettingsBinding.tresRodadasRb.setChecked(false);
                activitySettingsBinding.cincoRodadasRb.setChecked(true);
                break;
        }

        Log.d("TELA02 - Jogadores", String.valueOf(QTDE_JOGADORES));
        Log.d("TELA02 - Rodadas", String.valueOf(QTDE_RODADAS));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() != R.id.cancelarBt) {
            Intent retornoIntent = new Intent();

            int quantidadeJogadores = activitySettingsBinding.quantidadeJogadoresRg.getCheckedRadioButtonId() == R.id.doisJogadoresRb ? 2 : 3;
            retornoIntent.putExtra(MainActivity.JOGADORES, quantidadeJogadores);

            int quantidadeRodadas = activitySettingsBinding.quantidadeRodadasRg.getCheckedRadioButtonId() == R.id.umaRodadaRb ? 1 : activitySettingsBinding.quantidadeRodadasRg.getCheckedRadioButtonId() == R.id.tresRodadasRb ? 3 : 5;
            retornoIntent.putExtra(MainActivity.RODADAS, quantidadeRodadas);

            setResult(RESULT_OK, retornoIntent);
        }
        finish();


    }
}