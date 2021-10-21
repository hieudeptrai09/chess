package chess;

import javafx.application.Application;
import javafx.stage.Stage;

public class Chess extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        new PeelPage().start(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
