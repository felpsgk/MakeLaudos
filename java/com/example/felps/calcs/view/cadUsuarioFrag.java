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
import com.example.felps.calcs.controller.EmpresaDAO;
import com.example.felps.calcs.controller.UsuarioDAO;
import com.example.felps.calcs.model.Empresa;
import com.example.felps.calcs.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class cadUsuarioFrag extends Fragment {

    private Spinner cboxEmpresa;
    private List<Empresa> listEmpresa;
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

        popularCbox(v);
        cboxEmpresa = v.findViewById(R.id.cboxEmpresa);
        cboxEmpresa.setAdapter(empresaAdapter);
        txtNomeUtilizador = v.findViewById(R.id.txtNomeUtilizador);
        txtDocUtilizador = v.findViewById(R.id.txtDocUtilizador);
        txtContato = v.findViewById(R.id.txtContatoUtilizador);
        btnCadastrar = v.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oqsalvar;
                cboxEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "Empresa '"+listEmpresa.get(position).getNome()+"' selecionada", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Nenhuma empresa selecionada", Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    Usuario u = new Usuario();
                    u.setNome(txtNomeUtilizador.getText().toString());
                    u.setDocumento(txtDocUtilizador.getText().toString());
                    Empresa e = (Empresa) cboxEmpresa.getSelectedItem();
                    u.setEmpresa(e.getId());
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

    private void popularCbox(View v){

        listEmpresa = new ArrayList<>();
        EmpresaDAO edao = new EmpresaDAO(v.getContext());
        listEmpresa = edao.searchAll();

    }

    private BaseAdapter empresaAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listEmpresa.size();
        }

        @Override
        public Object getItem(int position) {
            return listEmpresa.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EmpresaHolder holder;
            View empresaView = convertView;

            if(empresaView == null){
                empresaView = getLayoutInflater().inflate(R.layout.linha_cbox_empresa,parent,false);

                holder = new EmpresaHolder();
                holder.empresaId = empresaView.findViewById(R.id.empresaId);
                holder.empresaNome = empresaView.findViewById(R.id.empresaNome);
                empresaView.setTag(holder);
            } else {
                holder = (EmpresaHolder) empresaView.getTag();
            }

            Empresa e = listEmpresa.get(position);
            holder.empresaId.setText(String.valueOf(e.getId()));
            holder.empresaNome.setText(e.getNome());

            return empresaView;
        }

        class EmpresaHolder {
            private TextView empresaId;
            private TextView empresaNome;
        }

    };

    public boolean intOrString (String contato){
        try{
            Integer.parseInt(contato);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

}
