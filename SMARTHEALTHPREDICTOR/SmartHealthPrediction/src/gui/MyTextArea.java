package gui;

import javax.swing.*;
import java.awt.*;

public class MyTextArea extends JTextArea {
    public MyTextArea(int rows, int columns) {
        super(rows, columns);
        setMyTextArea();
    }

    public void setMyTextArea() {
        setBorder(BorderFactory.createLineBorder(new Color(29, 182, 242)));
        setFont(new Font("Helvetica", Font.PLAIN, 20));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }
}
