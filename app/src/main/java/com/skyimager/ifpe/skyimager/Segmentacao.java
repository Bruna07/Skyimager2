package com.skyimager.ifpe.skyimager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import static com.skyimager.ifpe.skyimager.ImagemCap.getFileFoto;


public class Segmentacao extends AppCompatActivity {
    private Bitmap imagemO;//Imagem original
    private ImageView imagemOR;
    private ImageView imagemOG;
    private ImageView imagemOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segmentacao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i("SkyImager", "Inicio tela segmentação");

        imagemOG = (ImageView) findViewById(R.id.imagemGL);
        imagemOB = (ImageView) findViewById(R.id.imagemBL);

     this.segmentacao();
    }

    public void segmentacao() {
        Log.i("Bruna", "ENTROU:TESTE IMAGEMB ");


        imagemO = BitmapFactory.decodeFile( getFileFoto().getAbsolutePath());// Converte o destino do arquivo no bitmap

        int larg = imagemO.getWidth();//largura da imagem
        int alt = imagemO.getHeight();//altura da imagem

        int[][] matrizR = new int[larg][alt];
        int[][] matrizG = new int[larg][alt];
        int[][] matrizB = new int[larg][alt];
        int[][] matrizResR = new int[larg][alt];
        int[][] matrizResG = new int[larg][alt];
        int[][] matrizResB = new int[larg][alt];


        String x = " ";

        for (int linha = 0; linha < alt; linha++) {
            for (int coluna = 0; coluna < larg; coluna++) {


                int pixel = imagemO.getPixel(coluna, linha);


                int red2 = Color.red(pixel);
                int green2 = Color.green(pixel);
                int blue2 = Color.blue(pixel);


                matrizR[coluna][linha] = red2;
                matrizG[coluna][linha] = green2;
                matrizB[coluna][linha] = blue2;

            }

        }


        Bitmap imR = Bitmap.createBitmap(imagemO.getWidth(), imagemO.getHeight(), Bitmap.Config.ARGB_8888);
        for (int linha = 0; linha < alt; linha++) {
            for (int coluna = 0; coluna < larg; coluna++) {
                imR.setPixel(coluna, linha, (matrizR[coluna][linha] << 16) | 0xFF000000);
            }
        }
        int R, G, B = 0;

        for (int linha = 0; linha < alt; linha++) {
            for (int coluna = 0; coluna < larg; coluna++) {
                R = matrizR[coluna][linha];
                G = matrizG[coluna][linha];
                B = matrizB[coluna][linha];
                if (B > ((11288 - 41.7524 * R - 17.1889 * G) / 34.2023)) {
                    //nuvem
                    matrizResR[coluna][linha] = 80;
                    matrizResG[coluna][linha] = 90;
                    matrizResB[coluna][linha] = 100;
                } else {
                    //ceu
                    matrizResR[coluna][linha] = 110;
                    matrizResG[coluna][linha] = 160;
                    matrizResB[coluna][linha] = 190;
                }
                if (B > (16097 - 31.2814 * R - 28.7598 * G) / 33.9229) {

                    matrizResR[coluna][linha] = 230;
                    matrizResG[coluna][linha] = 230;
                    matrizResB[coluna][linha] = 220;
                }
            }
        }
        Bitmap imB = Bitmap.createBitmap(imagemO.getWidth(), imagemO.getHeight(), Bitmap.Config.ARGB_8888);
        for (int linha = 0; linha < alt; linha++) {
            for (int coluna = 0; coluna < larg; coluna++) {
                imB.setPixel(coluna, linha, (matrizResB[coluna][linha] | (matrizResG[coluna][linha] << 8) | (matrizResR[coluna][linha] << 16)) | 0xFF000000);
            //imagem limiarizada
            }
        }
        //imagemOR.setImageBitmap(imR);
        imagemOG.setImageBitmap(imagemO);//imagem original
        imagemOB.setImageBitmap(imB);//imagem final
    }
}
