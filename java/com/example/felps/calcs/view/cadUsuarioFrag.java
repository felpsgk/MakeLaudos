package com.example.felps.calcs.view;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.felps.calcs.R;
import com.example.felps.calcs.controller.UsuarioDAO;
import com.example.felps.calcs.model.Usuario;


public class cadUsuarioFrag extends Fragment {

    private EditText txtNomeUtilizador;
    private EditText txtDocUtilizador;
    private EditText txtContato;
    private Button btnCadastrar;

    public cadUsuarioFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cad_usuario, container, false);

        txtNomeUtilizador = v.findViewById(R.id.txtNomeUtilizador);
        txtDocUtilizador = v.findViewById(R.id.txtDocUtilizador);
        txtContato = v.findViewById(R.id.txtContatoUtilizador);
        btnCadastrar = v.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oqsalvar;
                try {
                    Usuario u = new Usuario();
                    u.setNome(txtNomeUtilizador.getText().toString());
                    u.setDocumento(txtDocUtilizador.getText().toString());
                    if(intOrString(txtContato.getText().toString())){
                        u.setContato(txtContato.getText().toString());
                        oqsalvar = "telefone";
                    } else {
                        u.setContato(txtContato.getText().toString());
                        oqsalvar = "email";
                    }
                    UsuarioDAO udao = new UsuarioDAO(v.getContext());

                    if (udao.newUsuario(u, oqsalvar) == true) {
                        Usuario un = new Usuario();
                        UsuarioDAO undao = new UsuarioDAO(v.getContext());
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        un = undao.searchUsuario(u.getNome());
                        Log.e("Usuario", "" + un);
                        dlg.setTitle("Sucesso");
                        dlg.setMessage("Usuario '" + un.getId() + " '" + un.getNome() + "' criado com sucesso");
                        dlg.setNeutralButton("OK", null);
                        dlg.show();
                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        dlg.setTitle("Erro");
                        dlg.setMessage("Erro");
                        dlg.setNeutralButton("OK", null);
                        dlg.show();
                    }

                } catch (SQLiteException sql) {
                    Log.e("erro", "erro " + sql);
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
