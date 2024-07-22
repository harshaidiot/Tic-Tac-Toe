import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean xTurn = true;
    private JLabel statusLabel;
    private JButton resetButton;

    public Main() {
        setTitle("Tic-Tac-Toe");
        setSize(300, 400); // Adjusted size to fit the status label and reset button
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeGamePanel();
        initializeControlPanel();
    }

    private void initializeGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                gamePanel.add(buttons[row][col]);
            }
        }

        add(gamePanel, BorderLayout.CENTER);
    }

    private void initializeControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        statusLabel = new JLabel("X's turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Increased font size
        controlPanel.add(statusLabel, BorderLayout.NORTH);

        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100, 50)); // Increased button size
        resetButton.setFont(new Font("Arial", Font.BOLD, 20)); // Increased font size
        resetButton.addActionListener(e -> resetBoard());
        controlPanel.add(resetButton, BorderLayout.SOUTH);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("") && !checkWinner()) {
                buttons[row][col].setText(xTurn ? "X" : "O");
                if (checkWinner()) {
                    statusLabel.setText((xTurn ? "X" : "O") + " wins!");
                    disableAllButtons();
                } else if (isBoardFull()) {
                    statusLabel.setText("It's a draw!");
                } else {
                    xTurn = !xTurn;
                    statusLabel.setText((xTurn ? "X's" : "O's") + " turn");
                }
            }
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {     //This is for rows
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().equals("")) {
                return true;
            }
             // This is for columns
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&   
                    buttons[1][i].getText().equals(buttons[2][i].getText()) &&
                    !buttons[0][i].getText().equals("")) {
                return true;
            }
        }
        // This will check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().equals("")) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().equals("")) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        xTurn = true;
        statusLabel.setText("X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main(); // we will create instance here
            game.setVisible(true);
        });
    }
}