package gui;

import user.Admin;
import user.Doctor;
import user.Patient;
import user.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class GUI {
    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=SHPS;integratedSecurity=true;";
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;
    {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private JFrame welcomeFrame;
    private JFrame loginFrame;
    private JFrame signUpFrame;
    private JFrame homeFrame;

    private User user;

    public GUI() {
        setWelcomeFrame();
    }

    public void setWelcomeFrame() {
        this.welcomeFrame = new JFrame("WELCOME");

        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("images/welcome.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        welcomeFrame.setContentPane(new ImagePanel(myImage));

        welcomeFrame.getContentPane().setPreferredSize(new Dimension(800, 449));
        welcomeFrame.setBackground(Color.BLACK);

        MyLabel[] labels = {new MyLabel("SMART"), new MyLabel("HEALTH"), new MyLabel("PREDICTION"), new MyLabel("SYSTEM")};
        for (int i = 0; i < labels.length; i++) {
            labels[i].setForeground(Color.WHITE);
            labels[i].setFont(new Font("Helvetica", Font.BOLD, 40));
            labels[i].setBounds(460, 50 * i, 300, 100);
            welcomeFrame.add(labels[i]);
        }

        MyButton signUp = new MyButton("  Sign Up  ");
        signUp.setLeftButton();
        signUp.setBounds(572, 275, 100, 40);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                setSignUpFrame();
            }
        });

        MyButton logIn = new MyButton(" Log In ");
        logIn.setRightButton();
        logIn.setBounds(572, 325, 100, 40);
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                setLogInFrame();
            }
        });

        welcomeFrame.add(signUp);
        welcomeFrame.add(logIn);

        welcomeFrame.pack();
        welcomeFrame.setResizable(false);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setVisible(true);
    }

    public void setSignUpFrame() {
        this.signUpFrame = new JFrame("SIGN UP");

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(800, 400));

        MyLabel label = new MyLabel("SIGN UP FOR A NEW ACCOUNT");
        label.setHeadingLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridBagLayout());
        fieldPanel.setBackground(Color.BLACK);

        MyLabel nameLabel = new MyLabel("Full Name:  ");
        nameLabel.setCommonLabel();
        MyTextField[] nameFields = {new MyTextField(10), new MyTextField(10), new MyTextField(10)};
        nameFields[0].setDocument(new MyDocument(20));
        nameFields[0].setText("First");
        nameFields[1].setDocument(new MyDocument(20));
        nameFields[1].setText("Middle");
        nameFields[2].setDocument(new MyDocument(20));
        nameFields[2].setText("Last");

        MyLabel dobLabel = new MyLabel("Date of Birth:  ");
        dobLabel.setCommonLabel();
        Integer[] day = new Integer[31];
        for (int i = 0; i < day.length; i++) {
            day[i] = i + 1;
        }
        Integer[] month = new Integer[12];
        for (int i = 0; i < month.length; i++) {
            month[i] = i + 1;
        }
        Integer[] year = new Integer[121];
        for (int i = 0; i < year.length; i++) {
            year[i] = 2021 - i;
        }
        MyComboBox<Integer>[] dobCB = new MyComboBox[]{new MyComboBox<>(day), new MyComboBox<>(month), new MyComboBox<>(year)};
        dobCB[0].setRenderer(new MyComboBoxRenderer("Day"));
        dobCB[1].setRenderer(new MyComboBoxRenderer("Month"));
        dobCB[2].setRenderer(new MyComboBoxRenderer("Year"));
        for (int i = 0; i < dobCB.length; i++) {
            dobCB[i].setSelectedIndex(-1);
        }

        MyLabel userLabel = new MyLabel("Username:  ");
        userLabel.setCommonLabel();
        MyTextField userField = new MyTextField(10);
        userField.setDocument(new MyDocument(20));

        MyLabel pwLabel = new MyLabel("              Password:");
        pwLabel.setCommonLabel();
        MyPasswordField pwField = new MyPasswordField(10);
        pwField.setDocument(new MyDocument(20));

        MyLabel phoneLabel = new MyLabel("Phone Number:  ");
        phoneLabel.setCommonLabel();
        MyTextField phoneField = new MyTextField(10);
        phoneField.setDocument(new MyDocument(11));

        MyLabel emailLabel = new MyLabel("      Email Address:");
        emailLabel.setCommonLabel();
        MyTextField emailField = new MyTextField();
        emailField.setDocument(new MyDocument(50));

        MyLabel addressLabel = new MyLabel("Address:  ");
        addressLabel.setCommonLabel();
        MyTextField addressField = new MyTextField();
        addressField.setDocument(new MyDocument(100));

        MyLabel typeLabel = new MyLabel("You Are:  ");
        typeLabel.setCommonLabel();
        String[] type = new String[1];
        type[0] = "A Doctor";
        MyComboBox<String> typeCB = new MyComboBox<>(new String[]{"A Doctor", "A Patient"});
        typeCB.setSelectedIndex(0);
        MyLabel specialtyLabel = new MyLabel("      Your Specialty:");
        specialtyLabel.setCommonLabel();
        int countSpecialty = getNumberOfRows("Specialty");
        String[] spStr = new String[countSpecialty];
        try {
            resultSet = statement.executeQuery("select * from Specialty");
            for (int i = 0; i < countSpecialty; i++) {
                resultSet.next();
                spStr[i] = resultSet.getString(2).trim();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        MyComboBox<String> specialtyCB = new MyComboBox<>(spStr);
        specialtyCB.setSelectedIndex(0);
        typeCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type[0] = String.valueOf(typeCB.getSelectedItem());

                if (type[0].equals("A Patient")) {
                    specialtyLabel.setText("");
                    specialtyCB.setEnabled(false);
                }
                else {
                    specialtyLabel.setText("      Your Specialty:");
                    specialtyCB.setEnabled(true);
                }
            }
        });

        // First - Middle - Last
        fieldPanel.add(nameLabel, gbc(0, 0, 1, 1, 0, 0.005));
        fieldPanel.add(nameFields[0], gbc(1, 0, 1, 1, 0, 0.005));
        fieldPanel.add(new MyLabel("  "), gbc(2, 0, 1, 1, 0, 0.005));
        fieldPanel.add(nameFields[1], gbc(3, 0, 1, 1, 0, 0.005));
        fieldPanel.add(new MyLabel("  "), gbc(4, 0, 1, 1, 0, 0.005));
        fieldPanel.add(nameFields[2], gbc(5, 0, 1, 1, 0, 0.005));

        // DD - MM - YYYY
        fieldPanel.add(dobLabel, gbc(0, 1, 1, 1, 0, 0.005));
        fieldPanel.add(dobCB[0], gbc(1, 1, 1, 1, 0, 0.005));
        fieldPanel.add(dobCB[1], gbc(3, 1, 1, 1, 0, 0.005));
        fieldPanel.add(dobCB[2], gbc(5, 1, 1, 1, 0, 0.005));

        // Username - Password
        fieldPanel.add(userLabel, gbc(0, 2, 1, 1, 0, 0.005));
        fieldPanel.add(userField, gbc(1, 2, 1, 1, 0, 0.005));
        fieldPanel.add(pwLabel, gbc(3, 2, 1, 1, 0, 0.005));
        fieldPanel.add(pwField, gbc(5, 2, 1, 1, 0, 0.005));

        // Phone - Email
        fieldPanel.add(phoneLabel, gbc(0, 3, 1, 1, 0, 0.005));
        fieldPanel.add(phoneField, gbc(1, 3, 1, 1, 0, 0.005));
        fieldPanel.add(emailLabel, gbc(3, 3, 1, 1, 0, 0.005));
        fieldPanel.add(emailField, gbc(5, 3, 1, 1, 0, 0.005));

        // Address
        fieldPanel.add(addressLabel, gbc(0, 4, 1, 1, 0, 0.005));
        fieldPanel.add(addressField, gbc(1, 4, 5, 1, 0, 0.005));

        // Type - Specialty
        fieldPanel.add(typeLabel, gbc(0, 5, 1, 1, 0, 0.005));
        fieldPanel.add(typeCB, gbc(1, 5, 1, 1, 0, 0.005));
        fieldPanel.add(specialtyLabel, gbc(3, 5, 1, 1, 0, 0.005));
        fieldPanel.add(specialtyCB, gbc(5, 5, 1, 1, 0, 0.005));

        fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(800, 60));

        MyButton go = new MyButton("  Sign Up  ");
        go.setLeftButton();
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (type[0]) {
                    case "A Doctor":
                        type[0] = "Doctor";
                        break;
                    case "A Patient":
                        type[0] = "Patient";
                        break;
                }

                String fName = nameFields[0].getText(), mName = nameFields[1].getText(), lName = nameFields[2].getText();
                String phone = phoneField.getText(), email = emailField.getText(), address = addressField.getText();
                String dob = dobCB[2].getSelectedItem() + "-" + dobCB[1].getSelectedItem() + "-" + dobCB[0].getSelectedItem();
                String user = userField.getText(), pw = new String(pwField.getPassword());

                boolean flag = true;
                if (fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() ||
                        (dobCB[2].getSelectedIndex() == -1) || (dobCB[1].getSelectedIndex() == -1) || (dobCB[0].getSelectedIndex() == -1) ||
                        user.isEmpty() || pw.isEmpty()) {
                    MyDialog.messageDialog("Input empty.", "SIGN UP ERROR", JOptionPane.ERROR_MESSAGE, null);
                    flag = false;
                }

                if (!user.isEmpty()) {
                    checkValidUsername(user, type[0]);
                    flag = false;
                }


                    try {
                        if (type[0].equals("Doctor")) {
                            int specialty = specialtyCB.getSelectedIndex() + 1;
                            statement.executeUpdate("insert into Doctor " +
                                    "(d_firstname, d_middlename, d_lastname, phone, email, address, dob, specialty_id, username, password) values " +
                                    "('" + fName + "', '" + mName + "', '" + lName + "', '" +
                                    phone + "', '" + email + "', '" + address + "', '" +
                                    dob + "', " +
                                    specialty + ", '" +
                                    user + "', '" + pw + "')");

                            selectFromTable("Doctor");
                        } else if (type[0].equals("Patient")) {
                            statement.executeUpdate("insert into Patient " +
                                    "(p_firstname, p_middlename, p_lastname, phone, email, address, dob, username, password) values " +
                                    "('" + fName + "', '" + mName + "', '" + lName + "', '" +
                                    phone + "', '" + email + "', '" + address + "', '" +
                                    dob + "', '" +
                                    user + "', '" + pw + "')");

                            selectFromTable("Patient");
                        }

                        MyDialog.messageDialog("Signed up!", "SIGN UP SUCCESS", JOptionPane.INFORMATION_MESSAGE, null);

                        signUpFrame.dispose();
                        setLogInFrame();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

            }
        });

        MyButton back = new MyButton("  Back  ");
        back.setRightButton();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpFrame.dispose();
                welcomeFrame.setVisible(true);
            }
        });

        buttonPanel.add(go);
        buttonPanel.add(back);

        mainPanel.add(label);
        mainPanel.add(fieldPanel);
        mainPanel.add(buttonPanel);

        signUpFrame.add(mainPanel);

        signUpFrame.pack();
        signUpFrame.setResizable(false);
        signUpFrame.setLocationRelativeTo(null);
        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpFrame.setVisible(true);
    }

    public void setLogInFrame() {
        this.loginFrame = new JFrame("LOG IN");

        MyLabel label = new MyLabel("LOG IN TO THE SYSTEM ");
        label.setHeadingLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        MyLabel typeLabel = new MyLabel("You Are:  ");
        typeLabel.setCommonLabel();

        String[] types = {"An Admin", "A Doctor", "A Patient"};
        final String[] userType = new String[1];
        MyComboBox<String> comboBox = new MyComboBox<>(types);
        comboBox.setRenderer(new MyComboBoxRenderer("Account"));
        comboBox.setSelectedIndex(-1);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userType[0] = String.valueOf(comboBox.getSelectedItem());
            }
        });

        JPanel typePanel = new JPanel();
        typePanel.setBackground(Color.BLACK);
        typePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typePanel.setPreferredSize(new Dimension(800, 60));
        typePanel.add(typeLabel);
        typePanel.add(comboBox);

        MyLabel userLabel = new MyLabel("Username:  ");
        userLabel.setCommonLabel();
        MyTextField userField = new MyTextField(10);
        userField.setDocument(new MyDocument(20));

        MyLabel pwLabel = new MyLabel("Password:  ");
        pwLabel.setCommonLabel();
        MyPasswordField pwField = new MyPasswordField(10);
        pwField.setDocument(new MyDocument(20));

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridBagLayout());
        fieldPanel.setBackground(Color.BLACK);
        fieldPanel.add(userLabel, gbc(0, 0, 1, 1, 0, 0.005));
        fieldPanel.add(userField, gbc(1, 0, 1, 1, 0, 0.005));
        fieldPanel.add(pwLabel, gbc(0, 1, 1, 1, 0, 0.005));
        fieldPanel.add(pwField, gbc(1, 1, 1, 1, 0, 0.005));
        fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(800, 60));
        MyButton go = new MyButton("  Log In  ");
        go.setLeftButton();
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userType[0] == null) {
                    MyDialog.messageDialog("Choose an account type.", "LOG IN ERROR", JOptionPane.ERROR_MESSAGE, null);
                }
                else {
                    switch (userType[0]) {
                        case "An Admin":
                            userType[0] = "Admin";
                            break;
                        case "A Doctor":
                            userType[0] = "Doctor";
                            break;
                        case "A Patient":
                            userType[0] = "Patient";
                            break;
                    }
                    selectFromTable(userType[0]);
                    String username = userField.getText();
                    String password = String.valueOf(pwField.getPassword());
                    boolean flag = false;

                    try {
                        resultSet = statement.executeQuery("select * from " +  userType[0]);
                        int numberOfColumns = resultSet.getMetaData().getColumnCount();

                        while (resultSet.next()) {
                            String un = resultSet.getString(numberOfColumns - 1).trim();
                            String pw = resultSet.getString(numberOfColumns).trim();

                            if (un.equals(username) && pw.equals(password)) {
                                switch (userType[0]) {
                                    case "Admin":
                                        user = new Admin(username, password);
                                        break;
                                    case "Patient":
                                        user = new Patient(username, password);
                                        break;
                                    case "Doctor":
                                        user = new Doctor(username, password);
                                        break;
                                }
                                flag = true;
                                break;
                            }
                        }

                        if (!flag) {
                            MyDialog.messageDialog("Incorrect credentials.", "LOGIN ERROR", JOptionPane.ERROR_MESSAGE, null);
                        }
                        else {
                            MyDialog.messageDialog("Logged in!", "LOGIN SUCCESS", JOptionPane.INFORMATION_MESSAGE, null);
                            loginFrame.dispose();
                            setHomeFrame(userType[0]);
                        }
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        MyButton back = new MyButton("  Back  ");
        back.setRightButton();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                welcomeFrame.setVisible(true);
            }
        });
        buttonPanel.add(go);
        buttonPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(500, 200));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(label);
        mainPanel.add(typePanel);
        mainPanel.add(fieldPanel);
        mainPanel.add(buttonPanel);

        loginFrame.add(mainPanel);

        loginFrame.pack();
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    public void setHomeFrame(String userType) {
        this.homeFrame = new JFrame(userType.toUpperCase() + " HOME");

        homeFrame.getContentPane().setPreferredSize(new Dimension(1200, 800));
        homeFrame.getContentPane().setBackground(Color.BLACK);

        JTabbedPane tp = new JTabbedPane();
        tp.setFont(new Font("Helvetica", Font.BOLD, 15));

        if (userType.equals("Admin")) {
            Admin admin = (Admin) user;
            tp.addTab("View Doctors", admin.viewDoctors());
            tp.addTab("View Patients", admin.viewPatients());
            tp.addTab("View Diseases", admin.viewDiseases());
            tp.addTab("View Feedback", admin.viewFeedback());
            tp.addTab("Log Out", admin.logOut());
        }
        else if (userType.equals("Doctor")) {
            Doctor doctor = (Doctor) user;
            tp.addTab("My Profile", doctor.viewMyProfile());
            tp.addTab("My Patients", doctor.viewMyPatients());
            tp.addTab("View Diseases", doctor.viewDiseases());
            tp.addTab("Log Out", doctor.logOut());
        }
        else if (userType.equals("Patient")) {
            Patient patient = (Patient) user;
            tp.addTab("My Profile", patient.viewMyProfile());
            tp.addTab("My History", patient.viewMyHistory());
            tp.addTab("Get Diagnosis", patient.getDiagnosis());
            tp.addTab("Give Feedback", patient.giveFeedback());
            tp.addTab("Log Out", patient.logOut());
        }

        homeFrame.add(tp);
        homeFrame.pack();
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);
    }

    public static GridBagConstraints gbc(int gridx, int gridy, int gridwidth, int gridheight, double weightx,  double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        return gbc;
    }

    public class ImagePanel extends JComponent {
        private Image image;

        public ImagePanel(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    public static void selectFromTable(String table) {
        try {
            resultSet = statement.executeQuery("select * from " + table);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.print(resultSet.getObject(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int getNumberOfRows(String table) {
        int count = 0;
        try {
            resultSet = statement.executeQuery("select * from " + table);
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    public boolean checkValidUsername(String newUser, String table) {
        boolean valid = true;
        try {
            resultSet = statement.executeQuery("select * from " + table);
            int numberOfColumns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                String oldUser = resultSet.getString(numberOfColumns - 1).trim();
                if (oldUser.equals(newUser)) {
                    MyDialog.messageDialog("Username already taken.", "SIGN UP ERROR", JOptionPane.ERROR_MESSAGE, null);
                    valid = false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return valid;
    }
}
