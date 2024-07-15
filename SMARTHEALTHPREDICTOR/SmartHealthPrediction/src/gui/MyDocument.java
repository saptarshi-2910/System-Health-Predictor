package gui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MyDocument extends PlainDocument {
    private int max;

    public MyDocument(int max) {
        this.max = max;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (getLength() + str.length() > max) {
            MyDialog.messageDialog("Cannot exceed " + max + " characters!", "LIMIT", JOptionPane.ERROR_MESSAGE, null);
            str = str.substring(0, max - getLength());
        }

        super.insertString(offs, str, a);
    }
}
