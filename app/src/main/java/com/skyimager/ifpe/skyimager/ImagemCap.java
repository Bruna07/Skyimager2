package com.skyimager.ifpe.skyimager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImagemCap extends AppCompatActivity {
    private ImageView fotoCap;       // Mostra a imagem capturada na tela principal
    static   private File fileFoto; //Arquivo da imagem capturada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem_cap);
        fotoCap = (ImageView) findViewById(R.id.imagemCap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tirarFoto();
        final Button btSegmentacao = (Button) findViewById(R.id.btSeg);         //botão(Esquerdo) está sendo usado para mostrar o resul. da segmentação

        btSegmentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaSegmentacao();
            }
        });
    }
    public void tirarFoto() {
        Log.i("SkyImager", "Entrou: tirarFoto();");

        //A Intent dá acesso a câmera do dispositivo
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentFoto, 1);//Inicia a atividade e retorna a imagem capturada
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    public void telaSegmentacao() {
        //chama a classe Segmentação, que possui a estrutura de limiarizar e segmentar a imagem
        Intent intentProxTela= new Intent(this,Segmentacao.class);
        startActivity(intentProxTela);//Inicia a atividade
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    /**O onActivityResult() retorna o resultado da intentFoto, por meio do requestCode.*/
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Dá o retorno da intent 1 ( requestCode=1)

            //-*-*-O trecho converte o retorno do onActivityResult em um bitmap para mostrar na no ImageView-*-*-*-
            Bundle extras = data.getExtras();
            Bitmap formatImagem = (Bitmap) extras.get("data");
            fotoCap.setImageBitmap(formatImagem);
            //*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

            this.salvarArquivo(formatImagem);// Salva a imagem capturada através do met. SalvarArquivo();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    private void salvarArquivo(Bitmap bitmap2) {
        Log.i("SkyImager", "Entrou: salvarArquivo();");

        File [] externalStorageVolumes = ContextCompat. getExternalFilesDirs ( getApplicationContext (), null );
        File primaryExternalStorage = externalStorageVolumes [1];//Seleciona o aramzenamento a ser usado

        File pastaArquivos = new File(primaryExternalStorage + "/Camera2_Arquivos/");

       // String root = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();//Aramzenar os arquivos no cartão mas não funciona;
        //File pastaArquivos = new File(root + "/Camera2_Arquivos/");                          //Da o nome a pasta de  armazenamento das imagens no dispositivo;

        pastaArquivos.mkdirs();                                                                //Cria o diretório;


        String sobreNome = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());//Formato do complemento do nome do arquivo;
        String nome = "skyImager_" + sobreNome + ".jpg";                                      // Formato completo do nome do arquivo;
        setFileFoto(new File(pastaArquivos,nome));                                            //Destino do arquivo (imagem);


        if (getFileFoto().exists()) getFileFoto().delete();                                   //Verifica se o arquivo existe;
        try {

            FileOutputStream salvar = new FileOutputStream(getFileFoto(), true);      //Cria um fluxo de saída do arquivo para gravar no arquivo representado
            // pelo File objeto especificado ;

           bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, salvar);                //Especifica os formato que o Bitmap será salvo;
            salvar.close();

            Log.i("SkyImager", "Salvou  arquivo " + getFileFoto());

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("SkyImager", "Erro");
        }
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**/
    public static File getFileFoto() {
        return fileFoto;

    }
    public void setFileFoto (File fileFotoo) {
        this.fileFoto = fileFotoo;
    }
}
