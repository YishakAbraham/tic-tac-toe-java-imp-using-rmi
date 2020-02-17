package tic_tac_toe.client;

import javafx.scene.control.TextField;
import tic_tac_toe.interfaces.ClientCallback;
import tic_tac_toe.interfaces.ClientCallbackListener;
import tic_tac_toe.interfaces.GuiCellListener;
import tic_tac_toe.interfaces.ServerInterface;
import tic_tac_toe.layout.Cell;

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
            Registry registry = LocateRegistry.getRegistry(textField.getText());
            System.out.println(textField.getText());
            ClientCallback callback = (ClientCallback) remoteService;
            callback.setCellOnClientCallback(this);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(int status, int index) {
        try {
            ServerInterface server = (ServerInterface) remoteService;
            server.sendCell(status, index);
        } catch (RemoteException e) {
            e.printStackTrace();
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
