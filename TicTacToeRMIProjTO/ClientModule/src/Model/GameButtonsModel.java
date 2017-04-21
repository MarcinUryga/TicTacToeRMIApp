package Model;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameButtonsModel {

    private Button[] gameButtons = new Button[9];
    private String sign;
    private Boolean blockFirstPlayer = false;
    private Boolean blockAccess;
    private int fieldsCounter;

    public GameButtonsModel() {
        this.blockAccess = false;
    }

    //setters
    public Button[] getGameButtons() {
        return gameButtons;
    }
    public String getSign() {
        return sign;
    }
    public Boolean getBlockFirstPlayer() {
        return blockFirstPlayer;
    }
    public Boolean getBlockAccess(){ return blockAccess; }

    //getters
    public void setSign(String sign){
        Platform.runLater(() ->this.sign = sign);
    }
    public void setBlockFirstPlayer(Boolean blockFirstPlayer) {
        this.blockFirstPlayer = blockFirstPlayer;
    }
    public void setBlockAccess(Boolean blockAccess) {
        this.blockAccess = blockAccess;
    }


    //methods
    public void createGamesFileds(GridPane gridPane){
        for(int i = 0; i < 9; i++){
            gameButtons[i] = new Button();
            gameButtons[i].setId(i+"");
            gameButtons[i].setPrefSize(gridPane.getPrefWidth(), gridPane.getPrefHeight());

            gridPane.addRow(i/3, gameButtons[i]);
        }
    }
    public String setSign(int whichPlayer){
        this.sign = "X";
        if(whichPlayer%2==1)
            this.sign = "O";

        return sign;
    }
    public Boolean blockTurn(String sign, Boolean blockFirstPlayer){
        if((sign.equals("X") && blockFirstPlayer) || (sign.equals("O") && !blockFirstPlayer))
            return true;

        return false;
    }

    public void checkFields(){
        fieldsCounter = 0;
        for(int i = 0; i<gameButtons.length; i++){
            if(!gameButtons[i].getText().equals(""))
                fieldsCounter++;
        }
    }

    public Boolean Draw(){
        checkFields();
        if(fieldsCounter==9)
            return true;
        return false;
    }

    public Boolean judge(String sign){

        //Horizontal
        if((gameButtons[0].getText().equals(gameButtons[1].getText()) && gameButtons[1].getText().equals(gameButtons[2].getText()) && gameButtons[2].getText().equals(sign))
                || (gameButtons[3].getText().equals(gameButtons[4].getText()) && gameButtons[4].getText().equals(gameButtons[5].getText()) && gameButtons[5].getText().equals(sign))
                || (gameButtons[6].getText().equals(gameButtons[7].getText()) && gameButtons[7].getText().equals(gameButtons[8].getText()) && gameButtons[8].getText().equals(sign))
                //Vertical
                || (gameButtons[0].getText().equals(gameButtons[3].getText()) && gameButtons[3].getText().equals(gameButtons[6].getText()) && gameButtons[6].getText().equals(sign))
                || (gameButtons[1].getText().equals(gameButtons[4].getText()) && gameButtons[4].getText().equals(gameButtons[7].getText()) && gameButtons[7].getText().equals(sign))
                || (gameButtons[2].getText().equals(gameButtons[5].getText()) && gameButtons[5].getText().equals(gameButtons[8].getText()) && gameButtons[8].getText().equals(sign))
                //diagonally
                || (gameButtons[0].getText().equals(gameButtons[4].getText()) && gameButtons[4].getText().equals(gameButtons[8].getText()) && gameButtons[8].getText().equals(sign))
                || (gameButtons[2].getText().equals(gameButtons[4].getText()) && gameButtons[4].getText().equals(gameButtons[6].getText()) && gameButtons[6].getText().equals(sign))){

            return true;
        }

        return false;
    }

    public String setFinalText(String winnerSign){
        String text = "YOU LOSE!";
        if(sign.equals(winnerSign))
            text = "YOU WON!";

        return text;
    }

    public void resetButtons(){
        for(int i=0;i<gameButtons.length;i++){
            gameButtons[i].setText("");
        }
    }
}