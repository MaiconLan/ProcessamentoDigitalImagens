package core;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

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

}
