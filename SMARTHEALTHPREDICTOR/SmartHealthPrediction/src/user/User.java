package user;

import gui.GUI;
import gui.MyButton;
import gui.MyDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public abstract class User {
    private final String url = "jdbc:sqlserver://localhost;database=SHPS;integratedSecurity=true;";
    public static Connection connection;
    public static Statement statement;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;
    {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private int id;
    private String fName, mName, lName;
    private String phone;
    private String email;
    private String address;
    private String dob;
    private String username;
    private String password;

    public User(int id) {
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JPanel logOut() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Are you sure you want to log out?");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Helvetica", Font.BOLD, 20));

        MyButton b1 = new MyButton("  Yes  ");
        b1.setLeftButton();
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GUI.connection.close();

                    MyDialog.messageDialog("You have logged out.", "LOG OUT", JOptionPane.INFORMATION_MESSAGE, null);

                    System.exit(0);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        panel.add(label, GUI.gbc(0, 0, 1, 1, 0, 0));
        panel.add(new JLabel(" "), GUI.gbc(0, 1, 1, 1, 0, 0));
        GridBagConstraints gbc = GUI.gbc(0, 2, 1, 1, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        panel.add(b1, gbc);

        return panel;
    }

    public String checkNull(String str) {
        if (str == null)
            return "";
        else return str.trim();
    }

    public String[] parseDob(String dob) {
        return dob.split("-");
    }

    @Override
    public String toString() {
        return getFName() + " " + getMName() + " " + getLName() + " (" + getUsername() + ")";
    }
}
