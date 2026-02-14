package game;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Crosses extends JFrame {

    private static final String EMPTY = "";
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    private final JButton[] buttons;
    private final String[] gameField;
    private final JLabel infoLabel;

    private String turn = PLAYER_X;

    public Crosses() {

        // Initialize fields
        buttons = new JButton[9];
        gameField = new String[9];
        infoLabel = new JLabel("Player X's turn", SwingConstants.CENTER);

        // Frame setup
        setTitle("Tic-Tac-Toe");
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Info label styling
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(infoLabel, BorderLayout.NORTH);

        // Game panel
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        add(gamePanel, BorderLayout.CENTER);

        Arrays.fill(gameField, EMPTY);

        // Create buttons
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));

            final int index = i;
            buttons[i].addActionListener(e -> handleMove(index));

            gamePanel.add(buttons[i]);
        }

        // Restart button
        JButton restartButton = new JButton("Restart Game");
        restartButton.addActionListener(e -> resetGame());
        add(restartButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleMove(int index) {

        if (!EMPTY.equals(gameField[index])) {
            infoLabel.setText("Cell already occupied!");
            return;
        }

        if (checkWinner() != null) {
            infoLabel.setText("Game is over! Press Restart.");
            return;
        }

        // Make move
        gameField[index] = turn;
        buttons[index].setText(turn);

        String winner = checkWinner();

        if (winner != null) {
            if ("draw".equals(winner)) {
                infoLabel.setText("Draw!");
            } else {
                infoLabel.setText("Player " + winner + " won!");
            }
            return;
        }

        // Switch turn
        turn = turn.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
        infoLabel.setText("Player " + turn + "'s turn");
    }

    private String checkWinner() {

        int[][] winCombos = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winCombos) {
            if (!EMPTY.equals(gameField[combo[0]]) &&
                    gameField[combo[0]].equals(gameField[combo[1]]) &&
                    gameField[combo[1]].equals(gameField[combo[2]])) {

                return gameField[combo[0]];
            }
        }

        if (!Arrays.asList(gameField).contains(EMPTY)) {
            return "draw";
        }

        return null;
    }

    private void resetGame() {

        Arrays.fill(gameField, EMPTY);

        for (JButton button : buttons) {
            button.setText("");
        }

        turn = PLAYER_X;
        infoLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Crosses::new);
    }
}
