package com.example.felps.calcs.view;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felps.calcs.R;
import com.example.felps.calcs.controller.AparelhoDAO;
import com.example.felps.calcs.controller.caracteristicaDAO;
import com.example.felps.calcs.model.Aparelho;
import com.example.felps.calcs.model.Caracteristica;

import java.util.ArrayList;
import java.util.List;

public class cadCaracteristicaFrag extends Fragment {

    private EditText txtDescCaracteristica;
    private EditText txtValorEsperado;
    private Button btnCadastrar;
    private Spinner cboxAparelhoId;
    private List<Aparelho> listAparelho;
    Aparelho a;

    public cadCaracteristicaFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cad_caracteristica, container, false);
        popularCbox(v);
        txtDescCaracteristica = v.findViewById(R.id.txtDescCaracteristica);
        txtValorEsperado = v.findViewById(R.id.txtValorEsperado);

        cboxAparelhoId = v.findViewById(R.id.cboxAparelhoId);
        cboxAparelhoId.setAdapter(aparelhoAdapter);
        cboxAparelhoId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Aparelho '"+listAparelho.get(position).getNomeAparelho()+"' selecionado", Toast.LENGTH_SHORT).show();
                a = (Aparelho) cboxAparelhoId.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Nenhum aparelho selecionado", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar = v.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Caracteristica c = new Caracteristica();
                    c.setDescricao(txtDescCaracteristica.getText().toString());
                    c.setVal_padrao(Float.parseFloat(txtValorEsperado.getText().toString()));
                    c.setIdAparelho(a.getId());
                    caracteristicaDAO cdao = new caracteristicaDAO(v.getContext());

                    if (cdao.newCaracteristica(c) == true) {
                        Caracteristica cn = new Caracteristica();
                        caracteristicaDAO cndao = new caracteristicaDAO(v.getContext());
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        cn = cndao.searchCaracteristica(c.getDescricao());
                        dlg.setTitle("Sucesso");
                        dlg.setMessage("Característica '" + cn.getDescricao() + "' referente ao aparelho '"+ cn.getIdAparelho()+"' criado com sucesso");
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
    private BaseAdapter aparelhoAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listAparelho.size();
        }

        @Override
        public Object getItem(int position) {
            return listAparelho.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AparelhoHolder holder;
            View aparelhoView = convertView;

            if(aparelhoView == null){
                aparelhoView = getLayoutInflater().inflate(R.layout.linha_cbox_aparelho,parent,false);

                holder = new AparelhoHolder();
                holder.aparelhoId = aparelhoView.findViewById(R.id.aparelhoId);
                holder.aparelhoNome = aparelhoView.findViewById(R.id.aparelhoNome);
                aparelhoView.setTag(holder);
            } else {
                holder = (AparelhoHolder) aparelhoView.getTag();
            }

            Aparelho a = listAparelho.get(position);
            holder.aparelhoId.setText(String.valueOf(a.getId()));
            holder.aparelhoNome.setText(a.getNomeAparelho());

            return aparelhoView;
        }

        class AparelhoHolder {
            private TextView aparelhoId;
            private TextView aparelhoNome;
        }

    };

    private void popularCbox(View v){

        listAparelho = new ArrayList<>();
        AparelhoDAO adao = new AparelhoDAO(v.getContext());
        listAparelho = adao.searchAll();

    }

}
