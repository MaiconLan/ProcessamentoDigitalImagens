package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Pixel;

import java.util.ArrayList;
import java.util.List;

public class Prova1 {

    private static int pressX, pressY;
    private static int releaseX, releseY;

    public static Image questao1(Image imagem, Integer colunas) {
        try {
            int w = (int) imagem.getWidth();
            int h = (int) imagem.getHeight();

            PixelReader pr = imagem.getPixelReader();
            WritableImage wi = new WritableImage(w, h);
            PixelWriter pw = wi.getPixelWriter();

            for (int i = 0; i < colunas; i++) {

                if (i % 2 == 0)
                    ImageProcess.manter((w * (i) / colunas), 0, (w * (i + 1) / colunas), h, pr, pw);
                else
                    ImageProcess.aritmetica((w * (i) / colunas), 0, (w * (i + 1) / colunas), h, pr, pw);
            }

            return wi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String questao3(Image imagem) {
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();

        PixelReader pr = imagem.getPixelReader();
        List<Pixel> pixels = new ArrayList<>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color cor = pr.getColor(i, j);
                Pixel pixel = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), i, j);
                pixel.setVizinhos3(ImageProcess.vizinhanca(pr, i, j));
                pixels.add(pixel);
            }
        }
        String resultado = "";
        Pixel pixelPreto = new Pixel(0D, 0D, 0D);
        for (Pixel pixel : pixels) {
            int qtdPixel = 0;
            for (Pixel vizinho : pixel.getVizinhos3()) {
                if (pixel.equals(pixelPreto) && pixel.equals(vizinho)) {
                    qtdPixel++;
                }
            }

            if (qtdPixel > 1 && qtdPixel < 3)
                resultado = "Não pintado";
            else if (qtdPixel >= 3)
                return "Pintado";
        }
        return resultado;
    }

    public static String questao3V2(Image imagem) {
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();

        PixelReader pr = imagem.getPixelReader();
        List<Pixel> pixels = new ArrayList<>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color cor = pr.getColor(i, j);
                Pixel pixel = new Pixel(cor.getRed(), cor.getGreen(), cor.getBlue(), i, j);
                pixel.setVizinhos3(ImageProcess.vizinhanca(pr, i, j));
                pixels.add(pixel);
            }
        }
        Pixel pixelPreto = new Pixel(0D, 0D, 0D);
        for (Pixel pixel : pixels) {
            int qtdPixel = 0;
            if (pixel.equals(pixelPreto)) {
                for (Pixel vizinho : pixel.getVizinhos3()) {
                    if(pixelPreto.equals(vizinho))
                        qtdPixel++;
                }

                if(qtdPixel > 1 && qtdPixel < 3)
                    return "Não pintado";
                else if(qtdPixel >= 3 )
                    return "Pintado";

            }


        }
        return "Não identificado";
    }

    public static void pressed(Image image, int x, int y) {
        pressX = x;
        pressY = y;
    }

    public static void released(Image image, int x, int y) {
        releaseX = x;
        releseY = y;
    }

    private static Image pintarRetangulo(Image imagem) {

        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();

        PixelReader pr = imagem.getPixelReader();
        WritableImage wi = new WritableImage(h, w);
        PixelWriter pw = wi.getPixelWriter();

        rotacionar90(pr, pw, w, h, pressX, pressY, releaseX, releseY);

        return wi;
    }

    private static void rotacionar90(PixelReader pr, PixelWriter pw, int wImagem, int hImagem, int wInicial, int hInicial, int wFinal, int hFinal) {
        int m = wFinal;
        for (int i = 0; i < wImagem; i++) {
            int n = hFinal;
            for (int j = 0; j < hImagem; j++) {
                Color prevColor = pr.getColor(i, j);
                Color novaCor = pr.getColor(i, j);

                int x = j;
                int y = i;
                if (i >= wInicial && i <= wFinal && j >= hInicial && j <= hFinal) {

                    double color1 = (prevColor.getRed());
                    double color2 = (prevColor.getGreen());
                    double color3 = (prevColor.getBlue());

                    novaCor = new Color(color1, color2, color3, prevColor.getOpacity());
                    x = n - j;
                    y = m - i;
                }

                pw.setColor(x, y, novaCor);
            }
        }
    }

    public static Image questao2(Image imagem, MouseEvent event) {
        return adicionaBorda(imagem, event);
    }

    public static Image adicionaBorda(Image img, MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int x2 = (int) event.getX();
        int y2 = (int) event.getY();

        PixelReader pr = img.getPixelReader();
        WritableImage wi = new WritableImage(x, y);
        PixelWriter pw = wi.getPixelWriter();

        for (int i = x; i < x2; i++) {
            Color newColor;
            newColor = new Color(1, 1, 1, 1);
            pw.setColor(x, x2, newColor);
        }
        return wi;
    }

    public static Image giraImgaem(Image img) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();

        PixelReader pr = img.getPixelReader();
        WritableImage wi = new WritableImage(w, h);
        PixelWriter pw = wi.getPixelWriter();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

            }
        }
        return wi;
    }

}
