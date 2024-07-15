package gui;

import javax.swing.*;
import java.awt.*;

public class MyComboBox<E> extends JComboBox<E> {
    public MyComboBox(E[] aModel) {
        super(aModel);
        setMyComboBox();
    }

    public void setMyComboBox() {
        setBorder(BorderFactory.createLineBorder(new Color(29, 182, 242)));
        setFont(new Font("Helvetica", Font.PLAIN, 20));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }
}
