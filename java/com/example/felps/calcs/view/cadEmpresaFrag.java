package com.example.felps.calcs.view;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.felps.calcs.R;
import com.example.felps.calcs.controller.EmpresaDAO;
import com.example.felps.calcs.model.Empresa;


public class cadEmpresaFrag extends Fragment {
    private EditText txtNomeEmpresa;
    private EditText txtEnderecoEmpresa;
    private EditText txtContato;
    private EditText txtTipo;
    private Button btnCadastrar;


    public cadEmpresaFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cad_empresa, container, false);
        txtNomeEmpresa = v.findViewById(R.id.txtNomeEmpresa);
        txtEnderecoEmpresa = v.findViewById(R.id.txtEnderecoEmpresa);
        txtContato = v.findViewById(R.id.txtContatoEmpresa);
        txtTipo = v.findViewById(R.id.txtTipoEmpresa);
        btnCadastrar = v.findViewById(R.id.btnCadastrar);
        //METODO ABAIXO CRIA EMPRESA E SALVA EMPRESA NO BANCO
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oqsalvar;
                try {
                    Empresa e = new Empresa();
                    e.setNome(txtNomeEmpresa.getText().toString());
                    e.setEndereco(txtEnderecoEmpresa.getText().toString());
                    e.setTipo(Integer.parseInt(txtTipo.getText().toString()));
                    if(intOrString(txtContato.getText().toString())){
                        oqsalvar = "telefone";
                        e.setContato(txtContato.getText().toString());
                    } else {
                        oqsalvar = "email";
                        e.setContato(txtContato.getText().toString());
                    }
                    EmpresaDAO edao = new EmpresaDAO(v.getContext());
                    if(edao.newEmpresa(e, oqsalvar) == true){
                        Empresa en = new Empresa();
                        EmpresaDAO endao = new EmpresaDAO(v.getContext());
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        en = endao.searchEmpresa(e.getNome());
                        Log.e("empresa",""+en);
                        dlg.setTitle("Sucesso");
                        dlg.setMessage("Empresa '"+ en.getId()+" '" + en.getNome() + "' criada com sucesso");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        dlg.setTitle("Erro");
                        dlg.setMessage("Erro");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }

                } catch (SQLiteException sql) {
                    Log.e("erro","erro "+sql);
                }
            }
        });
        return v;
    }
    public boolean intOrString (String contato){
        try{
            Integer.parseInt(contato);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
