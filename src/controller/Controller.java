package controller;

import core.ImageProcess;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.ActionGranulado;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private Pane resultColor;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private Label lblR;

    @FXML
    private Label lblG;

    @FXML
    private Label lblB;

    @FXML
    private TextField rPercentual;

    @FXML
    private TextField gPercentual;

    @FXML
    private TextField bPercentual;

    @FXML
    private Slider slider;

    @FXML
    private RadioButton rdCruz;

    @FXML
    private RadioButton rdX;

    @FXML
    private RadioButton rd3;


    private Image image1, image2, image3;

    private File f;

    private boolean verificarRGB = true;

    @FXML
    void processarRuido() {
        if(rdCruz.isSelected()){
            image3 = ImageProcess.granularizacao(image1, ActionGranulado.VIZINHO_C);

        } else if(rdX.isSelected()) {
            image3 = ImageProcess.granularizacao(image1, ActionGranulado.VIZINHO_X);

        } else if(rd3.isSelected()){
            image3 = ImageProcess.granularizacao(image1, ActionGranulado.VIZINHO_3);

        }

        atualizaImage3(image3);

    }

    @FXML
    public void desafio1(){
        image3 = ImageProcess.desafio1(image1);
        atualizaImage3(image3);
    }

    @FXML
    public void negativa(ActionEvent event){
        image3 = ImageProcess.processarNegativo(image1);
        atualizaImage3(image3);
    }

    @FXML
    public void limiar(ActionEvent event) {
        double limiar = slider.getValue();
        image3 = ImageProcess.processarLimiar(image1, limiar);
        atualizaImage3(image3);
    }

    @FXML
    public void abreImagem1() {
        f = selecionaImagem();
        if (f != null) {
            image1 = new Image(f.toURI().toString());
            imageView1.setImage(image1);
            imageView1.setFitWidth(image1.getWidth());
            imageView1.setFitHeight(image1.getHeight());
        }
    }

    @FXML
    public void escalaDeCinzaMediaAritmetica(){
        image3 = ImageProcess.processarMediaAritmetica(image1);
        atualizaImage3(image3);
    }

    @FXML
    public void escalaDeCinzaMediaPonderada(){
        image3 = ImageProcess.processarMediaPonderada(image1, new Double(rPercentual.getText()), new Double(gPercentual.getText()), new Double(bPercentual.getText()));
        atualizaImage3(image3);
    }

    private void atualizaImage3(Image image){
        imageView3.setImage(image);
        imageView3.setFitWidth(image.getWidth());
        imageView3.setFitHeight(image.getHeight());
    }

    @FXML
    public void abreImagem2() {
        f = selecionaImagem();
        if (f != null) {
            image2 = new Image(f.toURI().toString());
            imageView2.setImage(image2);
            imageView2.setFitWidth(image2.getWidth());
            imageView2.setFitHeight(image2.getHeight());
        }
    }

    @FXML
    public void rasterImg(MouseEvent event) {
        if (!verificarRGB)
            return;

        if (event.getTarget() instanceof ImageView) {
            ImageView imageView = (ImageView) event.getTarget();
            Image image = imageView.getImage();

            if (image != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                verificaCor(image, x, y);
            }
        }
    }

    @FXML
    public void alterarVerificarRGB(MouseEvent event) {
        verificarRGB = !verificarRGB;
    }

    private void verificaCor(Image img, int x, int y) {
        try {
            Color cor = img.getPixelReader().getColor(x - 1, y - 1);
            int r = (int) (cor.getRed() * 255);
            int g = (int) (cor.getGreen() * 255);
            int b = (int) (cor.getBlue() * 255);

            lblR.setText("R: " + r);
            lblG.setText("G: " + g);
            lblB.setText("B: " + b);
            resultColor.setStyle("-fx-background-color: RGB(" + r + ", " + g + "," + b + ")");

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @FXML
    public void btnSalvar() {
        if (image1 != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagem", "*.png"));
            fileChooser.setInitialDirectory(new
                    File("D:\\PDI"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                BufferedImage bImg = SwingFXUtils.fromFXImage(image1, null);
                try {
                    ImageIO.write(bImg, "PNG", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            exibeMsg("Salvar imagem",
                    "Não é possível salvar a imagem.",
                    "Não há nenhuma imagem modificada.",
                    Alert.AlertType.ERROR);
        }
    }

    private void exibeMsg(String titulo, String cabecalho,
                          String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private File selecionaImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new
                FileChooser.ExtensionFilter(
                "Imagens", "*.jpg", "*.JPG",
                "*.png", "*.PNG", "*.gif", "*.GIF",
                "*.bmp", "*.BMP"));
        fileChooser.setInitialDirectory(new File(
                "D:\\PDI"));
        File imgSelec = fileChooser.showOpenDialog(null);

        try {
            if (imgSelec != null) {
                return imgSelec;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
