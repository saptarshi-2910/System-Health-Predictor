package gui;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
    public MyButton(String text) {
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setFocusPainted(false);
    }

    public void setLeftButton() {
        setFont(new Font("Helvetica", Font.BOLD, 20));
        setForeground(Color.WHITE);
        setBackground(new Color(29, 182, 242));
        setBorder(BorderFactory.createLineBorder(new Color(29, 182, 242)));
    }

    public void setRightButton() {
        setFont(new Font("Helvetica", Font.BOLD, 20));
        setForeground(new Color(29, 182, 242));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(new Color(29, 182, 242)));
    }
}
