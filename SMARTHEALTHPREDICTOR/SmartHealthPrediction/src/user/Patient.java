package user;

import disease.Disease;
import disease.Specialty;
import disease.Symptom;
import gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Patient extends User {
    public Patient(int id) {
        super(id);
        setProfile();
    }

    public Patient(String username, String password) {
        super(username, password);
        setProfile();
    }

    public JPanel viewMyProfile() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        MyLabel nameLabel = new MyLabel("Full Name:  ");
        nameLabel.setCommonLabel();
        MyTextField[] nameFields = {new MyTextField(10), new MyTextField(10), new MyTextField(10)};
        nameFields[0].setDocument(new MyDocument(20));
        nameFields[0].setText(getFName());
        nameFields[1].setDocument(new MyDocument(20));
        nameFields[1].setText(getMName());
        nameFields[2].setDocument(new MyDocument(20));
        nameFields[2].setText(getLName());

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
        dobCB[0].setRenderer(new MyComboBoxRenderer(parseDob(getDob())[2]));
        dobCB[1].setRenderer(new MyComboBoxRenderer(parseDob(getDob())[1]));
        dobCB[2].setRenderer(new MyComboBoxRenderer(parseDob(getDob())[0]));
        for (int i = 0; i < dobCB.length; i++) {
            dobCB[i].setSelectedIndex(-1);
        }

        MyLabel phoneLabel = new MyLabel("Phone Number:  ");
        phoneLabel.setCommonLabel();
        MyTextField phoneField = new MyTextField(10);
        phoneField.setDocument(new MyDocument(11));
        phoneField.setText(getPhone());

        MyLabel emailLabel = new MyLabel("Email Address:  ");
        emailLabel.setCommonLabel();
        MyTextField emailField = new MyTextField();
        emailField.setDocument(new MyDocument(50));
        emailField.setText(getEmail());

        MyLabel addressLabel = new MyLabel("Address:  ");
        addressLabel.setCommonLabel();
        MyTextField addressField = new MyTextField();
        addressField.setDocument(new MyDocument(100));
        addressField.setText(getAddress());

        panel.add(nameLabel, gbc(0, 0, 1, 1, 0, 0.005));
        panel.add(nameFields[0], gbc(1, 0, 1, 1, 0, 0.005));
        panel.add(new MyLabel(" "), gbc(2, 0, 1, 1, 0, 0.005));
        panel.add(nameFields[1], gbc(3, 0, 1, 1, 0, 0.005));
        panel.add(new MyLabel(" "), gbc(4, 0, 1, 1, 0, 0.005));
        panel.add(nameFields[2], gbc(5, 0, 1, 1, 0, 0.005));

        panel.add(dobLabel, gbc(0, 1, 1, 1, 0, 0.005));
        panel.add(dobCB[0], gbc(1, 1, 1, 1, 0, 0.005));
        panel.add(dobCB[1], gbc(3, 1, 1, 1, 0, 0.005));
        panel.add(dobCB[2], gbc(5, 1, 1, 1, 0, 0.005));

        panel.add(phoneLabel, gbc(0, 2, 1, 1, 0, 0.005));
        panel.add(phoneField, gbc(1, 2, 1, 1, 0, 0.005));

        panel.add(emailLabel, gbc(0, 3, 1, 1, 0, 0.005));
        panel.add(emailField, gbc(1, 3, 1, 1, 0, 0.005));

        panel.add(addressLabel, gbc(0, 4, 1, 1, 0, 0.005));
        panel.add(addressField, gbc(1, 4, 1, 1, 0, 0.005));

        MyButton update = new MyButton("  Update Profile  ");
        update.setLeftButton();
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultSet = statement.executeQuery("select * from Patient");

                    while (resultSet.next()) {
                        if (resultSet.getString("username").trim().equals(getUsername())) {
                            preparedStatement = connection.prepareStatement("update Patient " +
                                    "set p_firstname = ?, p_middlename = ?, p_lastname = ?, " +
                                    "phone = ?, email = ?, address = ?, " +
                                    "dob = ? " +
                                    "where username = ?");
                            preparedStatement.setString(8, getUsername());

                            preparedStatement.setString(1, nameFields[0].getText());
                            preparedStatement.setString(2, nameFields[1].getText());
                            preparedStatement.setString(3, nameFields[2].getText());
                            preparedStatement.setString(4, phoneField.getText());
                            preparedStatement.setString(5, emailField.getText());
                            preparedStatement.setString(6, addressField.getText());
                            String day = parseDob(getDob())[2], month = parseDob(getDob())[1], year = parseDob(getDob())[0];
                            if (dobCB[0].getSelectedIndex() != -1)
                                day = String.valueOf(dobCB[0].getSelectedItem());
                            if (dobCB[1].getSelectedIndex() != -1)
                                month = String.valueOf(dobCB[1].getSelectedItem());
                            if (dobCB[2].getSelectedIndex() != -1)
                                year = String.valueOf(dobCB[2].getSelectedItem());
                            preparedStatement.setDate(7, Date.valueOf(year + "-" + month + "-" + day));
                            preparedStatement.executeUpdate();
                            MyDialog.messageDialog("Successful!", "UPDATE PROFILE", JOptionPane.INFORMATION_MESSAGE, null);
                            GUI.selectFromTable("Patient");
                            break;
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(update);

        panel.add(buttonPanel, gbc(0, 6, 6, 1, 0, 0.005));

        return panel;
    }

    public JPanel viewMyHistory() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        MyLabel l1 = new MyLabel("  Declarations  ");
        l1.setHeadingLabel();
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        MyLabel l2 = new MyLabel("  Diagnoses  ");
        l2.setHeadingLabel();
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 2));
        panel2.setBackground(Color.BLACK);
        panel2.add(l1);
        panel2.add(l2);

        MyTextArea symptom = new MyTextArea(20, 20);
        symptom.setEditable(false);
        JScrollPane scroll1 = new JScrollPane(symptom);
        scroll1.setPreferredSize(new Dimension(400, 400));

        MyTextArea diagnosis = new MyTextArea(20, 20);
        diagnosis.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(diagnosis);
        scroll2.setPreferredSize(new Dimension(400, 400));

        MyButton refresh = new MyButton("  Refresh  ");
        refresh.setLeftButton();
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.BLACK);
        panel3.setLayout(new GridBagLayout());
        panel3.add(refresh);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultSet = statement.executeQuery("select * from Declaration");
                    String date = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while (resultSet.next()) {
                        if (Integer.parseInt(resultSet.getObject(1).toString().trim()) == getId()) {
                            String newDate = resultSet.getObject(3).toString().trim();
                            if (!newDate.equals(date)) {
                                date = newDate;
                                stringBuilder.append("On " + parseDob(date)[2] + "/" + parseDob(date)[1] + "/" + parseDob(date)[0] + ":").append("\n");
                            }
                            stringBuilder.append("\tSymptom: ").append(new Symptom(resultSet.getInt(2))).append("\n");
                        }
                    }

                    symptom.setText(stringBuilder.toString());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    resultSet = statement.executeQuery("select * from Diagnosis");
                    String date = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while (resultSet.next()) {
                        if (Integer.parseInt(resultSet.getObject(1).toString().trim()) == getId()) {
                            String newDate = resultSet.getObject(3).toString().trim();
                            if (!newDate.equals(date)) {
                                date = newDate;
                                stringBuilder.append("On " + parseDob(date)[2] + "/" + parseDob(date)[1] + "/" + parseDob(date)[0] + ":").append("\n");
                            }
                            stringBuilder.append("\tDiagnosis: ").append(new Disease(resultSet.getInt(2))).append("\n");
                        }
                    }
                    diagnosis.setText(stringBuilder.toString());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        panel.add(panel2, gbc(0, 0, 5, 1, 0, 0.1));

        panel.add(new MyLabel(" "), gbc(0, 1, 1, 1, 0.1, 0.8));
        panel.add(scroll1, gbc(1, 1, 1, 1, 0.4, 0.8));
        panel.add(new MyLabel(" "), gbc(2, 1, 1, 1, 0, 0.8));
        panel.add(scroll2, gbc(3, 1, 1, 1, 0.4, 0.8));
        panel.add(new MyLabel(" "), gbc(4, 1, 1, 1, 0.1, 0.8));

        panel.add(panel3, gbc(0, 2, 5, 1, 0, 0.1));

        refresh.doClick();

        return panel;
    }

    public JPanel getDiagnosis() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        ArrayList<Symptom> declaration = new ArrayList<>();
        ArrayList<Symptom> symptomList = Symptom.getAllSymptoms();

        MyComboBox<Symptom>[] symptomCB = new MyComboBox[5];
        for (int i = 0; i < symptomCB.length; i++) {
            symptomCB[i] = new MyComboBox<>(symptomList.toArray(new Symptom[0]));
            symptomCB[i].setSelectedIndex(-1);
            symptomCB[i].setRenderer(new MyComboBoxRenderer("Choose symptom #" + (i + 1)));
            panel.add(symptomCB[i], gbc(0, i, 1, 1, 0.2, 0.005));
        }

        MyButton diagnoseButton = new MyButton("  Diagnose  ");
        diagnoseButton.setLeftButton();
        MyButton refreshButton = new MyButton("  Refresh  ");
        refreshButton.setRightButton();

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setBackground(Color.BLACK);
        buttonPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel1.add(diagnoseButton);
        buttonPanel1.add(refreshButton);

        MyTextArea result1 = new MyTextArea(10, 10);
        result1.setEditable(false);
        JScrollPane scroll1 = new JScrollPane(result1);

        ArrayList<Doctor> doctorList = new ArrayList<>();
        MyComboBox<Doctor> doctorCB = new MyComboBox<>(doctorList.toArray(new Doctor[0]));
        doctorCB.setSelectedIndex(-1);
        doctorCB.setRenderer(new MyComboBoxRenderer("Choose a doctor"));
        DefaultComboBoxModel<Doctor> model = (DefaultComboBoxModel<Doctor>) doctorCB.getModel();

        MyButton searchButton = new MyButton("  Search  ");
        searchButton.setLeftButton();
        MyButton bookButton = new MyButton("  Set Appointment  ");
        bookButton.setRightButton();

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setBackground(Color.BLACK);
        buttonPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.add(doctorCB);
        buttonPanel2.add(searchButton);
        buttonPanel2.add(bookButton);

        MyTextArea result2 = new MyTextArea(10, 10);
        result2.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(result2);

        panel.add(buttonPanel1, gbc(0, 5, 1, 1, 0, 0.005));
        panel.add(new MyLabel(" "), gbc(1, 0, 1, 6, 0.1, 0.005));
        panel.add(scroll1, gbc(2, 0, 1, 2, 0.7, 0.4));

        panel.add(buttonPanel2, gbc(2, 2, 1, 1, 0, 0.2));
        panel.add(scroll2, gbc(2, 3, 1, 3, 0, 0.4));

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < symptomCB.length; i++) {
                    symptomCB[i].setSelectedIndex(-1);
                    symptomCB[i].setRenderer(new MyComboBoxRenderer("Choose symptom #" + (i + 1)));
                }
                result1.setText("");
                panel.repaint();
            }
        });
        diagnoseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                declaration.clear();
                for (int i = 0; i < symptomCB.length; i++) {
                    if (symptomCB[i].getSelectedIndex() != -1) {
                        Symptom symptom = (Symptom) symptomCB[i].getSelectedItem();
                        declaration.add(symptom);

                        try {
                            preparedStatement = connection.prepareStatement("insert into Declaration (patient_id, symptom_id) values (?, ?)");
                            preparedStatement.setInt(1, getId());
                            preparedStatement.setInt(2, symptom.getId());
                            preparedStatement.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
                GUI.selectFromTable("Declaration");
                System.out.println(declaration);

                ArrayList<ArrayList<Disease>> diagnosisList = new ArrayList<>();
                if (!declaration.isEmpty()) {
                    ArrayList<Disease> dList = new ArrayList<>();
                    dList.add(new Disease(declaration.get(0).getClassId("Symptom")));
                    diagnosisList.add(dList);

                    for (int i = 1; i < declaration.size(); i++) {
                        Disease currentDisease = new Disease(declaration.get(i).getClassId("Symptom"));

                        int index = -1;

                        for (int j = 0; j < diagnosisList.size(); j++) {
                            dList = diagnosisList.get(j);
                            if (currentDisease.getId() == dList.get(0).getId()) {
                                index = j;
                                break;
                            }
                        }

                        if (index == -1) {
                            dList = new ArrayList<>();
                            dList.add(currentDisease);
                            diagnosisList.add(dList);
                        }
                        else {
                            diagnosisList.get(index).add(currentDisease);
                        }
                    }
                    System.out.println(diagnosisList);

                    int max = -1;
                    ArrayList<Integer> indexDiagnosisList = new ArrayList<>();
                    for (int i = 0; i < diagnosisList.size(); i++) {
                        if (diagnosisList.get(i).size() >= max) {
                            max = diagnosisList.get(i).size();
                            indexDiagnosisList.add(i);
                        }
                    }

                    ArrayList<Integer> specialties = new ArrayList<>();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("You may have: ").append("\n");
                    for (int i = 0; i < indexDiagnosisList.size(); i++) {
                        int id = diagnosisList.get(indexDiagnosisList.get(i)).get(0).getId();

                        if (!specialties.contains(id)) {
                            specialties.add(id);
                        }

                        stringBuilder.append("\t").append(diagnosisList.get(i).get(0)).append("\n");
                        try {
                            preparedStatement = connection.prepareStatement("insert into Diagnosis (patient_id, disease_id) values (?, ?)");
                            preparedStatement.setInt(1, getId());
                            preparedStatement.setInt(2, diagnosisList.get(i).get(0).getId());
                            preparedStatement.executeUpdate();

                            GUI.selectFromTable("Diagnosis");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }

                    System.out.println(specialties);

                    stringBuilder.append("Recommended doctors: ").append("\n");
                    doctorList.clear();
                    for (int i = 0; i < specialties.size(); i++) {
                        try {
                            resultSet = statement.executeQuery("select * from Doctor");
                            while (resultSet.next()) {
                                if (resultSet.getInt("specialty_id") == specialties.get(i)) {
                                    Doctor doctor = new Doctor(resultSet.getInt(1));
                                    stringBuilder.append("\t").append(doctor.toString()).append("\n");

                                    doctorList.add(doctor);
                                }
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }

                    model.removeAllElements();
                    model.addAll(doctorList);

                    result1.setText(stringBuilder.toString());
                    panel.repaint();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doctorCB.getSelectedIndex() != -1) {
                    Doctor doctor = (Doctor) doctorCB.getSelectedItem();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Full Name: ")
                            .append(doctor.getFName() + " ")
                            .append(doctor.getMName() + " ")
                            .append(doctor.getLName() + " ").append("\n");

                    stringBuilder.append("Date of Birth: ")
                            .append(parseDob(doctor.getDob())[2]).append("/")
                            .append(parseDob(doctor.getDob())[1]).append("/")
                            .append(parseDob(doctor.getDob())[0]).append("\n");

                    stringBuilder.append("Phone Number: ")
                            .append(doctor.getPhone()).append("\n");

                    stringBuilder.append("Email Address: ")
                            .append(doctor.getEmail()).append("\n");

                    stringBuilder.append("Address: ")
                            .append(doctor.getAddress()).append("\n");

                    result2.setText(stringBuilder.toString());
                }
            }
        });
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    preparedStatement = connection.prepareStatement("insert into PatientDoctor (patient_id, doctor_id) values (?, ?)");
                    Doctor doctor = (Doctor) doctorCB.getSelectedItem();
                    preparedStatement.setInt(1, getId());
                    preparedStatement.setInt(2, doctor.getId());
                    preparedStatement.executeUpdate();

                    MyDialog.messageDialog("Booked this doctor!", "DIAGNOSIS", JOptionPane.INFORMATION_MESSAGE, null);
                    GUI.selectFromTable("PatientDoctor");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();

                    MyDialog.messageDialog("Already booked this doctor.", "DIAGNOSIS ERROR", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });
        return panel;
    }

    public JPanel giveFeedback() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        MyLabel toDoctor = new MyLabel("To: ");
        toDoctor.setCommonLabel();

        ArrayList<Doctor> myDoctors = new ArrayList<>();
        ArrayList<Integer> doctorIds = new ArrayList<>();

        MyComboBox<Doctor> comboBox = new MyComboBox<>(myDoctors.toArray(new Doctor[0]));
        comboBox.setSelectedIndex(-1);
        comboBox.setRenderer(new MyComboBoxRenderer("Choose a doctor"));
        DefaultComboBoxModel<Doctor> model = (DefaultComboBoxModel<Doctor>) comboBox.getModel();
        MyTextArea feedback = new MyTextArea(20, 20);
        feedback.setDocument(new MyDocument(1000));

        MyButton send = new MyButton("  Send Feedback  ");
        send.setLeftButton();
        MyButton refresh = new MyButton("  Refresh  ");
        refresh.setRightButton();

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedIndex() != -1) {
                    try {
                        preparedStatement = connection.prepareStatement("insert into Feedback (patient_id, doctor_id, description) values (?, ?, ?)");
                        Doctor doctor = (Doctor) comboBox.getSelectedItem();
                        preparedStatement.setInt(1, getId());
                        preparedStatement.setInt(2, doctor.getId());
                        preparedStatement.setString(3, feedback.getText());
                        preparedStatement.executeUpdate();

                        MyDialog.messageDialog("Successful!", "FEEDBACK", JOptionPane.INFORMATION_MESSAGE, null);
                        GUI.selectFromTable("Feedback");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    refresh.doClick();
                }
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorIds.clear();
                myDoctors.clear();
                try {
                    resultSet = statement.executeQuery("select * from PatientDoctor");
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        if (id == getId()) {
                            doctorIds.add(resultSet.getInt(2));
                        }
                    }

                    for (Integer doctorId : doctorIds) {
                        myDoctors.add(new Doctor(doctorId));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                model.removeAllElements();
                model.addAll(myDoctors);

                feedback.setText("");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(send);
        buttonPanel.add(refresh);

        panel.add(toDoctor, gbc(0, 0, 1, 1, 0, 0.005));
        panel.add(comboBox, gbc(1, 0, 1, 1, 0, 0.005));

        panel.add(feedback, gbc(0, 1, 2, 1, 0, 0.005));

        panel.add(buttonPanel, gbc(0, 2, 2, 1, 0, 0.005));

        refresh.doClick();
        return panel;
    }

    public void setProfile() {
        try {
            resultSet = statement.executeQuery("select * from Patient");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String user = resultSet.getString(9).trim();
                if ((id == getId()) || user.equals(getUsername()) ) {
                    setId(resultSet.getInt(1));
                    setFName(checkNull(resultSet.getString(2)));
                    setMName(checkNull(resultSet.getString(3)));
                    setLName(checkNull(resultSet.getString(4)));
                    setPhone(checkNull(resultSet.getString(5)));
                    setEmail(checkNull(resultSet.getString(6)));
                    setAddress(checkNull(resultSet.getString(7)));
                    setDob(resultSet.getObject(8).toString().trim());
                    setUsername(resultSet.getString(9).trim());
                    setPassword(resultSet.getString(10).trim());
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
}
