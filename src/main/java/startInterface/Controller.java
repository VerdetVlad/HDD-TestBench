package startInterface;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    public Button startButton;
    public ChoiceBox bufferChoice1;
    public ChoiceBox bufferChoice2;
    public ChoiceBox fileChoice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bufferChoice1.setValue("512 B");
        bufferChoice2.setValue("512 B");
        fileChoice.setValue("32 KB");

    }



    public void checkValues()
    {
        String[] str =new String[]{"512 B", "1 KB","2 KB", "4 KB","8 KB","16 KB","32 KB","64 KB","128 KB","256 KB","512 KB","1 MB","2 MB","4 MB"};

        int b1 = bufferChoice1.getSelectionModel().getSelectedIndex();
        int b2 = bufferChoice2.getSelectionModel().getSelectedIndex();
        int f = fileChoice.getSelectionModel().getSelectedIndex();

        if(b1 > b2) bufferChoice2.setValue(str[b1]);
        if((b2 - 6) > f) fileChoice.setValue(str[b2]);

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
}
