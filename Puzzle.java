import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Puzzle extends Frame implements ActionListener {
    private Button[] buttons;

    // Define row colors that is being used
    private Color[] rowColors = { Color.RED, Color.GREEN, Color.BLUE };

    public Puzzle() {
        super("Puzzle - JavaTpoint");

        // Create an array of buttons with labels
        String[] labels = { "1", "2", "3", "4", "5", "6", "7", "8", "" };
        buttons = new Button[labels.length];

        // Shuffle labels randomly
        shuffleArray(labels);

        // Create buttons based on shuffled labels
        for (int i = 0; i < labels.length; i++) {
            buttons[i] = new Button(labels[i]);
            buttons[i].addActionListener(this);
            add(buttons[i]);

            // Set background color based on row
            buttons[i].setBackground(rowColors[i / 3]); // Determine row color
        }

        // Set layout using GridLayout (3x3 grid)
        setLayout(new GridLayout(3, 3));

        setSize(400, 400);
        setVisible(true);
    }

    private void shuffleArray(String[] array) {
        // Fisher-Yates shuffle algorithm to shuffle the array
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Handle button click events
        Button clickedButton = (Button) e.getSource();
        int clickedIndex = -1;

        // Find the index of the clicked button in the array
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == clickedButton) {
                clickedIndex = i;
                break;
            }
        }

        // Check if the clicked button can be moved
        if (canMove(clickedIndex)) {
            // Find the index of the empty button
            int emptyIndex = findEmptyIndex();

            // Swap labels between clicked button and the empty button
            String tempLabel = buttons[clickedIndex].getLabel();
            buttons[clickedIndex].setLabel("");
            buttons[emptyIndex].setLabel(tempLabel);

            // Check for winning condition
            if (isSolved()) {
                JOptionPane.showMessageDialog(this, "Congratulations! You won.");
            }
        }
    }

    private boolean canMove(int clickedIndex) {
        // Find the index of the empty button
        int emptyIndex = findEmptyIndex();

        // Check if the clicked button is adjacent to the empty button
        return (Math.abs(clickedIndex - emptyIndex) == 1 && Math.min(clickedIndex, emptyIndex) % 3 != 2) ||
                (Math.abs(clickedIndex - emptyIndex) == 3);
    }

    private int findEmptyIndex() {
        // Find the index of the button with an empty label
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getLabel().isEmpty()) {
                return i;
            }
        }
        return -1; // Not found (should not happen in this context)
    }

    private boolean isSolved() {
        // Check if buttons are in correct order (1 to 8 followed by an empty label)
        for (int i = 0; i < buttons.length - 1; i++) {
            if (!buttons[i].getLabel().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return buttons[buttons.length - 1].getLabel().isEmpty(); // Last button should be empty
    }

    public static void main(String[] args) {
        new Puzzle();
    }
}
