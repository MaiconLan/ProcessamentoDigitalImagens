package controller;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class opencv_version {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }

    public static void detectorDeRostos(){
        CascadeClassifier faceDetector =
                new CascadeClassifier(".\\config\\haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread(".\\config\\caras.jpeg");

        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println("Detectado: " + faceDetections.toArray().length);

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        //salva imagem
        Imgcodecs.imwrite(".\\config\\rostos_identificados.jpg", image);
    }

}