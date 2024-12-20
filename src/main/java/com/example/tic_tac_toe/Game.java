package com.example.tic_tac_toe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

import static java.lang.Thread.sleep;

public class Game {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label resultLabel;

    @FXML
    private Button newgame;

    @FXML
    private Line winline;

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

        // Check row
        if (board[row][0].getText().equals(symbol) && board[row][1].getText().equals(symbol) && board[row][2].getText().equals(symbol)) {
            drawWinningLine(row, 0+1, row, 2+1); // Draw horizontal line
            return true;
        }

        // Check column
        if (board[0][col].getText().equals(symbol) && board[1][col].getText().equals(symbol) && board[2][col].getText().equals(symbol)) {
            drawWinningLine(0+1, col, 2+1, col); // Draw vertical line
            return true;
        }

        // Check main diagonal
        if (board[0][0].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][2].getText().equals(symbol)) {
            drawWinningLine(0, 0, 2, 2); // Draw diagonal line
            return true;
        }

        // Check anti-diagonal
        if (board[0][2].getText().equals(symbol) && board[1][1].getText().equals(symbol) && board[2][0].getText().equals(symbol)) {
            drawWinningLine(0, 2, 2, 0); // Draw anti-diagonal line
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
    public void Start_newgame(ActionEvent actionEvent) throws InterruptedException {
        if (actionEvent.getSource() == newgame) {
            for (Button[] row : board) {
                for (Button button : row) {
                    button.setText("");
                    button.setDisable(false);
                }
            }
            playerXTurn = true;

            // Hide the winning line
            winline.setVisible(false);

            // Clear result text
            resultLabel.setText("New Game");
            sleep(1200);
            resultLabel.setText("Tic-Tac-Toe");

        }
    }
    private void drawWinningLine(int startRow, int startCol, int endRow, int endCol) {
        Platform.runLater(() -> {
            double cellWidth = gridPane.getWidth() / 3;
            double cellHeight = gridPane.getHeight() / 3;

            // Check if it is a horizontal line
            if (startRow == endRow) {
                winline.setStartX(startCol * cellWidth);
                winline.setStartY(startRow * cellHeight + cellHeight / 2);
                winline.setEndX((endCol + 1) * cellWidth); // Make the line go till the next column
                winline.setEndY(endRow * cellHeight + cellHeight / 2);
            }
            // Check if it is a vertical line
            else if (startCol == endCol) {
                winline.setStartX(startCol * cellWidth + cellWidth / 2);
                winline.setStartY(startRow * cellHeight);
                winline.setEndX(endCol * cellWidth + cellWidth / 2);
                winline.setEndY((endRow + 1) * cellHeight); // Make the line go till the next row
            }
            // Diagonal lines
            else {
                winline.setStartX(startCol * cellWidth + cellWidth / 2);
                winline.setStartY(startRow * cellHeight + cellHeight / 2);
                winline.setEndX(endCol * cellWidth + cellWidth / 2);
                winline.setEndY(endRow * cellHeight + cellHeight / 2);
            }

            // Make the line visible
            winline.setVisible(true);
        });
    }



}
