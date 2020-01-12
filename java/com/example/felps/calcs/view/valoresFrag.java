package com.example.felps.calcs.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felps.calcs.R;
import com.example.felps.calcs.controller.caracteristicaDAO;
import com.example.felps.calcs.controller.controllerPDF;
import com.example.felps.calcs.model.Aparelho;
import com.example.felps.calcs.model.Caracteristica;
import com.example.felps.calcs.model.Empresa;
import com.example.felps.calcs.model.Usuario;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class valoresFrag extends Fragment {

    public valoresFrag() {
        // Required empty public constructor
    }

    Image i;
    Bitmap bitmap;
    Button btnCamera;
    private controllerPDF tpdf;
    private Button btnGerar;
    private Button btnSaveCaracteristica;
    private EditText txtValorObt;
    ImageView imgView;
    View v;
    Usuario u;
    Aparelho a;
    Empresa e;
    Caracteristica c;
    caracteristicaDAO cdao;
    private Spinner cboxCaracteristica;
    private List<Caracteristica> listCaracteristica;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_valores, container, false);

        Bundle bundle = getArguments();
        u = (Usuario) bundle.getSerializable("usuario");
        e = (Empresa) bundle.getSerializable("empresa");
        a = (Aparelho) bundle.getSerializable("aparelho");

        popularCbox(v);
        cboxCaracteristica = v.findViewById(R.id.cboxCaracteristica);
        cboxCaracteristica.setAdapter(caracteristicaAdapter);
        cboxCaracteristica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Caracteristica '"+listCaracteristica.get(position).getDescricao()+"' selecionado", Toast.LENGTH_SHORT).show();
                c = (Caracteristica) cboxCaracteristica.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Nenhum caracat selecionado", Toast.LENGTH_SHORT).show();
            }
        });
        txtValorObt = v.findViewById(R.id.txtValorObt);
        btnSaveCaracteristica = v.findViewById(R.id.btnSaveCaracteristica);
        btnSaveCaracteristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdao = new caracteristicaDAO(v.getContext());
                c.setVal_obtido(Float.parseFloat(txtValorObt.getText().toString()));
                cdao.update(c);
            }
        });

       /* SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(v.getContext(), getFragmentManager() );
        ViewPager viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        imgView = v.findViewById(R.id.imgEquipamento);

        btnCamera = v.findViewById(R.id.btnCamera);

        tpdf = new controllerPDF(v.getContext());

        btnGerar = v.findViewById(R.id.btnGerar);

        btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constroiPdf();
            }
        });

        btnCamera = v.findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        return v;
    }

    private BaseAdapter caracteristicaAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listCaracteristica.size();
        }

        @Override
        public Object getItem(int position) {
            return listCaracteristica.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CaracteristicaHolder holder;
            View caracteristicaView = convertView;

            if(caracteristicaView == null){
                caracteristicaView = getLayoutInflater().inflate(R.layout.linha_cbox_caracteristica,parent,false);

                holder = new CaracteristicaHolder();
                holder.caracteristicaId = caracteristicaView.findViewById(R.id.caracteristicaId);
                holder.caracteristicaNome = caracteristicaView.findViewById(R.id.caracteristicaNome);
                caracteristicaView.setTag(holder);
            } else {
                holder = (CaracteristicaHolder) caracteristicaView.getTag();
            }

            Caracteristica c = listCaracteristica.get(position);
            holder.caracteristicaId.setText(String.valueOf(c.getId()));
            holder.caracteristicaNome.setText(c.getDescricao());

            return caracteristicaView;
        }

        class CaracteristicaHolder {
            private TextView caracteristicaId;
            private TextView caracteristicaNome;
        }

    };

    public void constroiPdf() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = dateFormat.format(data);
        try {
            tpdf.createPdf();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        //nova formatação
        Font f1Text = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD);
        Font f2Text = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.NORMAL);
        tpdf.addTitles("Laudo", "Verificação de aparelho", "Gerado em " + dataFormatada);

        PdfPTable medidor = new PdfPTable(2);
        medidor.setSpacingBefore(5);
        medidor.setSpacingAfter(5);
        medidor.addCell(new Paragraph("Empresa responsável pela medição", f1Text));
        medidor.addCell(new Paragraph(e.getNome()+" Contato: "+e.getContato(), f2Text));

        medidor.addCell(new Paragraph("Técnico medidor", f1Text));
        medidor.addCell(new Paragraph(u.getNome()+" -- Doc. Nº "+u.getDocumento(), f2Text));

        medidor.addCell(new Paragraph("Aparelho medido", f1Text));
        medidor.addCell(new Paragraph(a.getNomeAparelho(), f2Text));

        medidor.addCell(new Paragraph("Data da geração do f1Text", f1Text));
        medidor.addCell(new Paragraph(dataFormatada, f2Text));


        List <Caracteristica> cs = cdao.searchAparelho(String.valueOf(a.getId()));
        cdao = new caracteristicaDAO(v.getContext());
        PdfPTable medidas = new PdfPTable(4);
        medidas.getDefaultCell().setBorder(3);
        medidas.setSpacingBefore(5);
        medidas.setSpacingAfter(10);
        medidas.addCell(new Paragraph("Característica", f1Text));
        medidas.addCell(new Paragraph("Valor padrão", f1Text));
        medidas.addCell(new Paragraph("Valor obtido", f1Text));
        medidas.addCell(new Paragraph("Variação %", f1Text));

        for (int i = 0; i < cs.size(); i++) {
            Caracteristica ca = new Caracteristica();
            ca = cs.get(i);
            medidas.addCell(new Paragraph(ca.getDescricao(), f2Text));
            medidas.addCell(new Paragraph(String.valueOf(ca.getVal_padrao()), f2Text));
            medidas.addCell(new Paragraph(String.valueOf(ca.getVal_obtido()), f2Text));
            double parcial = (ca.getVal_obtido()*100)/ca.getVal_padrao();
            double resultado = parcial-100.00;
            if (resultado > 0) {
                String resposta = String.format("%.3f", resultado);
                medidas.addCell(new Paragraph("+"+ resposta +"%", f2Text));
            } else if (resultado < 0){
                String resposta = String.format("%.3f", resultado);
                medidas.addCell(new Paragraph(""+ resposta +"%", f2Text));
            } else if (resultado == 0){
                medidas.addCell(new Paragraph("Sem variação", f2Text));
            }
        }
        tpdf.addTableToDoc(medidor);
        tpdf.addParagraph("A seguir, os dados coletados");
        tpdf.addTableToDoc(medidas);

        if (bitmap == null) {
            tpdf.closeDocument();
        } else {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                i = Image.getInstance(stream.toByteArray());
                i.setDpi(900, 900);
                i.scaleToFit(300f, 350f);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadElementException e) {
                e.printStackTrace();
            }
            tpdf.addImages(i);
            tpdf.closeDocument();
        }
        Toast.makeText(getView().getContext(), "PDF gerado. Vá a Aramzenamento interno/documents para encontrar", Toast.LENGTH_LONG).show();
    }

    //PEGA IMAGEM DA CAMERA DO CELULAR
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imgView.setImageBitmap(bitmap);
        ConstraintLayout.LayoutParams layoutParams;
        layoutParams = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        layoutParams.width = 1000;
        layoutParams.height = 1250;
        imgView.setLayoutParams(layoutParams);
    }

    private void popularCbox(View v){

        listCaracteristica = new ArrayList<>();
        caracteristicaDAO cdao = new caracteristicaDAO(v.getContext());
        listCaracteristica = cdao.searchAparelho(String.valueOf(a.getId()));

    }

}