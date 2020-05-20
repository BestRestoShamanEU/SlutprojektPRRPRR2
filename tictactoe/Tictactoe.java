import javafx.application.Application;
import javafx.stage.Stage;

public class Tictactoe extends Application {



    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); 
    }   
}