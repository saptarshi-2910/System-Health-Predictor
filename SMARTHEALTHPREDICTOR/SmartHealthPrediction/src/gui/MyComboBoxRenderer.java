package gui;

import javax.swing.*;
import java.awt.*;

public class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
    private String title;

    public MyComboBoxRenderer(String title) {
        this.title = title;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        if (index == -1 && value == null)
            setText(title);
        else
            setText(value.toString());
        return this;
    }
}