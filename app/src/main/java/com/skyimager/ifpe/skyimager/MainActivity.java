package com.skyimager.ifpe.skyimager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageState;

/**ATIVIDADE RESPONSÁVEL PELO GERENCIAMENTO BÁSICO DA CÂMERA E ARMAZENAMENTO DE IMAGENS */
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("SkyImager", "Início");

         this.permissaoSd(); //Verificação de permissão e armazenamento
         this.armazenamentoDisp();//Verificação  armazenamento

         final Button camera = (Button) findViewById(R.id.btCamera);           //Botão (Centro)para abrir a câmera
         final Button cameraHandler = (Button) findViewById(R.id.btCameraTemp);//Botão(Direito) para abrir a câmera de s em s

        /***=*=*=*=*=*=*=*=*=*=*=*=*=*=*= Ação dos botões*=*=*=*=**=*=*=*=*=*=*=*=*=*=*=*=*/
        cameraHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCameraTemp();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });


    }

    /**=*=*=*=*=*=*=*=*=*=*=Métodos de ação (botões)=*=*=*=*=*=*=*=*=*=*=*=*=**/
    public void abrirCameraTemp() {
        //chama a classe CameraHandler, que possui a estrutura de abrir a câmera de s em s
        Intent intentCamera = new Intent(this, CameraHandler.class);
        startActivity(intentCamera);//Inicia a atividade
    }
    /*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/

    /*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    public void tirarFoto() {
        Log.i("SkyImager", "Entrou: tirarFoto();");

        Intent intentProxTela3= new Intent(this,ImagemCap.class);
        startActivity(intentProxTela3);//Inicia a atividade

    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/


    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    /**VERIFICA A PERMISSÃO PARA ACESSO AO CARTÃO SD */
    private void permissaoSd() {
        Log.i("SkyImager", "Entrou: verificarPermissaoSD();");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    /** VERIFICA SE HÁ ARMAZENAMENTO EXTERNO DISPONÍVEL  */

    public boolean armazenamentoDisp() {
        Log.i("SkyImager", "Entrou: ArmazenamentoDis();");

        String state = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {                             // Estado de armazenamento se a mídia estiver presente
            Log.i("SkyImager", "Situação:" + getExternalStorageState());
            return true;
        }
        Log.i("SkyImager", "Situação:" + getExternalStorageState());
        return false;

    }
    /**=*=*=*=*=*=*=*=*=*=*=Sets e gets=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/


}
