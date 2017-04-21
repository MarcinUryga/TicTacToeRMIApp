package Controller;

import Model.GameButtonsModel;
import View.GameButtonsView;
import interfaces.IServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Controller {

    private TicTacToeClientController clientController;
    private IServer server;
    private GameButtonsModel gameButtonsModel;
    private ISenderRecieverForServer p2pController;

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ipField;
    @FXML
    private Button connectButton;
    @FXML
    private TextField typeTheTextField;
    @FXML
    private TextArea textArea;
    @FXML
    private Label yourSignLabel;

    @FXML
    public void initialize(){
        ipField.setText("localhost");
        gameButtonsModel = new GameButtonsModel();
    }
    @FXML
    public void doConnect() throws RemoteException {

        if (connectButton.getText().equals("Connect")){
            if (nameField.getText().length()<2){
                displayAlert("Input your Name", "Please type a name");
                return;
            }

            if (ipField.getText().length()<2){
                displayAlert("Input IP adress", "Please type an IP");
                return;
            }

            try{
                startSettingsForConnection();

            }catch(Exception e){e.printStackTrace();
                displayAlert("Error", "Something's wrong. You cannot connect!");

            }
        }else{
            server.logout(clientController);
            connectButton.setText("Connect");
            Platform.exit();
        }
    }

    @FXML
    public void addListenerForTextArea() throws RemoteException {
        if (connectButton.getText().equals("Connect")){
            displayAlert("Error", "You have to connect firstly!");
            return;
        }

        String textToSend = "["+nameField.getText()+"] " + typeTheTextField.getText();
        p2pController = new SenderRecieverForChat(server, ACTION.Sender, textArea, textToSend);
        typeTheTextField.setText("");
    }

    private void startSettingsForConnection() throws RemoteException, MalformedURLException, NotBoundException {
        clientController = new TicTacToeClientController(nameField.getText(), p2pController);
        clientController.setController(this);

        server=(IServer) Naming.lookup("rmi://"+ipField.getText()+"/myabc");
        server.login(clientController);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(gameButtonsModel.getBlockAccess()){
                    displayAlert("Sorry :(", "To much players on this server. Try later.");
                    Platform.exit();}
            }
        });

        gameButtonsModel.createGamesFileds(gridPane);

        for(int i=0; i<gameButtonsModel.getGameButtons().length;i++){
            new GameButtonsView(gameButtonsModel.getGameButtons()[i]);
        }

        addListenerForButtons();

        clientController.setGameButtonsModel(gameButtonsModel);

        connectButton.setText("Disconnect");

        setYourSignLabel(gameButtonsModel.setSign(server.returnNumberOfUser()));

        Platform.runLater(() -> {
            gameButtonsModel.setSign(yourSignLabel.getText());
        });
    }

    public void addListenerForButtons(){
        for(int i = 0; i < 9; i++){
            Button button = gameButtonsModel.getGameButtons()[i];
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    p2pController = new SenderRecieverForGame(server, ACTION.Sender,
                            button.getId(), gameButtonsModel, null, null);
                }
            });
        }
    }

    public void setYourSignLabel(String sign){
        Platform.runLater( () -> yourSignLabel.setText(sign));
    }

    public void displayAlert(String title, String text){
        textArea.setText(text);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public IServer getServer() {
        return server;
    }
    public TextArea getTextArea() {
        return textArea;
    }

}