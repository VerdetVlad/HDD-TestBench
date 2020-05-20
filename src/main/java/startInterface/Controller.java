package startInterface;

import bench.HDDBench;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import utilities.StringManagement;


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


    private   XYChart.Series<String,Double> write = new XYChart.Series<>();
    private   XYChart.Series<String,Double> read = new XYChart.Series<>();

    private   XYChart.Series<String,Double> write2 = new XYChart.Series<>();
    private   XYChart.Series<String,Double> read2 = new XYChart.Series<>();

    private static final int KB_SIZE = 1024; // KB
    private static final int MB_SIZE = 1024 * 1024; // MB
    private static final String[] bufferSizes = new String[]{"1 KB","2 KB", "4 KB","8 KB","16 KB","32 KB",
                                                            "64 KB","128 KB","256 KB","512 KB","1 MB"};
    private static final String[] fileSizes = new String[]{"32 KB","64 KB","128 KB","256 KB","512 KB","1 MB",
                                                            "2 MB","4 MB","8 MB","16 MB","32 MB","64 MB"};



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bufferChoice1.setValue("1 KB");
        bufferChoice2.setValue("1 KB");
        fileChoice.setValue("32 KB");

        bufferChoiceTab2.setValue("1 KB");
        fileChoiceTab21.setValue("32 KB");
        fileChoiceTab22.setValue("32 KB");


        textBox.setText("Niset Text sa vad\nCum ar arata scrise!!");
        textBox.setEditable(false);

//        rwChart.setLegendVisible(false);
        rwChart.setAnimated(false);
        rwChart.setTitle("Write and Read test");

        rwChart2.setAnimated(false);
        rwChart2.setTitle("Write and Read test");


        write.setName("WRITE");
        read.setName("READ");

        write2.setName("WRITE");
        read2.setName("READ");



    }


    public void checkValues()
    {
        String[] str =new String[]{"1 KB","2 KB", "4 KB","8 KB","16 KB","32 KB","64 KB","128 KB","256 KB","512 KB","1 MB","2 MB","4 MB"};

        int b1 = bufferChoice1.getSelectionModel().getSelectedIndex();
        int b2 = bufferChoice2.getSelectionModel().getSelectedIndex();
        int f = fileChoice.getSelectionModel().getSelectedIndex();


        if(b1 > b2) bufferChoice2.setValue(bufferSizes[b1]);
        if((b2 - 5) > f) fileChoice.setValue(bufferSizes[b2]);

        int bTab2 = bufferChoiceTab2.getSelectionModel().getSelectedIndex();
        int fTab1 = fileChoiceTab21.getSelectionModel().getSelectedIndex();
        int fTab2 = fileChoiceTab22.getSelectionModel().getSelectedIndex();


        if((bTab2 - 5) > fTab1) fileChoiceTab21.setValue(bufferSizes[bTab2]);
        if(fTab1 > fTab2) fileChoiceTab22.setValue(fileSizes[fTab1]);


    }



    public  void addToChart(double writeValue, double readValue, String index)
    {

        for(int j=0;j<5;j++){
        write.getData().add(new XYChart.Data(index+j, writeValue+j));
        read.getData().add(new XYChart.Data(index+j, readValue+j));
       }


        rwChart.getData().removeAll();
        rwChart.setData(FXCollections.observableArrayList(write,read));


    }

    public  void addToChart2(double writeValue, double readValue, String index)
    {

        for(int j=0;j<5;j++){
            write2.getData().add(new XYChart.Data(index+j, writeValue+j));
            read2.getData().add(new XYChart.Data(index+j, readValue+j));
        }


        rwChart2.getData().removeAll();
        rwChart2.setData(FXCollections.observableArrayList(write2,read2));


    }



    public void startHandle(ActionEvent actionEvent) {

        addToChart(12,21,"1");
        int[] b = new int[2];
        b[0] = bufferChoice1.getSelectionModel().getSelectedIndex();
        b[1] = bufferChoice2.getSelectionModel().getSelectedIndex();

        int[] f = new int[2];
        f[0] = fileChoice.getSelectionModel().getSelectedIndex();

        int indexDif = b[1] - b[0]+1;

        boolean options[] = new boolean[2];
        options[0]=false;
        options[1]=deleteCheckBox.isSelected();

        HDDBench bench = new HDDBench();
        bench.initialize(b,f,options,indexDif);
        bench.run();
        System.out.println(bench.getResult());

    }



    public void startHandle2(ActionEvent actionEvent) {

        addToChart2(12,21,"1");

        int[] b = new int[2];
        b[0] = bufferChoiceTab2.getSelectionModel().getSelectedIndex();


        int[] f = new int[2];
        f[0] = fileChoiceTab21.getSelectionModel().getSelectedIndex();
        f[1] = fileChoiceTab22.getSelectionModel().getSelectedIndex();

        int indexDif = f[1] - f[0]+1;

        boolean options[] = new boolean[2];
        options[0]=true;
        options[1]=deleteCheckBox2.isSelected();

        HDDBench bench = new HDDBench();
        bench.initialize(b,f,options,indexDif);
        bench.run();
        System.out.println(bench.getResult());


    }


    private static void printTest1(String resStr, int buffIndex1, int buffIndex2, int fileIndex)
    {
        String[] strArr = new String[30];
        strArr= resStr.split(";");
        int indexDif = buffIndex2 - buffIndex1+1;

        int i ;
        for (i=0; i <indexDif ; i++)
        {
            
        }

    }



}
