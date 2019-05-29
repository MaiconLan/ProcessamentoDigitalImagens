package controller;

import core.ImageProcess;
import core.OpenCVUtils;
import core.Prova1;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ActionGranulado;
import model.ActionMedia;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Controller {

    @FXML
    private Pane resultColor;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private Label resultadoProva1Questao3;

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
    private TextField distancia;

    @FXML
    private TextField gPercentual;

    @FXML
    private TextField bPercentual;

    @FXML
    private TextField colunas;

    @FXML
    private Slider slider;

    @FXML
    private RadioButton rdCruz;

    @FXML
    private RadioButton rdX;

    @FXML
    private RadioButton rd3;

    @FXML
    private Slider sliderPercentual;

    @FXML
    private Slider thresholdCanny;

    private Image image1, image2, image3;

    private File f;

    private boolean verificarRGB = true;

    @FXML
    public void prewitt(){
        image3 = OpenCVUtils.prewitt(image1);
        atualizaImage3(image3);
    }

    @FXML
    public void laplace(){
        image3 = OpenCVUtils.laplace(image1);
        atualizaImage3(image3);
    }

    @FXML
    public void canny(){
        image3 = OpenCVUtils.canny(image1, thresholdCanny.getValue());
        atualizaImage3(image3);
    }

    @FXML
    public void erosao(){
        String path = image1.impl_getUrl();
        String resultado = OpenCVUtils.erosao(path);
        File file = new File(resultado);
        atualizaImage3(new Image(file.toURI().toString()));
    }

    @FXML
    public void dilatacao(){
        String path = image1.impl_getUrl();
        String resultado = OpenCVUtils.dilatacao(path);
        File file = new File(resultado);
        atualizaImage3(new Image(file.toURI().toString()));
    }

    @FXML
    public void prova1Questao1() throws IOException {
        image3 = Prova1.questao1(image1, Integer.valueOf(colunas.getText()));
        atualizaImage3(image3);
    }

    @FXML
    public void prova1Questao2(MouseEvent event) {
        image3 = Prova1.questao2(image1, event);
        atualizaImage3(image3);
    }

    @FXML
    public void prova1Questao3() {
        String resultado = Prova1.questao3V2(image1);
        resultadoProva1Questao3.setText(resultado);
    }

    @FXML
    public void histograma() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/histograma.fxml"));
        BorderPane page = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Histograma");
        //dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(Main.stage);
        dialogStage.initStyle(StageStyle.UTILITY);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        HistogramaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        dialogStage.show();
        if(image1 != null) {
            ImageProcess.getGrafico(image1, controller.getGrafico1());
        }
        if(image2 != null) {
            ImageProcess.getGrafico(image2, controller.getGrafico2());
        }
        if(image3 != null) {
            ImageProcess.getGrafico(image3, controller.getGrafico3());
        }
    }


    @FXML
    public void subtracao() {
        image3 = ImageProcess.adicaoSubtracao(image1, image2, sliderPercentual.getValue(), ActionMedia.SUBTRACAO);
        atualizaImage3(image3);
    }

    @FXML
    public void adicao() {
        image3 = ImageProcess.adicaoSubtracao(image1, image2, sliderPercentual.getValue(), ActionMedia.ADICAO);
        atualizaImage3(image3);
    }

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
    public void atividade1(){
        image3 = ImageProcess.atividade1(image1, Integer.valueOf(distancia.getText()));
        atualizaImage3(image3);
    }


    @FXML
    public void desafio1(){
        image3 = ImageProcess.desafio1(image1);
        atualizaImage3(image3);
    }

    @FXML
    public void desafio2(){
        image3 = ImageProcess.desafio2(image1);
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
    public void pressed(MouseEvent event) {
        if (event.getTarget() instanceof ImageView) {
            ImageView imageView = (ImageView) event.getTarget();
            Image image = imageView.getImage();

            if (image != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Prova1.pressed(image, x, y);
            }
        }
    }

    @FXML
    public void released(MouseEvent event) {
        if (event.getTarget() instanceof ImageView) {
            ImageView imageView = (ImageView) event.getTarget();
            Image image = imageView.getImage();

            if (image != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Prova1.released(image, x, y);
            }
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
                "Imagens", "*.jpg", "*.JPG", "*.JPEG", "*.jpeg",
                "*.png", "*.PNG", "*.gif", "*.GIF",
                "*.bmp", "*.BMP"));
        fileChooser.setInitialDirectory(new File(
                "C:\\Users\\maico\\Desktop"));
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
