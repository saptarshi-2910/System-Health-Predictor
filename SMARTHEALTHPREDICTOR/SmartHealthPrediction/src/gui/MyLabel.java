package gui;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    public MyLabel(String text) {
        super(text);
    }

    public void setCommonLabel() {
        setForeground(Color.WHITE);
        setFont(new Font("Helvetica", Font.BOLD, 20));
    }

    public void setHeadingLabel() {
        setForeground(new Color(29, 182, 242));
        setFont(new Font("Helvetica", Font.BOLD, 30));
    }
}
