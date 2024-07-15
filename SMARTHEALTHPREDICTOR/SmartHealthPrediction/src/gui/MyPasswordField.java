package gui;

import javax.swing.*;
import java.awt.*;

public class MyPasswordField extends JPasswordField {
    public MyPasswordField() {
        super();
        setMyPasswordField();
    }

    public MyPasswordField(int columns) {
        super(columns);
        setMyPasswordField();
    }

    public void setMyPasswordField() {
        setBorder(BorderFactory.createLineBorder(new Color(29, 182, 242)));
        setFont(new Font("Helvetica", Font.PLAIN, 20));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }
}
