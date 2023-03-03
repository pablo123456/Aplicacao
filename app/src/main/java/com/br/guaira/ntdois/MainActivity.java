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
    Button btCadastro,btConsulta,btVoltar,btRegAnt,btProxReg,btMenuPrincipal,btGravar;
    TextView tvNome,tvEndereco,tvTelefone;
    int contadorRegistros = 0, posicao =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        primeiroRegistro = null;
        ultimoRegistro = null;
        chamaMenuPrincipal();
        inicializacaoObjetos();
    }
    public void chamaCadastro() {
        setContentView(R.layout.cadastro);
        inicializacaoObjetos();
        listeners();

        btGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                objRegistro = new Registro();
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
        inicializacaoObjetos();
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
              //Nao sei se é pra carregar a inicializacao de objetos aqui, testando ...
                inicializacaoObjetos();

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
                //Nao sei se é pra carregar a inicializacao de objetos aqui, testando ...
                inicializacaoObjetos();

                tvNome.setText(regAuxiliar.nome);
                tvEndereco.setText(regAuxiliar.endereco);
                tvTelefone.setText(regAuxiliar.telefone);

            }
        });
    }
    public void chamaMenuPrincipal() {
        setContentView(R.layout.activity_main);
        inicializacaoObjetos();
        listeners();
    }
    public void inicializacaoObjetos(){
        btCadastro = (Button) findViewById(R.id.btTelaCadastro);
        btConsulta = (Button) findViewById(R.id.btTelaConsulta);
        //Objetos da Tela Consulta
        btVoltar = (Button) findViewById(R.id.btVoltar);
        btRegAnt = (Button) findViewById(R.id.btRegAnt);
        btProxReg = (Button) findViewById(R.id.btProxReg);
        //Objetos da Tela Cadastro
        btMenuPrincipal = (Button) findViewById(R.id.btMenuPrincipal);
        btGravar = (Button) findViewById(R.id.btGravar);
        tvNome = (TextView) findViewById(R.id.tvNome);
        tvEndereco = (TextView) findViewById(R.id.tvEndereco);
        tvTelefone = (TextView) findViewById(R.id.tvTelefone);
    }
    public void listeners(){
        try {
            btMenuPrincipal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chamaMenuPrincipal();
                }
            });
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
        catch (Exception erro){}
    }

}