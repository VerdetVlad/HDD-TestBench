package startInterface;

import bench.HDDBench;
import bench.ParametersSizes;

import bench.ParametersSizes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.scene.layout.Background;
import utilities.AlertBox;
import utilities.StringManagement;

import javax.swing.*;


public class Controller implements Initializable {


    public ChoiceBox fileChoiceTab22;
    public ChoiceBox bufferChoiceTab2;
    public ChoiceBox fileChoiceTab21;
    public BarChart rwChart2;
    public CategoryAxis xAxis2;
    public NumberAxis yAxis2;
    public TextArea textBox2;
    public Button startButton2;
    public CheckBox deleteCheckBox;
    public CheckBox deleteCheckBox2;
    public Button startButton;
    public ChoiceBox nrOfRep2;
    public ChoiceBox nrOfRep1;
    public static MenuItem exitMenu;


    @FXML
    private ChoiceBox bufferChoice1;
    @FXML
    private ChoiceBox bufferChoice2;
    @FXML
    private ChoiceBox fileChoice;
    @FXML
    private BarChart rwChart;
    public TextArea textBox;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;


    private static final int KB_SIZE = 1024; // KB
    private static final int MB_SIZE = 1024 * 1024; // MB
    private static final String[] bufferSizes = new String[]{"1 KB", "2 KB", "4 KB", "8 KB", "16 KB", "32 KB",
            "64 KB", "128 KB", "256 KB", "512 KB",
            "1 MB", "2 MB", "4 MB", "8 MB", "16 MB", "32 MB"};
    private static final String[] fileSizes = new String[]{"32 KB", "64 KB", "128 KB", "256 KB", "512 KB", "1 MB",
            "2 MB", "4 MB", "8 MB", "16 MB", "32 MB", "64 MB",
            "128 MB", "256 MB", "512 MB"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bufferChoice1.setValue("1 KB");
        bufferChoice2.setValue("1 KB");
        fileChoice.setValue("32 KB");

        bufferChoiceTab2.setValue("1 KB");
        fileChoiceTab21.setValue("32 KB");
        fileChoiceTab22.setValue("32 KB");

        nrOfRep1.setValue("1");
        nrOfRep2.setValue("1");


        textBox.setEditable(false);

//        rwChart.setLegendVisible(false);
        rwChart.setAnimated(false);


        rwChart2.setAnimated(false);


        yAxis.setLabel("MB");
        yAxis2.setLabel("MB");

        xAxis.setLabel("Buffer -->");
        xAxis2.setLabel("File -->");

        textBox2.setText("");
        textBox.setText("");

        deleteCheckBox.setSelected(true);
        deleteCheckBox2.setSelected(true);


    }


    public void checkValues() {


        int b1 = bufferChoice1.getSelectionModel().getSelectedIndex();
        int b2 = bufferChoice2.getSelectionModel().getSelectedIndex();
        int f = fileChoice.getSelectionModel().getSelectedIndex();


        if (b1 > b2) bufferChoice2.setValue(bufferSizes[b1]);
        if ((b2 - 5) > f) fileChoice.setValue(bufferSizes[b2]);

        int bTab2 = bufferChoiceTab2.getSelectionModel().getSelectedIndex();
        int fTab1 = fileChoiceTab21.getSelectionModel().getSelectedIndex();
        int fTab2 = fileChoiceTab22.getSelectionModel().getSelectedIndex();


        if ((bTab2 - 5) > fTab1) fileChoiceTab21.setValue(bufferSizes[bTab2]);
        if (fTab1 > fTab2) fileChoiceTab22.setValue(fileSizes[fTab1]);


    }


    public void cancelAction() {

        HDDBench.running = false;
    }


    public void startHandle(ActionEvent actionEvent) {

        int aux = nrOfRep1.getSelectionModel().getSelectedIndex();

        ParametersSizes.repeatPerSize = aux + 1;

        int[] b = new int[2];
        b[0] = bufferChoice1.getSelectionModel().getSelectedIndex();
        b[1] = bufferChoice2.getSelectionModel().getSelectedIndex();

        int[] f = new int[2];
        f[0] = fileChoice.getSelectionModel().getSelectedIndex();

        int indexDif = b[1] - b[0] + 1;

        boolean options[] = new boolean[2];
        options[0] = false;
        options[1] = deleteCheckBox.isSelected();

        HDDBench bench = new HDDBench();
        bench.initialize(b, f, options, indexDif);
        bench.warmUp();
        bench.run();

        String check = bench.getResult();

        if (check == null) return;

        printToInterface1(check, b[0], b[1], f[0]);

    }


    private void printToInterface1(String resStr, int buffIndex1, int buffIndex2, int fileIndex) {
        String[] strArr = new String[30];
        strArr = resStr.split(";");

        Double[] write = new Double[30];
        Double[] read = new Double[30];

        int indexDif = buffIndex2 - buffIndex1 + 1;
        String textBoxPrint = new String("");


        int i;
        for (i = 0; i < indexDif; i++) {
            String a = StringManagement.makeReadAndWrite(strArr[i], bufferSizes[buffIndex1 + i], fileSizes[fileIndex]);

            textBoxPrint += a;

            String[] doubleAux = new String[2];
            doubleAux = strArr[i].split(",");

            write[i] = StringManagement.makeDoubleFromStr(doubleAux[0]);
            read[i] = StringManagement.makeDoubleFromStr(doubleAux[1]);

        }

        String a = StringManagement.makeTotalAverage(strArr[i]);
        textBoxPrint += a;
        this.textBox.setText(textBoxPrint);

        String[] doubleAux = new String[2];
        doubleAux = strArr[i].split(",");
        write[i] = StringManagement.makeDoubleFromStr(doubleAux[0]);
        read[i] = StringManagement.makeDoubleFromStr(doubleAux[1]);


        addToChart(write, read, buffIndex1, indexDif);
    }


