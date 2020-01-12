package com.example.felps.calcs.view;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.felps.calcs.R;
import com.example.felps.calcs.controller.AparelhoDAO;
import com.example.felps.calcs.model.Aparelho;


public class cadAparelhoFrag extends Fragment {

    private TextInputLayout txtNomeAparelho;
    private TextInputLayout txtNSerie;
    private TextInputLayout txtfabricanteAparelho;
    private TextInputLayout txtcategoriaAparelho;
    private TextInputLayout txtmodeloAparelho;
    private Button btnCadastrar;

    public cadAparelhoFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cad_aparelho, container, false);

        txtNomeAparelho = v.findViewById(R.id.txtNomeAparelho);
        txtNSerie = v.findViewById(R.id.txtNSerie);
        txtfabricanteAparelho = v.findViewById(R.id.txtfabricanteAparelho);
        txtcategoriaAparelho = v.findViewById(R.id.txtcategoriaAparelho);
        txtmodeloAparelho = v.findViewById(R.id.txtmodeloAparelho);
        btnCadastrar = v.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Aparelho a = new Aparelho();
                    a.setNomeAparelho(String.valueOf(txtNomeAparelho.getEditText().getText()));
                    a.setNserieAparelho(Integer.parseInt(String.valueOf(txtNSerie.getEditText().getText())));
                    a.setFabricanteAparelho(Integer.parseInt(String.valueOf(txtfabricanteAparelho.getEditText().getText())));
                    a.setCategoriaAparelho(String.valueOf(txtcategoriaAparelho.getEditText().getText()));
                    a.setModeloAparelho(Integer.parseInt(String.valueOf(txtmodeloAparelho.getEditText().getText())));
                    a.setEmpresa(1);
                    AparelhoDAO adao = new AparelhoDAO(v.getContext());

                    if (adao.newAparelho(a) == true) {
                        Aparelho an = new Aparelho();
                        AparelhoDAO andao = new AparelhoDAO(v.getContext());
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        an = andao.searchAparelho(a.getNomeAparelho());
                        Log.e("Aparelho", "" + an);
                        dlg.setTitle("Sucesso");
                        dlg.setMessage("Aparelho '" + an.getId() + " '" + an.getNomeAparelho() + "' criado com sucesso");
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

}
