package com.br.guaira.ntdois;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    Registro objRegistro,regAuxiliar,ultimoRegistro,primeiroRegistro,proximoRegistro,registroAnteior;
    EditText edtNome,edtEndereco,edtTelefone;
    int contadorRegistros = 0, posicao =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        primeiroRegistro = null;
        ultimoRegistro = null;
        chamaMenuPrincipal();
    }
    public void chamaCadastro() {
        setContentView(R.layout.cadastro);
        Button btMenuPrincipal = (Button) findViewById(R.id.btMenuPrincipal);
        Button btGravar = (Button) findViewById(R.id.btGravar);
        btMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaMenuPrincipal();
            }
        });
        btGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                objRegistro = new Registro();
                edtNome = (EditText) findViewById(R.id.edtNome);
                edtEndereco = (EditText) findViewById(R.id.edtEndereco);
                edtTelefone = (EditText) findViewById(R.id.edtTelefone);
                //Gravar
                objRegistro.nome = edtNome.getText().toString();
                objRegistro.endereco = edtEndereco.getText().toString();
                objRegistro.telefone = edtTelefone.getText().toString();

                if (primeiroRegistro ==null)
                    primeiroRegistro = objRegistro;
                    contadorRegistros++;

                objRegistro.registroAnterior = ultimoRegistro;

                if (ultimoRegistro == null)
                    ultimoRegistro = objRegistro;
                else{
                        ultimoRegistro.proximoRegistro = objRegistro;
                        ultimoRegistro = objRegistro;}
                    contadorRegistros++;
                    {

                    }
                mensagemExibir("Aviso","Dados Gravados com Sucesso!");
                chamaMenuPrincipal();
            }
                catch (Exception erro)
                {
                    mensagemExibir("Erro","Deu Erro ao gravar os Dados");
                }
            }
        });
    }
    public void mensagemExibir(String titulo, String texto){
        AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
        mensagem.setTitle("Aviso");
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("ok",null);
        mensagem.show();
    }
    public void chamaConsulta() {

        if (contadorRegistros ==0){

            mensagemExibir("Aviso","Não possui registros gravados!");
            chamaMenuPrincipal();
            return;
        }
        posicao=1;
        setContentView(R.layout.consulta);
        Button btVoltar= (Button) findViewById(R.id.btVoltar);
        Button btRegAnt= (Button) findViewById(R.id.btRegAnt);
        Button btProxReg= (Button) findViewById(R.id.btProxReg);
        TextView tvEndereco = (TextView) findViewById(R.id.tvEndereco);
        TextView tvTelefone = (TextView) findViewById(R.id.tvTelefone);
        TextView tvNome = (TextView) findViewById(R.id.tvNome);
        regAuxiliar = primeiroRegistro;
        tvNome.setText(regAuxiliar.nome);
        tvEndereco.setText(regAuxiliar.endereco);
        tvTelefone.setText(regAuxiliar.telefone);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaMenuPrincipal();
            }
        });
        btProxReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicao == contadorRegistros)
                    return;
                posicao++;
                regAuxiliar = regAuxiliar.proximoRegistro;
                TextView tvEndereco = (TextView) findViewById(R.id.tvEndereco);
                TextView tvTelefone = (TextView) findViewById(R.id.tvTelefone);
                TextView tvNome = (TextView) findViewById(R.id.tvNome);

                tvNome.setText(regAuxiliar.nome);
                tvEndereco.setText(regAuxiliar.endereco);
                tvTelefone.setText(regAuxiliar.telefone);
            }
        });
        btRegAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicao ==1)
                    return;
                posicao--;
                regAuxiliar = regAuxiliar.registroAnterior;

                TextView tvEndereco = (TextView) findViewById(R.id.tvEndereco);
                TextView tvTelefone = (TextView) findViewById(R.id.tvTelefone);
                TextView tvNome = (TextView) findViewById(R.id.tvNome);

                tvNome.setText(regAuxiliar.nome);
                tvEndereco.setText(regAuxiliar.endereco);
                tvTelefone.setText(regAuxiliar.telefone);

            }
        });
    }
    public void chamaMenuPrincipal() {
        setContentView(R.layout.activity_main);
        Button btCadastro = (Button) findViewById(R.id.btTelaCadastro);
        Button btConsulta = (Button) findViewById(R.id.btTelaConsulta);
        btConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaConsulta();
            }
        });
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaCadastro();
            }
        });
    }
}