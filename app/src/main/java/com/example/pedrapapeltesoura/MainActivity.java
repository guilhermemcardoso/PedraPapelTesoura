package com.example.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.pedrapapeltesoura.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.pedraIb.setOnClickListener(this);
        activityMainBinding.papelIb.setOnClickListener(this);
        activityMainBinding.tesouraIb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String jogador;
        String computador1;
        String computador2 = "";

        switch (v.getId()) {
            case R.id.papelIb:
                jogador = "papel";
                break;
            case R.id.tesouraIb:
                jogador = "tesoura";
                break;
            default:
                jogador = "pedra";
                break;
        }

        int quantidadeJogadores = activityMainBinding.quantidadeJogadoresRg.getCheckedRadioButtonId() == R.id.doisJogadoresRb ? 2 : 3;
        computador1 = gerarJogada();
        if(quantidadeJogadores == 3) {
            computador2 = gerarJogada();
        }

        String resultado;
        resultado = gerarResultado(jogador, computador1, computador2, quantidadeJogadores);

        StringBuilder sb = new StringBuilder();
        sb.append("Você ");
        sb.append(resultado.toUpperCase());
        sb.append("!");

        activityMainBinding.resultadoTv.setText(sb.toString());
        activityMainBinding.resultadoLl.setVisibility(View.VISIBLE);

        switch (jogador) {
            case "pedra":
                activityMainBinding.jogadaIv.setImageResource(R.mipmap.pedra);
                break;
            case "papel":
                activityMainBinding.jogadaIv.setImageResource(R.mipmap.papel);
                break;
            case "tesoura":
                activityMainBinding.jogadaIv.setImageResource(R.mipmap.tesoura);
                break;
            default:
                break;
        }

        switch (computador1) {
            case "pedra":
                activityMainBinding.jogadaComputador1Iv.setImageResource(R.mipmap.pedra);
                break;
            case "papel":
                activityMainBinding.jogadaComputador1Iv.setImageResource(R.mipmap.papel);
                break;
            case "tesoura":
                activityMainBinding.jogadaComputador1Iv.setImageResource(R.mipmap.tesoura);
                break;
            default:
                break;
        }

        if(quantidadeJogadores == 3) {
            activityMainBinding.jogadaComputador2Iv.setVisibility(View.VISIBLE);
            activityMainBinding.jogadaComputador2Tv.setVisibility(View.VISIBLE);

            switch (computador2) {
                case "pedra":
                    activityMainBinding.jogadaComputador2Iv.setImageResource(R.mipmap.pedra);
                    break;
                case "papel":
                    activityMainBinding.jogadaComputador2Iv.setImageResource(R.mipmap.papel);
                    break;
                case "tesoura":
                    activityMainBinding.jogadaComputador2Iv.setImageResource(R.mipmap.tesoura);
                    break;
                default:
                    break;
            }
        } else {
            activityMainBinding.jogadaComputador2Iv.setVisibility(View.GONE);
            activityMainBinding.jogadaComputador2Tv.setVisibility(View.GONE);
        }
    }

    private String gerarResultado(String jogador, String computador1, String computador2, int quantidadeJogadores) {

        if(!jogador.equals(computador1) &&
                !jogador.equals(computador2) &&
                !computador1.equals(computador2) &&
                computador2.length() > 0) {
            return "empatou";
        }

        if(jogador.equals("pedra")) {
            if(computador1.equals("papel") || computador2.equals("papel")) {
                return "perdeu";
            }

            if(computador1.equals("pedra") || computador2.equals("pedra")) {
                return "empatou";
            }

            return "ganhou";
        }

        if(jogador.equals("papel")) {
            if(computador1.equals("tesoura") || computador2.equals("tesoura")) {
                return "perdeu";
            }

            if(computador1.equals("papel") || computador2.equals("papel")) {
                return "empatou";
            }

            return "ganhou";
        }

        if(jogador.equals("tesoura")) {
            if(computador1.equals("pedra") || computador2.equals("pedra")) {
                return "perdeu";
            }

            if(computador1.equals("tesoura") || computador2.equals("tesoura")) {
                return "empatou";
            }

            return "ganhou";
        }

        return "ganhou";

    }

    private String gerarJogada() {
        Random random = new Random();
        int valor = random.nextInt(3);
        String jogada;

        switch (valor) {
            case 1:
                jogada = "papel";
                break;
            case 2:
                jogada = "tesoura";
                break;
            default:
                jogada = "pedra";
                break;
        }

        return jogada;
    }
}