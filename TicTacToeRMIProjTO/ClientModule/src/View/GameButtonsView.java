package View;


import javafx.scene.control.Button;

public class GameButtonsView {

    Button button;

    public GameButtonsView(Button button){
        this.button = button;
        button.setStyle("-fx-background-color: #282828 ; -fx-border-color: #004d80;");

    }

    public void fillButtonText(String sign){
        if(sign.equals("O"))
            button.setStyle("-fx-font-size: 60px; -fx-background-color: #282828 ; -fx-border-color: #004d80; -fx-text-fill: #ff0000 ;");
        else{
            button.setStyle("-fx-font-size: 60px; -fx-background-color: #282828 ; -fx-border-color: #004d80; -fx-text-fill: #00e600;");
        }
    }

}

