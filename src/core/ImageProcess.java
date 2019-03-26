package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageProcess {

    public static Image processarMediaAritmetica(Image imagem) {
        return processarImagem(imagem, Action.MEDIA_ARITMETICA, null, null, null, null);
    }

    public static Image processarMediaPonderada(Image imagem, Double r, Double g, Double b) {
        return processarImagem(imagem, Action.MEDIA_PONDERADA, null, r, g, b);
    }

    public static Image processarNegativo(Image imagem) {
        return processarImagem(imagem, Action.NEGATIVO, null, null, null, null);
    }

    public static Image processarLimiar(Image imagem, Double valor) {
        return processarImagem(imagem, Action.LIMIAR, valor, null, null, null);
    }

    private static Image processarImagem(Image imagem, Action action, Double value,  Double r, Double g, Double b){
        try {
            int w = (int) imagem.getWidth();
            int h = (int) imagem.getHeight();

            PixelReader pr = imagem.getPixelReader();
            WritableImage wi = new WritableImage(w, h);
            PixelWriter pw = wi.getPixelWriter();

            switch (action) {
                case NEGATIVO:
                    negativo(w, h, pr, pw);
                    break;
                case LIMIAR:
                    limiar(w, h, pr, pw, value);
                    break;
                case MEDIA_ARITMETICA:
                    aritmetica(w, h, pr, pw);
                    break;
                case MEDIA_PONDERADA:
                    ponderada(w, h, pr, pw, r, g, b);
                    break;
            }


            return wi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void negativo(int w, int h, PixelReader pr, PixelWriter pw){
        negativo(0, 0, w, h, pr, pw);
    }

    private static void negativo(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw) {
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double red = 1 - corA.getRed();
                double green = 1 - corA.getGreen();
                double blue = 1 - corA.getBlue();

                Color novaCor = new Color(red, green, blue, corA.getOpacity());


                pw.setColor(i, j, novaCor);
            }
        }
    }
    private static void limiar(int w, int h, PixelReader pr, PixelWriter pw, double limiar){
        limiar(0, 0, w, h, pr, pw, limiar);
    }

    private static void limiar(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw, double limiar){
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double media = (corA.getRed() + corA.getGreen() + corA.getBlue()) / 3;

                Color novaCor = null;
                if(media > (limiar/100))
                    novaCor = Color.WHITE;
                else
                    novaCor = Color.BLACK;

                pw.setColor(i, j, novaCor);
            }
        }
    }

    private static void aritmetica(int w, int h, PixelReader pr, PixelWriter pw){
        aritmetica(0, 0, w, h, pr, pw);
    }

    private static void aritmetica(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw){
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double media = (corA.getRed() + corA.getGreen() + corA.getBlue()) / 3;
                Color corN = new Color(media, media, media, corA.getOpacity());
                pw.setColor(i, j, corN);
            }
        }
    }

    private static void ponderada(int w, int h, PixelReader pr, PixelWriter pw, double r, double g, double b){
        ponderada(0, 0, w, h, pr, pw, r, g, b);
    }

    private static void ponderada(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw, double r, double g, double b){
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double media = 0;

                if(r == 0 && g == 0 && b == 0)
                    media = (corA.getRed() + corA.getGreen() + corA.getBlue()) / 3;
                else
                    media = ((corA.getRed() * r) / 100 + (corA.getGreen() * g) / 100 + (corA.getBlue() * b)/ 100 );
                Color corN = new Color(media, media, media, corA.getOpacity());
                pw.setColor(i, j, corN);
            }
        }
    }

    private static void manter(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw){
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                pw.setColor(i, j, corA);
            }
        }
    }

    public static Image desafio1(Image imagem) {
        try {
            int w = (int) imagem.getWidth();
            int h = (int) imagem.getHeight();

            PixelReader pr = imagem.getPixelReader();
            WritableImage wi = new WritableImage(w, h);
            PixelWriter pw = wi.getPixelWriter();

            manter(0,0, (w*1/4),h, pr, pw);
            aritmetica((w*1/4),0, (w*2/4),h, pr, pw);
            limiar((w*2/4),0, (w*3/4),h, pr, pw, 50);
            negativo((w*3/4),0, w,h, pr, pw);

            return wi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
