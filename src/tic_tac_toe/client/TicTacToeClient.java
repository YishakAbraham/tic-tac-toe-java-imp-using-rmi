package tic_tac_toe.client;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tic_tac_toe.interfaces.ClientCallback;
import tic_tac_toe.interfaces.ClientCallbackListener;
import tic_tac_toe.interfaces.GuiCellListener;
import tic_tac_toe.interfaces.ServerInterface;
import tic_tac_toe.layout.Cell;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TicTacToeClient extends UnicastRemoteObject implements ClientCallbackListener {
    Remote remoteService = null;
    private GuiCellListener guiCellListener;

    public TicTacToeClient(TextField textField) throws RemoteException {
        super();
        try {
            remoteService = Naming.lookup(textField.getText());
            System.out.println(textField.getText());
            ClientCallback callback = (ClientCallback) remoteService;
            callback.setCellOnClientCallback(this);
        } catch (NotBoundException | MalformedURLException e) {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Exception Dialog");
            errorDialog.setHeaderText("Error binding to Server!");
            errorDialog.setContentText("Could not register to the service!");
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String exceptionText = stringWriter.toString();
            Label label = new Label("The exception stacktrace was:");
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            errorDialog.getDialogPane().setExpandableContent(expContent);
            errorDialog.showAndWait();
        }
    }

    public void sendToServer(int status, int index) {
        try {
            ServerInterface server = (ServerInterface) remoteService;
            server.sendCell(status, index);
        } catch (RemoteException e) {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Exception Dialog");
            errorDialog.setHeaderText("Connection Error!");
            errorDialog.setContentText("Could not connect to server");
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String exceptionText = stringWriter.toString();
            Label label = new Label("The exception stacktrace was:");
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            errorDialog.getDialogPane().setExpandableContent(expContent);
            errorDialog.showAndWait();
        }
    }

    @Override
    public void sendToClient(int status, int index) throws RemoteException {
        if (guiCellListener != null) {
            guiCellListener.writeCell(new Cell(this, status, index));
        }
    }

    public void setGuiCellListener(GuiCellListener guiCellListener) {
        this.guiCellListener = guiCellListener;
    }
}
