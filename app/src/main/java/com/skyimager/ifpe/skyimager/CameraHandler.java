package com.skyimager.ifpe.skyimager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
/**ATIVIDADE RESPONSÁVEL PELO GERENCIAMENTO DA CÃMERA DE s EM s*/
public class CameraHandler extends AppCompatActivity {

    Handler hd= new Handler();//Possibilita o controle de atividade de tempo em tempo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_handler);
        Log.i("SkyImager", "Inicio CameraHandler");

        abrirCamera();

    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    private void abrirCamera(){
        new Thread() {
            @Override
            public void run() {
                chamarCamera();
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        abrirCamera();
                    }
                }, 20000);
            }
        }.start();

    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    @Override
    protected void onDestroy(){
        super.onDestroy();
        hd.removeCallbacksAndMessages(null);
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/

    public  void chamarCamera(){
        //A Intent dá acesso a câmera do dispositivo
        Intent intentCamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera,1);//Inicia a câmera


    }
}
