package gui;

import javax.swing.*;
import java.awt.*;

public class MyDialog extends JOptionPane {
    public static void messageDialog(Object message, String title, int messageType, Icon icon) {
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Helvetica", Font.BOLD, 20));
        UIManager.put("OptionPane.buttonFont", new Font("Helvetica", Font.BOLD, 20));
        //UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(14, 123, 247), 3, true));
        UIManager.put("Button.background", new Color(29, 182, 242));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.okButtonText", "  Okay  ");

        showMessageDialog(null, message, title, messageType, icon);

        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("OptionPane.buttonFont", null);
        UIManager.put("Button.border", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("OptionPane.okButtonText", null);
    }
}
