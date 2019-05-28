package com.example.felps.calcs.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.felps.calcs.R;
import com.example.felps.calcs.controller.AparelhoDAO;
import com.example.felps.calcs.controller.EmpresaDAO;
import com.example.felps.calcs.controller.UsuarioDAO;
import com.example.felps.calcs.controller.controllerPDF;
import com.example.felps.calcs.model.Aparelho;
import com.example.felps.calcs.model.Empresa;
import com.example.felps.calcs.model.Usuario;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class valoresFrag extends Fragment {

    public valoresFrag() {
        // Required empty public constructor
    }

    Image i;
    Bitmap bitmap;
    Button btnCamera;
    private controllerPDF tpdf;
    private Button btnGerar;
    ImageView imgView;
    View v;
    Usuario u;
    Aparelho a;
    Empresa e;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_valores, container, false);

        Bundle bundle = getArguments();
        u = (Usuario) bundle.getSerializable("usuario");
        e = (Empresa) bundle.getSerializable("empresa");
        a = (Aparelho) bundle.getSerializable("aparelho");

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
        //RECEBE OS GETEXTRA INTENT
        tpdf.addTitles("Laudo teste","teste laudo","Gerado em "+dataFormatada);
        tpdf.addParagraph("O laudo a seguir foi gerado por:");
        tpdf.addParagraph(u.getNome());
        tpdf.addParagraph("Dono do documento -- "+u.getDocumento());
        tpdf.addParagraph("Funcionário da empresa: ");
        tpdf.addParagraph(e.getNome());
        tpdf.addParagraph("Telefone -- "+e.getContato());
        tpdf.addParagraph("E ele apresentará dados do aparelho: ");
        tpdf.addParagraph(a.getNome());
        tpdf.addParagraph("Nº de série -- "+a.getNserie());
        if (bitmap == null) {
            tpdf.closeDocument();
        } else {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                i = Image.getInstance(stream.toByteArray());
                i.setDpi(900,900);
                i.scaleToFit(300f,350f);

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

}
