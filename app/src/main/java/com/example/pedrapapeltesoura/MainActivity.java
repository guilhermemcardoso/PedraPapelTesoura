package com.example.pedrapapeltesoura;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pedrapapeltesoura.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String JOGADORES = "JOGADORES";
    public static final String RODADAS = "RODADAS";

    private static final String JOGADOR = "JOGADOR";
    private static final String COMPUTADOR1 = "COMPUTADOR1";
    private static final String COMPUTADOR2 = "COMPUTADOR2";

    private static final String PEDRA = "PEDRA";
    private static final String PAPEL = "PAPEL";
    private static final String TESOURA = "TESOURA";

    public int QTDE_JOGADORES = 2;
    public int QTDE_RODADAS = 1;

    int vitoriasJogador = 0;
    int vitoriasComputador1 = 0;
    int vitoriasComputador2 = 0;

    int rodadaAtual = 1;

    ActivityMainBinding activityMainBinding;
    ActivityResultLauncher<Intent> settingsActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Partida");

        settingsActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int jogadores = data.getIntExtra(JOGADORES, 2);
                        int rodadas = data.getIntExtra(RODADAS, 1);
                        QTDE_JOGADORES  = jogadores;
                        QTDE_RODADAS = rodadas;

                        reiniciarPartida();
                        Log.d("TELA01 - Jogadores", String.valueOf(QTDE_JOGADORES));
                        Log.d("TELA01 - Rodadas", String.valueOf(QTDE_RODADAS));
                    }
                });

        activityMainBinding.pedraIb.setOnClickListener(this);
        activityMainBinding.papelIb.setOnClickListener(this);
        activityMainBinding.tesouraIb.setOnClickListener(this);
    }

    public void openSettingsActivityForResult() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(JOGADORES, QTDE_JOGADORES);
        intent.putExtra(RODADAS, QTDE_RODADAS);
        settingsActivityResultLauncher.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        openSettingsActivityForResult();
        return true;
    }

    @Override
    public void onClick(View v) {

        ArrayList<String> resultado = novaRodada(v);
        StringBuilder sb = new StringBuilder();

        for(int counter = 0; counter < resultado.size(); counter++) {
            if(resultado.get(counter).equals(JOGADOR)) {
                vitoriasJogador++;
            }

            if(resultado.get(counter).equals(COMPUTADOR1)) {
                vitoriasComputador1++;
            }

            if(resultado.get(counter).equals(COMPUTADOR2)) {
                vitoriasComputador2++;
            }
        }

        Log.d("TELA01 - Rodada Atual", String.valueOf(rodadaAtual));
        if(rodadaAtual < QTDE_RODADAS) {

            for(int  counter = 0; counter < resultado.size(); counter++) {
                sb.append(resultado.get(counter));
                if(counter < (resultado.size() - 1)) {
                    sb.append(", ");
                }
            }

            if(resultado.size() > 1) {
                sb.append(" ganharam esta rodada! Selecione sua próxima jogada para a rodada seguinte.");
            } else {
                sb.append(" ganhou esta rodada! Selecione sua próxima jogada para a rodada seguinte.");
            }

            rodadaAtual++;
        } else {
            ArrayList<String> campeoes = new ArrayList<>();
            if(vitoriasJogador >= vitoriasComputador1 && vitoriasJogador >= vitoriasComputador2) {
                campeoes.add(JOGADOR);
            }

            if(vitoriasComputador1 >= vitoriasJogador && vitoriasComputador1 >= vitoriasComputador2) {
                campeoes.add(COMPUTADOR1);
            }

            if(vitoriasComputador2 >= vitoriasComputador1 && vitoriasComputador2 >= vitoriasJogador) {
                campeoes.add(COMPUTADOR2);
            }

            for(int  counter = 0; counter < campeoes.size(); counter++) {
                Log.d("AQUI", campeoes.get(counter));
                sb.append(campeoes.get(counter));
                if(counter < (resultado.size() - 1)) {
                    sb.append(", ");
                }
            }

            if(campeoes.size() > 1) {
                sb.append(" ganharam esta partida! Selecione sua próxima jogada para iniciar uma nova partida.");
            } else {
                sb.append(" ganhou esta partida! Selecione sua próxima jogada para iniciar uma nova partida.");
            }


            reiniciarPartida();

        }
        activityMainBinding.resultadoTv.setText(sb.toString());
        activityMainBinding.resultadoLl.setVisibility(View.VISIBLE);

    }

    private void reiniciarPartida() {
        rodadaAtual = 1;
        vitoriasJogador = 0;
        vitoriasComputador1 = 0;
        vitoriasComputador2 = 0;
        activityMainBinding.resultadoLl.setVisibility(View.GONE);
    }

    private ArrayList<String> novaRodada(View v) {
        String jogador;
        String computador1;
        String computador2 = "";

        switch (v.getId()) {
            case R.id.papelIb:
                jogador = PAPEL;
                break;
            case R.id.tesouraIb:
                jogador = TESOURA;
                break;
            default:
                jogador = PEDRA;
                break;
        }

        computador1 = gerarJogada();
        if(QTDE_JOGADORES == 3) {
            computador2 = gerarJogada();
        }

        ArrayList<String> resultado;
        resultado = gerarResultado(jogador, computador1, computador2, QTDE_JOGADORES);

        switch (jogador) {
            case PEDRA:
                activityMainBinding.jogadaIv.setImageResource(R.mipmap.pedra);
                break;
            case PAPEL:
                activityMainBinding.jogadaIv.setImageResource(R.mipmap.papel);
                break;
            case TESOURA:
                activityMainBinding.jogadaIv.setImageResource(R.mipmap.tesoura);
                break;
            default:
                break;
        }

        switch (computador1) {
            case PEDRA:
                activityMainBinding.jogadaComputador1Iv.setImageResource(R.mipmap.pedra);
                break;
            case PAPEL:
                activityMainBinding.jogadaComputador1Iv.setImageResource(R.mipmap.papel);
                break;
            case TESOURA:
                activityMainBinding.jogadaComputador1Iv.setImageResource(R.mipmap.tesoura);
                break;
            default:
                break;
        }

        if(QTDE_JOGADORES == 3) {
            activityMainBinding.jogadaComputador2Iv.setVisibility(View.VISIBLE);
            activityMainBinding.jogadaComputador2Tv.setVisibility(View.VISIBLE);

            switch (computador2) {
                case PEDRA:
                    activityMainBinding.jogadaComputador2Iv.setImageResource(R.mipmap.pedra);
                    break;
                case PAPEL:
                    activityMainBinding.jogadaComputador2Iv.setImageResource(R.mipmap.papel);
                    break;
                case TESOURA:
                    activityMainBinding.jogadaComputador2Iv.setImageResource(R.mipmap.tesoura);
                    break;
                default:
                    break;
            }
        } else {
            activityMainBinding.jogadaComputador2Iv.setVisibility(View.GONE);
            activityMainBinding.jogadaComputador2Tv.setVisibility(View.GONE);
        }

        return resultado;
    }

    private ArrayList<String> gerarResultado(String jogador, String computador1, String computador2, int quantidadeJogadores) {

        ArrayList<String> vencedores = new ArrayList<String>();

        if(!jogador.equals(computador1) && !jogador.equals(computador2) && !computador1.equals(computador2) && quantidadeJogadores == 3) {
            vencedores.add(JOGADOR);
            vencedores.add(COMPUTADOR1);
            vencedores.add(COMPUTADOR2);
            return vencedores;
        }

        if(jogador.equals(PEDRA)) {
            if(computador1.equals(PAPEL) || computador1.equals(PEDRA)) {
                vencedores.add(COMPUTADOR1);
            }

            if(quantidadeJogadores == 3) {
                if(computador2.equals(PAPEL) || computador2.equals(PEDRA)) {
                    vencedores.add(COMPUTADOR2);
                }
            }

            if(!computador1.equals(PAPEL)) {
                if((quantidadeJogadores == 3 && !computador2.equals(PAPEL)) || quantidadeJogadores == 2) {
                    vencedores.add(JOGADOR);
                }
            }

            return vencedores;
        }

        if(jogador.equals(PAPEL)) {
            if(computador1.equals(PAPEL) || computador1.equals(TESOURA)) {
                vencedores.add(COMPUTADOR1);
            }

            if(quantidadeJogadores == 3) {
                if (computador2.equals(PAPEL) || computador2.equals(TESOURA)) {
                    vencedores.add(COMPUTADOR2);
                }
            }

            if(!computador1.equals(TESOURA)) {
                if((quantidadeJogadores == 3 && !computador2.equals(TESOURA)) || quantidadeJogadores == 2) {
                    vencedores.add(JOGADOR);
                }
            }

            return vencedores;
        }

        if(jogador.equals(TESOURA)) {
            if(computador1.equals(TESOURA) || computador1.equals(PEDRA)) {
                vencedores.add(COMPUTADOR1);
            }

            if(quantidadeJogadores == 3) {
                if (computador2.equals(TESOURA) || computador2.equals(PEDRA)) {
                    vencedores.add(COMPUTADOR2);
                }
            }

            if(!computador1.equals(PEDRA)) {
                if((quantidadeJogadores == 3 && !computador2.equals(PEDRA)) || quantidadeJogadores == 2) {
                    vencedores.add(JOGADOR);
                }
            }

            return vencedores;
        }

        return vencedores;

    }

    private String gerarJogada() {
        Random random = new Random();
        int valor = random.nextInt(3);
        String jogada;

        switch (valor) {
            case 1:
                jogada = PAPEL;
                break;
            case 2:
                jogada = TESOURA;
                break;
            default:
                jogada = PEDRA;
                break;
        }

        return jogada;
    }
}