package gui;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public MyTextField() {
        super();
        setMyTextField();
    }

    public MyTextField(int columns) {
        super(columns);
        setMyTextField();
    }

    public void setMyTextField() {
        setBorder(BorderFactory.createLineBorder(new Color(29, 182, 242)));
        setFont(new Font("Helvetica", Font.PLAIN, 20));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }
}
