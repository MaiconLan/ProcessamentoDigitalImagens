package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

public class HistogramaController {

    private Stage dialogStage;

    @FXML
    private BarChart<String, Number> grafico1;

    @FXML
    private BarChart<String, Number> grafico2;

    @FXML
    private BarChart<String, Number> grafico3;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void fecharModal(){
        if (dialogStage != null)
            dialogStage.close();
    }

    public BarChart<String, Number> getGrafico1() {
        return grafico1;
    }

    public void setGrafico1(BarChart<String, Number> grafico1) {
        this.grafico1 = grafico1;
    }

    public BarChart<String, Number> getGrafico2() {
        return grafico2;
    }

    public void setGrafico2(BarChart<String, Number> grafico2) {
        this.grafico2 = grafico2;
    }

    public BarChart<String, Number> getGrafico3() {
        return grafico3;
    }

    public void setGrafico3(BarChart<String, Number> grafico3) {
        this.grafico3 = grafico3;
    }
}
