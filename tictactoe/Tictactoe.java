import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Tictactoe extends Application {

    private Parent createContent() {
        final Pane root = new Pane();
        root.setPrefSize(600, 600);

        return root;
        //Anger storlek på rotbehållaren
    }


    @Override
    public void start(final Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
     //Detta är fönstret, scenen är innehållet, anropar createContent.
    }

    public static void main(final String[] args) {
        launch(args); 
    }   
}