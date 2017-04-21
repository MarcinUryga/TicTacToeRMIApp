
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TicTacToeMain extends Application {


    public static void main(String[] args) {
        System.out.println("Let's play the game!");
        launch(args);
        TicTacToeMain c = new TicTacToeMain();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("View/TicTacToePanel.fxml"));
        BorderPane borderPane = loader.load();

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
