package startInterface;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class Controller implements Initializable {


    public TextArea textBox;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    @FXML
    private Button startButton;
    @FXML
    private ChoiceBox bufferChoice1;
    @FXML
    private ChoiceBox bufferChoice2;
    @FXML
    private ChoiceBox fileChoice;
    @FXML
    private BarChart rwChart;

    private   XYChart.Series<String,Double> write = new XYChart.Series<>();
    private   XYChart.Series<String,Double> read = new XYChart.Series<>();

    private   ObservableList<XYChart.Series<String,Double>> chartData = FXCollections.observableArrayList();
    private static int i=8;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bufferChoice1.setValue("1 KB");
        bufferChoice2.setValue("1 KB");
        fileChoice.setValue("32 KB");

        textBox.setText("Niset Text sa vad\nCum ar arata scrise!!");
        textBox.setEditable(false);

        rwChart.setLegendVisible(false);
        rwChart.setAnimated(false);
        rwChart.setTitle("Write and Read test");


       /* write.setName("WRITE");
        read.setName("READ");*/




    }


    public void checkValues()
    {
        String[] str =new String[]{"1 KB","2 KB", "4 KB","8 KB","16 KB","32 KB","64 KB","128 KB","256 KB","512 KB","1 MB","2 MB","4 MB"};

        int b1 = bufferChoice1.getSelectionModel().getSelectedIndex();
        int b2 = bufferChoice2.getSelectionModel().getSelectedIndex();
        int f = fileChoice.getSelectionModel().getSelectedIndex();

        if(b1 > b2) bufferChoice2.setValue(str[b1]);
        if((b2 - 5) > f) fileChoice.setValue(str[b2]);

    }



    public  void addToChart(double writeValue, double readValue, String index)
    {


        write.getData().add(new XYChart.Data(index,writeValue));
        read.getData().add(new XYChart.Data(index,readValue));


        chartData.clear();
        chartData.addAll(write,read);


        rwChart.setData(chartData);


    }


    public ChoiceBox getBufferChoice1() {
        return bufferChoice1;
    }

    public ChoiceBox getBufferChoice2() {
        return bufferChoice2;
    }

    public ChoiceBox getFileChoice() {
        return fileChoice;
    }


    public void test(ActionEvent actionEvent) {
        addToChart(6.6,9.7,Integer.toString(i));
        i++;
    }
}
