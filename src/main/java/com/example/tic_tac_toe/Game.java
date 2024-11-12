package com.example.tic_tac_toe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Game {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label resultLabel;

    @FXML
    private Button newgame;

    private boolean playerXTurn = true;
    private Button[][] board = new Button[3][3];

    // Initialize the board and set up the game
    @FXML
    public void initialize() {
        // Load buttons into the board array and set action listeners
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = (Button) getNodeByRowColumnIndex(row, col, gridPane);
                board[row][col] = button;
                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleMove(button, finalRow, finalCol));
            }
        }
    }

    // Handle a player's move
    private void handleMove(Button button, int row, int col) {
        // If the cell is already occupied, ignore the click
        if (!button.getText().isEmpty()) {
            return;
        }

        // Set text based on the current player's turn
        button.setText(playerXTurn ? "X" : "O");
        playerXTurn = !playerXTurn;

        // Check if there's a winner or if it's a draw
        if (checkWin(row, col)) {
            resultLabel.setText((button.getText() + " wins!"));
            disableBoard();
        } else if (isBoardFull()) {
            resultLabel.setText("It's a draw!");
        }
    }

    // Check if the current player won the game
    private boolean checkWin(int row, int col) {
        String symbol = board[row][col].getText();

        // Check the row
        if (board[row][0].getText().equals(symbol) && board[row][1].getText().equals(symbol) && board[row][2].getText().equals(symbol)) {
            return true;
        }

        // Check the column
        if (board[0][col].getText().equals(symbol) && board[1][col].getText().equals(symbol) && board[2][col].getText().equals(symbol)) {
            return true;
        }

        // Check the diagonals
        if (board[0][0].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][2].getText().equals(symbol)) {
            return true;
        }
        if (board[0][2].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    // Check if all cells are filled
    private boolean isBoardFull() {
        for (Button[] row : board) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Disable all buttons on the board (when game is over)
    private void disableBoard() {
        for (Button[] row : board) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    // Helper method to get a button by row and column index in the GridPane
    private Button getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (var node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);

            // Default to row/column 0 if null, as GridPane does when unspecified
            if (nodeRow == null) nodeRow = 0;
            if (nodeCol == null) nodeCol = 0;

            if (nodeRow == row && nodeCol == column) {
                return (Button) node;
            }
        }
        return null;
    }
    @FXML
    public  void Start_newgame(ActionEvent actionEvent)
    {


    }

}
