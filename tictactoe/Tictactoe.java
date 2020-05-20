
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tictactoe extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    private Pane root = new Pane();

    private Parent createContent() {
        root.setPrefSize(600, 600);

           //Anger storlek på rotbehållaren

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);
                //Detta skapar nya tiles.

                board[j][i] = tile;
                
            }
        }

        
        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
            //Horisontella kombinationer.
        }

        
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
            //Vertikala kombinationer.
        }

        
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
            //Diagonella kombinationer.
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        //Detta är fönstret, scenen är innehållet, anropar createContent.
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                break;
                /*Kollar statusen på spelet för att se om det går att fortsätta eller inte.
                  Exempelvis kommer den att kolla om combo är klar, om den är det så är playable
                  falskt och en playWinAnimation anropas eftersom att det inte finns någon poäng i att
                  spela vidare efter att en spelare vunnit. 
                */
            }
        }
    }

    private void playWinAnimation(Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());

        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        timeline.play();

        /* En animation som drar linjer mellan de tiles som använts för att få den vinnande
        kombinationen, linjen startar från den första tile som blev klickad i kombinationen
        och drar sedan linjen genom den andra till den sista.
        */
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;
            // Hämtar värdet på tiles, om det är tomt så returnar den false. 
            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());

                    // ger tiles ett värde. 
                   
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();
        //Skapar tom text. 
        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            //Anger egenskaperna för en tile, denna loopas sedan för att skapa flera tiles.
            text.setFont(Font.font(72));
            //Textstorlek

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
           

            setOnMouseClicked(event -> {
                if (!playable)
                    return;
                //Bestämmer att något ska hända när musen klickar. 
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;

                    drawX();
                    turnX = false;
                    checkState();
                    /*Om man vänsterklickar så kommer programmet att skriva ett X i den rutan man klickar, men bara om det är på spelarens tur.
                    Efter att man tryckt en gång så avslutas turen. */

                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;

                    drawO();
                    turnX = true;
                    checkState();
                    //Om man högerklickar så kommer programmet att skriva ett O i den rutan man klickar, men bara om det är på spelarens tur.
                }
            });
        }

        public double getCenterX() {
            return getTranslateX() + 100;
        }

        public double getCenterY() {
            return getTranslateY() + 100;
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText("X");
            //Anger drawX text som X
        }

        private void drawO() {
            text.setText("O");
            //Samma som DrawX fast med O. 
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}