/**
 * Created by piyush0 on 22/07/17.
 */


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Othelo extends JFrame implements ActionListener {

    public JButton[][] buttons;
    private boolean blackTurn;

    public Othelo() {

        this.blackTurn = true;
        this.buttons = new JButton[8][8];
        super.setVisible(true);
        super.setResizable(false);
        super.setSize(600, 600);
        GridLayout layout = new GridLayout(8, 8);
        super.setTitle("Othelo");
        super.setLayout(layout);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                if (row == 3 && col == 3) {
                    JButton button = new JButton();
                    button.setBackground(Color.BLACK);
                    button.setOpaque(true);
                    button.setBorder(new LineBorder(Color.black,1,true));
                    button.addActionListener(this);
                    button.setText(" ");
                    super.add(button);
                    this.buttons[row][col] = button;
                } else if (row == 3 && col == 4) {
                    JButton button = new JButton();
                    button.setBackground(Color.WHITE);
                    button.setOpaque(true);
                    button.setBorder(new LineBorder(Color.black,1,true));
                    button.addActionListener(this);
                    button.setText(" ");
                    super.add(button);
                    this.buttons[row][col] = button;
                } else if (row == 4 && col == 4) {
                    JButton button = new JButton();
                    button.setBackground(Color.BLACK);
                    button.setOpaque(true);
                    button.setBorder(new LineBorder(Color.black,1,true));
                    button.addActionListener(this);
                    button.setText(" ");
                    super.add(button);
                    this.buttons[row][col] = button;
                } else if (row == 4 && col == 3) {
                    JButton button = new JButton();
                    button.setBackground(Color.WHITE);
                    button.setOpaque(true);
                    button.setBorder(new LineBorder(Color.black,1,true));
                    button.addActionListener(this);
                    button.setText(" ");
                    super.add(button);
                    this.buttons[row][col] = button;
                } else {

                    JButton button = new JButton();
                    button.addActionListener(this);
                    button.setBackground(Color.pink);
                    button.setOpaque(true);
                    button.setBorder(new LineBorder(Color.black,1,true));
                    super.add(button);
                    button.setText(" ");
                    this.buttons[row][col] = button;

                }
            }

        }
        start(true);
    }

    public void actionPerformed(ActionEvent arg0) {
        JButton button = (JButton) arg0.getSource();

        if (button.getBackground().equals(Color.BLACK) || button.getBackground().equals(Color.WHITE)
                || button.getBackground().equals(Color.pink)) {
            JOptionPane.showMessageDialog(null, "Invalid move.");
        } else {

            this.convert(button);
            this.blackTurn = !this.blackTurn;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.buttons[i][j].getBackground().equals(Color.GREEN)) {
                        this.buttons[i][j].setBackground(Color.pink);
                    }
                }
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.buttons[i][j].getBackground().equals(Color.ORANGE)) {
                        this.buttons[i][j].setBackground(Color.pink);
                    }
                }
            }

            this.showPossibleMoves(this.blackTurn);

            if (this.returnPossibleMoves(this.blackTurn) == 0) {
                this.blackTurn = !this.blackTurn;
                this.showPossibleMoves(this.blackTurn);
                if (this.returnPossibleMoves(this.blackTurn) == 0) {
                    this.declareWinner();
                    super.dispose();
                }
            }

        }
    }

    private void declareWinner() {
        int blackTiles = 0;
        int whiteTiles = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.buttons[i][j].getBackground().equals(Color.BLACK)) {
                    blackTiles++;
                } else if (this.buttons[i][j].getBackground().equals(Color.WHITE)) {
                    whiteTiles++;
                }
            }
        }

        if (blackTiles > whiteTiles) {
            JOptionPane.showMessageDialog(null, "Black wins");
        } else if (blackTiles < whiteTiles) {
            JOptionPane.showMessageDialog(null, "White wins");
        } else {
            JOptionPane.showMessageDialog(null, "Tie");
        }
    }

    public void start(boolean bool) {
        this.showPossibleMoves(bool);
    }

    private int returnPossibleMoves(boolean blackturn) {

        ArrayList<JButton> retVal = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.buttons[i][j].getBackground().equals(Color.ORANGE)) {
                    retVal.add(this.buttons[i][j]);
                } else if (this.buttons[i][j].getBackground().equals(Color.GREEN)) {
                    retVal.add(this.buttons[i][j]);
                }
            }
        }
        return retVal.size();
    }

    private void convert(JButton button) {

        ArrayList<JButton> retVal = new ArrayList<>();
        retVal.add(button);

        if (this.blackTurn) {
            for (int dir = 1; dir < 9; dir++) {
                ArrayList<JButton> temp = new ArrayList<>();
                JButton tButton = this.returnSomeButton(button, dir);
                int count = 0;
                boolean toAdd = false;
                while (tButton != null && tButton.getBackground().equals(Color.WHITE)) {
                    count++;
                    temp.add(tButton);
                    tButton = this.returnSomeButton(tButton, dir);

                }
                if (count > 0 && tButton != null && tButton.getBackground().equals(Color.BLACK)) {
                    toAdd = true;
                    temp.add(tButton);
                }

                if (toAdd == true) {
                    for (int i = 0; i < temp.size(); i++) {
                        retVal.add(temp.get(i));
                    }
                }

            }
        } else {
            for (int dir = 1; dir < 9; dir++) {
                ArrayList<JButton> temp = new ArrayList<>();
                JButton tButton = this.returnSomeButton(button, dir);
                int count = 0;
                boolean toAdd = false;
                while (tButton != null && tButton.getBackground().equals(Color.BLACK)) {
                    count++;
                    temp.add(tButton);
                    tButton = this.returnSomeButton(tButton, dir);

                }
                if (count > 0 && tButton != null && tButton.getBackground().equals(Color.WHITE)) {
                    toAdd = true;
                    temp.add(tButton);
                }

                if (toAdd == true) {
                    for (int i = 0; i < temp.size(); i++) {
                        retVal.add(temp.get(i));
                    }
                }

            }
        }

        if (this.blackTurn) {
            for (int i = 0; i < retVal.size(); i++) {
                retVal.get(i).setBackground(Color.BLACK);
            }
        } else {
            for (int i = 0; i < retVal.size(); i++) {
                retVal.get(i).setBackground(Color.WHITE);
            }
        }
    }

    private void showPossibleMoves(boolean blackturn) {

        ArrayList<JButton> retval = new ArrayList<>();

        if (blackturn == true) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.buttons[i][j].getBackground().equals(Color.ORANGE)
                            || this.buttons[i][j].getBackground().equals(Color.GREEN)
                            || this.buttons[i][j].getBackground().equals(Color.WHITE)
                            || this.buttons[i][j].getBackground().equals(Color.BLACK)) {
                        continue;
                    }
                    for (int dir = 1; dir < 9; dir++) {
                        int count = 0;
                        JButton thisbutton = this.buttons[i][j];
                        JButton ToBeAdded = thisbutton;
                        thisbutton = this.returnSomeButton(thisbutton, dir);
                        while (thisbutton != null && thisbutton.getBackground().equals(Color.WHITE)) {
                            count++;
                            thisbutton = this.returnSomeButton(thisbutton, dir);
                        }
                        if (count > 0 && thisbutton != null && thisbutton.getBackground().equals(Color.BLACK)) {
                            retval.add(ToBeAdded);
                        }

                    }
                }
            }
        } else {

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.buttons[i][j].getBackground().equals(Color.ORANGE)
                            || this.buttons[i][j].getBackground().equals(Color.GREEN)
                            || this.buttons[i][j].getBackground().equals(Color.BLACK)
                            || this.buttons[i][j].getBackground().equals(Color.WHITE)) {
                        continue;
                    }
                    for (int dir = 1; dir < 9; dir++) {
                        int count = 0;
                        JButton thisbutton = this.buttons[i][j];
                        JButton ToBeAdded = thisbutton;
                        thisbutton = this.returnSomeButton(thisbutton, dir);
                        while (thisbutton != null && thisbutton.getBackground().equals(Color.BLACK)) {
                            count++;
                            thisbutton = this.returnSomeButton(thisbutton, dir);
                        }
                        if (count > 0 && thisbutton != null && thisbutton.getBackground().equals(Color.WHITE)) {
                            retval.add(ToBeAdded);
                        }

                    }
                }
            }
        }

        if (blackturn) {
            for (int i = 0; i < retval.size(); i++) {
                retval.get(i).setBackground(Color.ORANGE);
            }
        } else {
            for (int i = 0; i < retval.size(); i++) {
                retval.get(i).setBackground(Color.GREEN);
            }
        }
    }

    private JButton returnSomeButton(JButton button, int direction) {

        int row = -1;
        int col = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.buttons[i][j].equals(button)) {
                    row = i;
                    col = j;
                }
            }
        }

        JButton retVal = null;

        if (direction <= 0 || direction > 8) {
            return null;
        }

        if (direction == 1) {
            if (row - 1 < 0 || col + 1 > 7) {
                return null;
            }
            retVal = this.buttons[row - 1][col + 1];
        } else if (direction == 2) {
            if (row - 1 < 0) {
                return null;
            }
            retVal = this.buttons[row - 1][col];
        } else if (direction == 3) {
            if (row - 1 < 0 || col - 1 < 0) {
                return null;
            }
            retVal = this.buttons[row - 1][col - 1];
        } else if (direction == 4) {
            if (col - 1 < 0) {
                return null;
            }
            retVal = this.buttons[row][col - 1];
        } else if (direction == 5) {
            if (row + 1 > 7 || col - 1 < 0) {
                return null;
            }
            retVal = this.buttons[row + 1][col - 1];
        } else if (direction == 6) {
            if (row + 1 > 7) {
                return null;
            }
            retVal = this.buttons[row + 1][col];
        } else if (direction == 7) {
            if (row + 1 > 7 || col + 1 > 7) {
                return null;
            }
            retVal = this.buttons[row + 1][col + 1];
        } else {
            if (col + 1 > 7) {
                return null;
            }
            retVal = this.buttons[row][col + 1];
        }
        return retVal;

    }
}
