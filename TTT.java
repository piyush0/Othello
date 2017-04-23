package lec20;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.rmi.CORBA.Tie;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TTT extends JFrame implements ActionListener {

    public static final int BOARD_SIZE = 3;
    public static final int X_WINS = 1;
    public static final int Z_WINS = 2;
    public static final int INCOMPLETE = 3;
    public static final int TIE = 4;
    private boolean CrossTurn;
    public JButton[][] buttons;

    private static int x_score;
    private static int o_score;

    public TTT() {

        super.setVisible(true);
        super.setTitle("Tic Tac Toe");
        super.setSize(600, 600);
        super.setResizable(false);


        this.buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        GridLayout layout = new GridLayout(3, 3);
        super.setLayout(layout);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Comic Sans MS", 1, 100));
//                button.addActionListener(this);

                button.addActionListener(new MyOnClickListener(row, col));

                this.buttons[row][col] = button;


                super.add(button);
            }
        }

    }

    public void startGame() {
        this.CrossTurn = false;
    }

    public void actionPerformed(ActionEvent arg0) {

        JButton button = (JButton) arg0.getSource();


        if (button.getText().equals("")) {
            this.makeMove(button);
            int gameStatus = this.getGameStatus();

            if (gameStatus == INCOMPLETE) {
                this.CrossTurn = !this.CrossTurn;
            } else {
                this.declareWinner(gameStatus);
                super.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Move");
        }

    }

    public void makeMove(JButton button) {
        if (this.CrossTurn) {
            button.setForeground(Color.RED);
            button.setText("X");

        } else {

            button.setText("0");
        }
    }

    public int getGameStatus() {
        String text1 = "";
        String text2 = "";

        int row = 0;
        int col = 0;

        for (row = 0; row < BOARD_SIZE; row++) {

            col = 0;

            while (col < BOARD_SIZE - 1) {
                text1 = this.buttons[row][col].getText();
                text2 = this.buttons[row][col + 1].getText();

                if (text1.length() == 0 || !text1.equals(text2)) {
                    break;
                }

                col++;
            }

            if (col == BOARD_SIZE - 1) {
                return text1.equals("X") ? X_WINS : Z_WINS;
            }
        }

        row = 0;
        col = 0;

        text1 = "";
        text2 = "";
        for (col = 0; col < BOARD_SIZE; col++) {

            row = 0;

            while (row < BOARD_SIZE - 1) {
                text1 = this.buttons[row][col].getText();
                text2 = this.buttons[row + 1][col].getText();
                if (text1.length() == 0 || !text1.equals(text2)) {
                    break;
                }
                row++;
            }

            if (row == BOARD_SIZE - 1) {
                return text1.equals("X") ? X_WINS : Z_WINS;
            }
        }

        // diagonal 1

        row = 0;
        col = 0;

        while (col < BOARD_SIZE - 1) {
            text1 = this.buttons[row][col].getText();
            text2 = this.buttons[row + 1][col + 1].getText();
            if (text1.length() == 0 || !text1.equals(text2)) {
                break;
            }
            col++;
            row++;
        }

        if (col == BOARD_SIZE - 1) {
            return text1.equals("X") ? X_WINS : Z_WINS;
        }

        // diagonal 2

        row = BOARD_SIZE - 1;
        col = 0;

        while (col < BOARD_SIZE - 1) {
            text1 = this.buttons[row][col].getText();
            text2 = this.buttons[row - 1][col + 1].getText();
            if (text1.length() == 0 || !text1.equals(text2)) {
                break;
            }
            col++;
            row--;
        }

        if (col == BOARD_SIZE - 1) {
            return text1.equals("X") ? X_WINS : Z_WINS;
        }

        // incomplete

        for (row = 0; row < BOARD_SIZE; row++) {
            for (col = 0; col < BOARD_SIZE; col++) {
                if (this.buttons[row][col].getText() == "") {
                    return INCOMPLETE;
                }
            }
        }

        return TIE;
    }

    public void declareWinner(int status) {
        if (status == TIE) {
            JOptionPane.showMessageDialog(null, "Tie");
        } else if (status == X_WINS) {
//			JOptionPane.showMessageDialog(null, "X winner");
            x_score++;
        } else if (status == Z_WINS) {
//			JOptionPane.showMessageDialog(null, "O winner");
            o_score++;
        }

        JOptionPane.showMessageDialog(null, "X score : " + x_score + "\nO score : " + o_score);
    }

    public class MyOnClickListener implements ActionListener {

        int x;
        int y;

        public MyOnClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(x + ", " + y);
        }
    }

}
