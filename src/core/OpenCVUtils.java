package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class OpenCVUtils {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static final String RESULTADO = ".\\config\\resultado.jpeg";
    /*
    public static void dilatacao1(Image image) {

        Mat source = Imgcodecs.imread(".\\config\\caras.jpeg");
        Mat source = Imgcodecs.imread(".\\config\\caras_dilatadas.jpeg");

        Imgproc.dilate(source, destination, element);

    }*/

    public static String dilatacao(String url) {
        int dilation_size = 5;

        String urlTratada = url.replace("file:/", "").replace("/", "\\\\");

        Mat source = Imgcodecs.imread(urlTratada);

        Mat destination = source;

        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));
        Imgproc.dilate(source, destination, element);
        Imgcodecs.imwrite(RESULTADO, destination);

        return RESULTADO.replace("\\", "\\\\");
    }

    public static String erosao(String url) {
        int erosion_size = 5;

        String urlTratada = url.replace("file:/", "").replace("/", "\\\\");

        Mat source = Imgcodecs.imread(urlTratada);

        Mat destination = source;

        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2*erosion_size + 1, 2*erosion_size+1));
        Imgproc.erode(source, destination, element);
        Imgcodecs.imwrite(RESULTADO, destination);

        return RESULTADO.replace("\\", "\\\\");
    }

    public static Image canny(Image imagem, double bordaThreshold) {
        Mat detectedEdges = imageToMat(imagem);
        Mat destino = imageToMat(imagem);
        Imgproc.cvtColor(detectedEdges, destino, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(destino, detectedEdges, new Size(3, 3));
        Imgproc.Canny(detectedEdges, detectedEdges, bordaThreshold, bordaThreshold * 3, 3, false);
        Core.add(detectedEdges, Scalar.all(0), destino);

        destino.copyTo(detectedEdges, destino);

        return matrixToImage(destino);
    }

    public static Mat imageToMat(Image image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];

        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;
    }

    public static Image matrixToImage(Mat mat){
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".bmp", mat, byteMat);
        return new Image(new ByteArrayInputStream(byteMat.toArray()));
    }
}
