package com.example.felps.calcs.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.felps.calcs.controller.EmpresaDAO;
import com.example.felps.calcs.controller.UsuarioDAO;
import com.example.felps.calcs.model.Aparelho;
import com.example.felps.calcs.model.Empresa;
import com.example.felps.calcs.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class startAnaliseFrag extends Fragment {

    private Spinner cboxEmpresa;
    private List<Empresa> listEmpresa;
    private Spinner cboxUsuario;
    private List<Usuario> listUsuario;
    private Spinner cboxAparelho;
    private List<Aparelho> listAparelho;
    private EditText txtNomeEquip;
    private EditText txtNumSerie;
    private Button btnIniciar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start_analise, container, false);

        popularCbox(v);
        txtNomeEquip = v.findViewById(R.id.txtNomeEquip);
        txtNumSerie = v.findViewById(R.id.txtNumSerie);
        btnIniciar = v.findViewById(R.id.btnIniciar);
        cboxEmpresa = v.findViewById(R.id.cboxEmpresa);
        cboxEmpresa.setAdapter(empresaAdapter);
        cboxUsuario = v.findViewById(R.id.cboxUsuario);
        cboxUsuario.setAdapter(usuarioAdapter);
        cboxAparelho = v.findViewById(R.id.cboxAparelho);
        cboxAparelho.setAdapter(aparelhoAdapter);

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
        cboxUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Usuario '"+listUsuario.get(position).getNome()+"' selecionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Nenhum usuário selecionado", Toast.LENGTH_SHORT).show();
            }
        });
        cboxAparelho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Aparelho '"+listAparelho.get(position).getNome()+"' selecionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Nenhum aparelho selecionado", Toast.LENGTH_SHORT).show();
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                valoresFrag frag = new valoresFrag();
                ft.replace(R.id.fragment_container, frag);
                ft.commit();
            }
        });
        return v;
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

    private BaseAdapter usuarioAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listUsuario.size();
        }

        @Override
        public Object getItem(int position) {
            return listUsuario.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UsuarioHolder holder;
            View usuarioView = convertView;

            if(usuarioView == null){
                usuarioView = getLayoutInflater().inflate(R.layout.linha_cbox_usuario,parent,false);

                holder = new UsuarioHolder();
                holder.usuarioId = usuarioView.findViewById(R.id.usuarioId);
                holder.usuarioNome = usuarioView.findViewById(R.id.usuarioNome);
                usuarioView.setTag(holder);
            } else {
                holder = (UsuarioHolder) usuarioView.getTag();
            }

            Usuario u = listUsuario.get(position);
            holder.usuarioId.setText(String.valueOf(u.getId()));
            holder.usuarioNome.setText(u.getNome());

            return usuarioView;
        }

        class UsuarioHolder {
            private TextView usuarioId;
            private TextView usuarioNome;
        }

    };

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
            holder.aparelhoNome.setText(a.getNome());

            return aparelhoView;
        }

        class AparelhoHolder {
            private TextView aparelhoId;
            private TextView aparelhoNome;
        }

    };

    public startAnaliseFrag() {
        // Required empty public constructor
    }

    private void popularCbox(View v){

        listEmpresa = new ArrayList<>();
        EmpresaDAO edao = new EmpresaDAO(v.getContext());
        listEmpresa = edao.searchAll();

        listUsuario = new ArrayList<>();
        UsuarioDAO udao = new UsuarioDAO(v.getContext());
        listUsuario = udao.searchAll();

        listAparelho = new ArrayList<>();
        AparelhoDAO adao = new AparelhoDAO(v.getContext());
        listAparelho = adao.searchAll();
    }

}