    public void addToChart(Double[] writeValue, Double[] readValue, int buffIndex1, int indexDif) {

        XYChart.Series<String, Double> write = new XYChart.Series<>();
        XYChart.Series<String, Double> read = new XYChart.Series<>();
        write.setName("WRITE");
        read.setName("READ");

        for (int j = 0; j < indexDif; j++) {
            write.getData().add(new XYChart.Data(bufferSizes[buffIndex1 + j], writeValue[j]));
            read.getData().add(new XYChart.Data(bufferSizes[buffIndex1 + j], readValue[j]));
        }

        write.getData().add(new XYChart.Data("AVR", writeValue[indexDif]));
        read.getData().add(new XYChart.Data("AVR", readValue[indexDif]));


        rwChart.getData().clear();
        rwChart.layout();
        rwChart.getData().addAll(FXCollections.observableArrayList(write, read));


    }


    public void startHandle2(ActionEvent actionEvent) {

        int aux = nrOfRep2.getSelectionModel().getSelectedIndex();
        ParametersSizes.repeatPerSize = aux + 1;


        int[] b = new int[2];
        b[0] = bufferChoiceTab2.getSelectionModel().getSelectedIndex();


        int[] f = new int[2];
        f[0] = fileChoiceTab21.getSelectionModel().getSelectedIndex();
        f[1] = fileChoiceTab22.getSelectionModel().getSelectedIndex();

        int indexDif = f[1] - f[0] + 1;

        boolean options[] = new boolean[2];
        options[0] = true;
        options[1] = deleteCheckBox2.isSelected();

        HDDBench bench = new HDDBench();
        bench.initialize(b, f, options, indexDif);
        bench.warmUp();
        bench.run();


        String check = bench.getResult();

        if (check == null) return;

        printToInterface2(check, f[0], f[1], b[0]);


    }


    private void printToInterface2(String resStr, int fileIndex1, int fileIndex2, int buffIndex) {
        String[] strArr = new String[30];
        strArr = resStr.split(";");

        Double[] write = new Double[30];
        Double[] read = new Double[30];

        int indexDif = fileIndex2 - fileIndex1 + 1;
        String textBoxPrint = new String("");


        int i;
        for (i = 0; i < indexDif; i++) {
            String a = StringManagement.makeReadAndWrite(strArr[i], bufferSizes[buffIndex], fileSizes[fileIndex1 + i]);

            textBoxPrint += a;

            String[] doubleAux = new String[2];
            doubleAux = strArr[i].split(",");

            write[i] = StringManagement.makeDoubleFromStr(doubleAux[0]);
            read[i] = StringManagement.makeDoubleFromStr(doubleAux[1]);

        }

        String a = StringManagement.makeTotalAverage(strArr[i]);
        textBoxPrint += a;
        this.textBox2.setText(textBoxPrint);

        String[] doubleAux = new String[2];
        doubleAux = strArr[i].split(",");

        write[i] = StringManagement.makeDoubleFromStr(doubleAux[0]);
        read[i] = StringManagement.makeDoubleFromStr(doubleAux[1]);


        addToChart2(write, read, fileIndex1, indexDif);
    }


    public void addToChart2(Double[] writeValue, Double[] readValue, int fileIndex, int indexDif) {

        XYChart.Series<String, Double> write = new XYChart.Series<>();
        XYChart.Series<String, Double> read = new XYChart.Series<>();
        write.setName("WRITE");
        read.setName("READ");

        for (int j = 0; j < indexDif; j++) {
            write.getData().add(new XYChart.Data(fileSizes[fileIndex + j], writeValue[j]));
            read.getData().add(new XYChart.Data(fileSizes[fileIndex + j], readValue[j]));
        }


        write.getData().add(new XYChart.Data("AVR", writeValue[indexDif]));
        read.getData().add(new XYChart.Data("AVR", readValue[indexDif]));

        rwChart2.getData().removeAll();
        rwChart2.setData(FXCollections.observableArrayList(write, read));


    }

    public void saveTextBox(ActionEvent actionEvent) {
        File saveDir = new File(System.getProperty("user.dir") + "//saveDir");
        saveDir.mkdir();

        if (!textBox.getText().isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
            LocalDateTime now = LocalDateTime.now();

            try {
                File saveFile = new File(saveDir + "//Fixed_File " + dtf.format(now) + ".txt");
                saveFile.createNewFile();

                FileWriter myWriter = new FileWriter(saveFile);
                myWriter.write(textBox.getText());
                myWriter.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (!textBox2.getText().isEmpty()) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
            LocalDateTime now = LocalDateTime.now();


            try {
                File saveFile = new File(saveDir + "//Fixed_Buffer " + dtf.format(now) + ".txt");
                saveFile.createNewFile();

                FileWriter myWriter = new FileWriter(saveFile);
                myWriter.write(textBox2.getText());
                myWriter.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        StartInterface.window.close();
    }


    public void aboutMenuAction(ActionEvent actionEvent) {
        AlertBox.display("About",
                "Ce vreti voi.");
    }


}
