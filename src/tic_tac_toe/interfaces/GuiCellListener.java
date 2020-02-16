package tic_tac_toe.interfaces;

import tic_tac_toe.layout.Cell;
import java.util.EventListener;


public interface GuiCellListener extends EventListener {
    void writeCell(Cell event);
}
