package startInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartInterface extends Application {

    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Holy Grail");
        window.resizableProperty().setValue(false);





        window.setScene(new Scene(root));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
