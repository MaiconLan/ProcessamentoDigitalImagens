package core;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.ActionGranulado;
import model.ActionMedia;
import model.Pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageProcess {

    public static Image processarMediaAritmetica(Image imagem) {
        return processarImagem(imagem, ActionMedia.MEDIA_ARITMETICA, null, null, null, null);
    }

    public static Image processarMediaPonderada(Image imagem, Double r, Double g, Double b) {
        return processarImagem(imagem, ActionMedia.MEDIA_PONDERADA, null, r, g, b);
    }

    public static Image processarNegativo(Image imagem) {
        return processarImagem(imagem, ActionMedia.NEGATIVO, null, null, null, null);
    }

    public static Image processarLimiar(Image imagem, Double valor) {
        return processarImagem(imagem, ActionMedia.LIMIAR, valor, null, null, null);
    }

    private static Image processarImagem(Image imagem, ActionMedia actionMedia, Double value, Double r, Double g, Double b) {
        try {
            int w = (int) imagem.getWidth();
            int h = (int) imagem.getHeight();

            PixelReader pr = imagem.getPixelReader();
            WritableImage wi = new WritableImage(w, h);
            PixelWriter pw = wi.getPixelWriter();

            switch (actionMedia) {
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

    private static void negativo(int w, int h, PixelReader pr, PixelWriter pw) {
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

    private static void limiar(int w, int h, PixelReader pr, PixelWriter pw, double limiar) {
        limiar(0, 0, w, h, pr, pw, limiar);
    }

    private static void limiar(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw, double limiar) {
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double media = (corA.getRed() + corA.getGreen() + corA.getBlue()) / 3;

                Color novaCor = null;
                if (media > (limiar / 255))
                    novaCor = Color.WHITE;
                else
                    novaCor = Color.BLACK;

                pw.setColor(i, j, novaCor);
            }
        }
    }

    private static void aritmetica(int w, int h, PixelReader pr, PixelWriter pw) {
        aritmetica(0, 0, w, h, pr, pw);
    }

    public static void aritmetica(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw) {
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double media = (corA.getRed() + corA.getGreen() + corA.getBlue()) / 3;
                Color corN = new Color(media, media, media, corA.getOpacity());
                pw.setColor(i, j, corN);
            }
        }
    }

    private static void ponderada(int w, int h, PixelReader pr, PixelWriter pw, double r, double g, double b) {
        ponderada(0, 0, w, h, pr, pw, r, g, b);
    }

    private static void ponderada(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw, double r, double g, double b) {
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                double media = 0;

                if (r == 0 && g == 0 && b == 0)
                    media = (corA.getRed() + corA.getGreen() + corA.getBlue()) / 3;
                else
                    media = ((corA.getRed() * r) / 100 + (corA.getGreen() * g) / 100 + (corA.getBlue() * b) / 100);
                Color corN = new Color(media, media, media, corA.getOpacity());
                pw.setColor(i, j, corN);
            }
        }
    }

    public static void manter(int inicioW, int inicioH, int w, int h, PixelReader pr, PixelWriter pw) {
        for (int i = inicioW; i < w; i++) {
            for (int j = inicioH; j < h; j++) {
                Color corA = pr.getColor(i, j);
                pw.setColor(i, j, corA);
            }
        }
    }

    public static Image desafio1(Image imagem) {
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();
        PixelReader pr = imagem.getPixelReader();
        WritableImage wi = new WritableImage(w, h);
        PixelWriter pw = wi.getPixelWriter();
        double limite1 = 0.33;
        double limite2 = 0.66;

        for (int i = 1; i < w; i++) {
            for (int j = 1; j < h; j++) {

                Color corAtual = pr.getColor(i, j);
                Color novaCor = null;
                if (corAtual.getRed() < limite1)
                    novaCor = new Color(0.3, 0.5, 0.9, 1);

                else if (corAtual.getRed() > limite1 && corAtual.getRed() < limite2)
                    novaCor = new Color(0, 1, 0.5, 1);

                else
                    novaCor = new Color(1, 0, 1, 1);

                pw.setColor(i, j, novaCor);
            }
        }
        return wi;
    }

    public static Image desafio2(Image imagem) {
        try {
            int w = (int) imagem.getWidth();
            int h = (int) imagem.getHeight();

            PixelReader pr = imagem.getPixelReader();
            WritableImage wi = new WritableImage(w, h);
            PixelWriter pw = wi.getPixelWriter();

            manter(0, 0, (w * 1 / 4), h, pr, pw);
            aritmetica((w * 1 / 4), 0, (w * 2 / 4), h, pr, pw);
            limiar((w * 2 / 4), 0, (w * 3 / 4), h, pr, pw, 50);
            negativo((w * 3 / 4), 0, w, h, pr, pw);

            return wi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image granularizacao(Image imagem, ActionGranulado acao) {
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();

        PixelReader pr = imagem.getPixelReader();
        WritableImage wi = new WritableImage(w, h);
        PixelWriter pw = wi.getPixelWriter();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color corAtual = pr.getColor(i, j);

                List<Pixel> vizinhanca = new ArrayList<>();

                switch (acao) {
                    case VIZINHO_X:
                        vizinhanca = vizinhanca_x(pr, i, j);
                        break;

                    case VIZINHO_C:
                        vizinhanca = vizinhanca_c(pr, i, j);
                        break;

                    case VIZINHO_3:
                        vizinhanca = vizinhanca_x(pr, i, j);
                        vizinhanca.addAll(vizinhanca_c(pr, i, j));
                        break;
                }

                Color novaCor = new Color(medianaR(vizinhanca), medianaG(vizinhanca), medianaB(vizinhanca), corAtual.getOpacity());
                pw.setColor(i, j, novaCor);
            }
        }

        return wi;
    }

    private static List<Pixel> vizinhanca_x(PixelReader pr, int x, int y) {
        List<Pixel> vizinhanca = new ArrayList<>();

        Color cor1 = getColor(pr, x - 1, y - 1);
        Color cor2 = getColor(pr, x - 1, y + 1);
        Color cor3 = getColor(pr, x + 1, y - 1);
        Color cor4 = getColor(pr, x + 1, y + 1);

        vizinhanca.add(new Pixel(cor1 != null ? cor1.getRed() : null, cor1 != null ? cor1.getGreen() : null, cor1 != null ? cor1.getBlue() : null, x - 1, y - 1));
        vizinhanca.add(new Pixel(cor2 != null ? cor2.getRed() : null, cor2 != null ? cor2.getGreen() : null, cor2 != null ? cor2.getBlue() : null, x - 1, y + 1));
        vizinhanca.add(new Pixel(cor3 != null ? cor3.getRed() : null, cor3 != null ? cor3.getGreen() : null, cor3 != null ? cor3.getBlue() : null, x + 1, y - 1));
        vizinhanca.add(new Pixel(cor4 != null ? cor4.getRed() : null, cor4 != null ? cor4.getGreen() : null, cor4 != null ? cor4.getBlue() : null, x + 1, y + 1));

        return vizinhanca;
    }

    private static List<Pixel> vizinhanca_c(PixelReader pr, int x, int y) {
        List<Pixel> vizinhanca = new ArrayList<>();

        Color cor1 = getColor(pr, x, y - 1);
        Color cor2 = getColor(pr, x, y + 1);
        Color cor3 = getColor(pr, x - 1, y);
        Color cor4 = getColor(pr, x + 1, y);

        vizinhanca.add(new Pixel(cor1 != null ? cor1.getRed() : null, cor1 != null ? cor1.getGreen() : null, cor1 != null ? cor1.getBlue() : null, x, y - 1));
        vizinhanca.add(new Pixel(cor2 != null ? cor2.getRed() : null, cor2 != null ? cor2.getGreen() : null, cor2 != null ? cor2.getBlue() : null, x, y + 1));
        vizinhanca.add(new Pixel(cor3 != null ? cor3.getRed() : null, cor3 != null ? cor3.getGreen() : null, cor3 != null ? cor3.getBlue() : null, x - 1, y));
        vizinhanca.add(new Pixel(cor4 != null ? cor4.getRed() : null, cor4 != null ? cor4.getGreen() : null, cor4 != null ? cor4.getBlue() : null, x + 1, y));

        return vizinhanca;
    }

    public static List<Pixel> vizinhanca(PixelReader pr, int x, int y) {
        List<Pixel> vizinhanca = new ArrayList<>();
        vizinhanca.addAll(vizinhanca_c(pr, x, y));
        vizinhanca.addAll(vizinhanca_x(pr, x, y));
        return vizinhanca;
    }

    private static Color getColor(PixelReader px, int x, int y) {
        try {
            return px.getColor(x, y);

        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private static Double medianaR(List<Pixel> pixelList) {
        List<Double> cor = new ArrayList<>();
        pixelList.stream().filter(pixel -> pixel.getR() != null).forEach(pixel -> cor.add(pixel.getR()));
        Collections.sort(cor);
        return cor.get(cor.size() / 2);
    }

    private static Double medianaG(List<Pixel> pixelList) {
        List<Double> cor = new ArrayList<>();
        pixelList.stream().filter(pixel -> pixel.getG() != null).forEach(pixel -> cor.add(pixel.getG()));
        Collections.sort(cor);
        return cor.get(cor.size() / 2);
    }

    private static Double medianaB(List<Pixel> pixelList) {
        List<Double> cor = new ArrayList<>();
        pixelList.stream().filter(pixel -> pixel.getB() != null).forEach(pixel -> cor.add(pixel.getB()));
        Collections.sort(cor);
        return cor.get(cor.size() / 2);
    }

    public static Image adicaoSubtracao(Image imagem1, Image imagem2, double percentual1, ActionMedia acao) {
        int w1 = (int) imagem1.getWidth();
        int h1 = (int) imagem1.getHeight();
        int w2 = (int) imagem2.getWidth();
        int h2 = (int) imagem2.getHeight();

        int minW = Math.min(w1, w2);
        int minH = Math.min(h1, h2);

        PixelReader pr1 = imagem1.getPixelReader();
        PixelReader pr2 = imagem2.getPixelReader();

        WritableImage wi = new WritableImage(minW, minH);
        PixelWriter pw = wi.getPixelWriter();

        switch (acao) {
            case ADICAO:
                adicao(minW, minH, pr1, pr2, percentual1, pw);
                break;
            case SUBTRACAO:
                subtracao(minW, minH, pr1, pr2, pw);
                break;
            default:
                break;
        }

        return wi;
    }

    private static void adicao(int minW, int minH, PixelReader pr1, PixelReader pr2, double percentual1, PixelWriter pw) {
        for (int i = 0; i < minW; i++) {
            for (int j = 0; j < minH; j++) {
                Color cor1 = pr1.getColor(i, j);
                Color cor2 = pr2.getColor(i, j);

                double percentual2 = 100 - percentual1;

                double r = (cor1.getRed() * (percentual1 / 100)) + (cor2.getRed() * (percentual2 / 100));
                double g = (cor1.getGreen() * (percentual1 / 100)) + (cor2.getGreen() * (percentual2 / 100));
                double b = (cor1.getBlue() * (percentual1 / 100)) + (cor2.getBlue() * (percentual2 / 100));

                pw.setColor(i, j, new Color(r, g, b, (cor1.getOpacity() * (percentual1 / 100)) + (cor2.getOpacity() * (percentual2 / 100))));
            }
        }
    }

    private static void subtracao(int minW, int minH, PixelReader pr1, PixelReader pr2, PixelWriter pw) {
        for (int i = 0; i < minW; i++) {
            for (int j = 0; j < minH; j++) {
                Color cor1 = pr1.getColor(i, j);
                Color cor2 = pr2.getColor(i, j);

                double r = cor1.getRed() - cor2.getRed();
                double g = cor1.getGreen() - cor2.getGreen();
                double b = cor1.getBlue() - cor2.getBlue();

                pw.setColor(i, j, new Color(zerar(r), zerar(g), zerar(b), 1));
            }
        }
    }

    private static int minValue(int i, int j) {
        if (i < j)
            return i;
        else
            return j;
    }

    private static double zerar(double x) {
        return x > 0 ? x : 0;
    }

    public static void getGrafico(Image imagem, BarChart<String, Number> grafico) {
        int[] histograma = histograma(imagem);

        XYChart.Series vlr = new XYChart.Series();
        for (int i = 0; i < histograma.length; i++) {
            vlr.getData().add(new XYChart.Data(i + "", histograma[i]));
        }
        grafico.getData().addAll(vlr);
    }

    private static int[] histograma(Image imagem) {
        int[] histograma = new int[256];
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();
        PixelReader pr = imagem.getPixelReader();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                histograma[(int) (pr.getColor(i, j).getRed() * 255)]++;
                histograma[(int) (pr.getColor(i, j).getBlue() * 255)]++;
                histograma[(int) (pr.getColor(i, j).getGreen() * 255)]++;
            }
        }
        return histograma;
    }

    public static int[] histograma(Image imagem, int canal) {
        int[] histograma = new int[256];
        PixelReader pr = imagem.getPixelReader();
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                if (canal == 1)
                    histograma[(int) (pr.getColor(i, j).getRed() * 255)]++;
                else if (canal == 2)
                    histograma[(int) (pr.getColor(i, j).getGreen() * 255)]++;
                else
                    histograma[(int) (pr.getColor(i, j).getBlue() * 255)]++;
            }
        }
        return histograma;
    }

    public static Image atividade1(Image imagem, int distancia) {
        int w = (int) imagem.getWidth();
        int h = (int) imagem.getHeight();
        PixelReader pr = imagem.getPixelReader();
        WritableImage wi = new WritableImage(w, h);
        PixelWriter pw = wi.getPixelWriter();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color atual = pr.getColor(i, j);
                Color nova = Color.RED;

                if (i % distancia == 0) {
                    pw.setColor(i, j, nova);
                } else {
                    pw.setColor(i, j, atual);
                }
            }
        }

        return wi;
    }


}
