package user;

import disease.Disease;
import disease.Specialty;
import disease.Symptom;
import gui.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Doctor extends User {
    private int specialtyId;

    public Doctor(int id) {
        super(id);
        setProfile();
    }

    public Doctor(String username, String password) {
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

        MyLabel specialtyLabel = new MyLabel("My Specialty:  ");
        specialtyLabel.setCommonLabel();

        MyComboBox<Specialty> specialtyCB = new MyComboBox<>(Specialty.getAllSpecialties().toArray(new Specialty[0]));
        specialtyCB.setSelectedIndex(-1);
        specialtyCB.setRenderer(new MyComboBoxRenderer(new Specialty(specialtyId).toString()));

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

        panel.add(specialtyLabel, gbc(0, 5, 1, 1, 0, 0.005));
        panel.add(specialtyCB, gbc(1, 5, 1, 1, 0, 0.005));

        MyButton update = new MyButton("  Update Profile  ");
        update.setLeftButton();
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultSet = statement.executeQuery("select * from Doctor");

                    while (resultSet.next()) {
                        if (resultSet.getString("username").trim().equals(getUsername())) {
                            preparedStatement = connection.prepareStatement("update Doctor " +
                                    "set d_firstname = ?, d_middlename = ?, d_lastname = ?, " +
                                    "phone = ?, email = ?, address = ?, " +
                                    "dob = ?, " +
                                    "specialty_id = ? " +
                                    "where username = ?");
                            preparedStatement.setString(9, getUsername());

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

                            if (specialtyCB.getSelectedIndex() != -1)
                                preparedStatement.setInt(8, specialtyCB.getSelectedIndex() + 1);
                            else
                                preparedStatement.setInt(8, specialtyId);
                            preparedStatement.executeUpdate();

                            MyDialog.messageDialog("Successful!", "UPDATE PROFILE", JOptionPane.INFORMATION_MESSAGE, null);
                            GUI.selectFromTable("Doctor");
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

    public JPanel viewMyPatients() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());
        ArrayList<Patient> patientList = new ArrayList<>();
        ArrayList<Integer> pId = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select * from PatientDoctor");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                if (resultSet.getInt(2) == getId())
                    pId.add(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (Integer id : pId) {
            patientList.add(new Patient(id));
        }

        MyLabel label1 = new MyLabel("You have " + patientList.size() + " patients.");
        label1.setForeground(new Color(29, 182, 242));
        label1.setFont(new Font("Helvetica", Font.BOLD, 30));

        MyComboBox<Patient> comboBox = new MyComboBox<>(patientList.toArray(new Patient[0]));
        comboBox.setSelectedIndex(0);

        MyButton searchButton = new MyButton("  Search Patient  ");
        searchButton.setLeftButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(comboBox);
        buttonPanel.add(searchButton);

        MyTextArea patientInfo = new MyTextArea(20, 5);
        patientInfo.setEditable(false);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder stringBuilder = new StringBuilder();
                Patient patient = (Patient) comboBox.getSelectedItem();
                try {
                    resultSet = statement.executeQuery("select * from PatientDoctor");
                    while (resultSet.next()) {
                        int p = resultSet.getInt(1);
                        int d = resultSet.getInt(2);
                        if ((p == patient.getId()) && (d == getId())) {
                            stringBuilder.append("Full Name: ")
                                    .append(patient.getFName() + " ")
                                    .append(patient.getMName() + " ")
                                    .append(patient.getLName() + " ").append("\n");

                            stringBuilder.append("Date of Birth: ")
                                    .append(parseDob(patient.getDob())[2]).append("/")
                                    .append(parseDob(patient.getDob())[1]).append("/")
                                    .append(parseDob(patient.getDob())[0]).append("\n");

                            stringBuilder.append("Phone Number: ")
                                    .append(patient.getPhone()).append("\n");

                            stringBuilder.append("Email Address: ")
                                    .append(patient.getEmail()).append("\n");

                            stringBuilder.append("Address: ")
                                    .append(patient.getAddress()).append("\n");
                            break;
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                stringBuilder.append("\n").append("Feedback:").append("\n");

                try {
                    resultSet = statement.executeQuery("select * from Feedback");
                    while (resultSet.next()) {
                        int p = resultSet.getInt(1);
                        int d = resultSet.getInt(2);
                        if ((p == patient.getId()) && (d == getId())) {
                            String[] date = parseDob(resultSet.getObject(4).toString().trim());
                            stringBuilder.append("On " + date[2] + "/" + date[1] + "/" + date[0] + ": ").append("\n");
                            stringBuilder.append("\t").append(resultSet.getString(3).trim()).append("\n\n");
                        }
                    }

                    patientInfo.setText(String.valueOf(stringBuilder));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                panel.repaint();
            }
        });
        JScrollPane scroll1 = new JScrollPane(patientInfo);

        panel.add(label1, gbc(0, 0, 1, 1, 0, 0));
        panel.add(buttonPanel, gbc(0, 1, 1, 1, 0, 0));
        panel.add(scroll1, gbc(0, 2, 1, 1, 0, 0));
        return panel;
    }

    public JPanel viewDiseases() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        Specialty specialty = new Specialty(getSpecialtyId());

        MyLabel label = new MyLabel("Diseases in " + specialty.getName());
        label.setHeadingLabel();
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.BLACK);
        panel2.add(label);

        ArrayList<String[]> data = new ArrayList<>();
        String[] columnNames = {"Disease", "Symptom", "Specialty"};

        JTable table = new JTable();
        table.setRowHeight(30);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.setFont(new Font("Helvetica", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1100, 300));
        scrollPane.setBackground(Color.BLACK);

        MyLabel l1 = new MyLabel("Name: ");
        l1.setCommonLabel();
        MyLabel l2 = new MyLabel("Description: ");
        l2.setCommonLabel();

        ArrayList<Integer> diseaseIds = new ArrayList<>();
        ArrayList<Disease> diseaseList = new ArrayList<>();
        MyComboBox<Disease> diseaseCB = new MyComboBox<>(diseaseList.toArray(new Disease[0]));
        diseaseCB.setSelectedIndex(-1);
        diseaseCB.setRenderer(new MyComboBoxRenderer("Choose a disease"));
        DefaultComboBoxModel<Disease> dModel = (DefaultComboBoxModel<Disease>) diseaseCB.getModel();

        MyTextArea nameArea = new MyTextArea(3, 20);
        nameArea.setDocument(new MyDocument(20));
        MyTextArea descArea = new MyTextArea(3, 20);
        descArea.setDocument(new MyDocument(1000));
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.BLACK);
        panel3.setLayout(new GridBagLayout());
        panel3.add(diseaseCB, gbc(1, 0, 1, 1, 0, 0.3));
        panel3.add(new MyLabel(" "), gbc(1, 1, 1, 1, 0, 0.1));
        panel3.add(nameArea, gbc(1, 2, 1, 1, 0, 0.2));
        panel3.add(l1, gbc(0, 2, 1, 1, 0, 0.2));
        panel3.add(new MyLabel(" "), gbc(1, 3, 1, 1, 0, 0.1));
        panel3.add(descArea, gbc(1, 4, 1, 1, 0, 0.3));
        panel3.add(l2, gbc(0, 4, 1, 1, 0, 0.3));

        MyButton addDisease = new MyButton("  Add Disease  ");
        addDisease.setLeftButton();
        MyButton refresh = new MyButton("  Refresh  ");
        refresh.setRightButton();
        MyButton addSymptom = new MyButton("  Add Symptom  ");
        addSymptom.setLeftButton();
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.BLACK);
        panel4.setLayout(new GridBagLayout());
        panel4.add(addDisease, gbc(0, 0, 1, 1, 0, 0.2));
        panel4.add(new MyLabel(" "), gbc(0, 1, 1, 1, 0, 0.2));
        panel4.add(refresh, gbc(0, 2, 1, 1, 0, 0.2));
        panel4.add(new MyLabel(" "), gbc(0, 3, 1, 1, 0, 0.2));
        panel4.add(addSymptom, gbc(0, 4, 1, 1, 0, 0.2));

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data.clear();
                try {
                    resultSet = statement.executeQuery("select * from DiseaseClassification");

                    while (resultSet.next()) {
                        if (resultSet.getInt(2) == getSpecialtyId()) {
                            String[] row = {String.valueOf(resultSet.getInt(1)), "", ""};
                            data.add(row);
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                for (int i = 0; i < data.size(); i++) {
                    Disease disease = new Disease(Integer.parseInt(data.get(i)[0]));
                    data.get(i)[0] += " " + disease.getName();

                    ArrayList<Integer> symptomIds = new ArrayList<>();
                    try {
                        resultSet = statement.executeQuery("select * from SymptomClassification");
                        while (resultSet.next()) {
                            if (resultSet.getInt(2) == disease.getId()) {
                                symptomIds.add(resultSet.getInt(1));
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    ArrayList<Symptom> symptoms = new ArrayList<>();
                    for (int j = 0; j < symptomIds.size(); j++) {
                        Symptom symptom = new Symptom(symptomIds.get(j));
                        symptoms.add(symptom);
                        data.get(i)[1] += symptom.getId() + ": " + symptom.getName() + " ";
                    }

                    Specialty sp = new Specialty(disease.getClassId("Disease"));
                    data.get(i)[2] = sp.getId() + ": " + sp.getName();
                }

                DefaultTableModel model = new DefaultTableModel(data.toArray(new String[0][]), columnNames) {
                    boolean[] canEdit = new boolean[]{false, false, false};

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
                table.setModel(model);

                TableColumnModel tableColumnModel = table.getColumnModel();
                tableColumnModel.getColumn(0).setPreferredWidth(200);
                tableColumnModel.getColumn(1).setPreferredWidth(700);
                tableColumnModel.getColumn(2).setPreferredWidth(200);

                JTableHeader tableHeader = table.getTableHeader();
                tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
                tableHeader.setForeground(new Color(29, 182, 242));
                tableHeader.setBackground(Color.BLACK);

                nameArea.setText("");
                descArea.setText("");

                try {
                    diseaseIds.clear();
                    diseaseList.clear();

                    dModel.removeAllElements();

                    resultSet = statement.executeQuery("select * from DiseaseClassification");
                    while (resultSet.next()) {
                        int id = resultSet.getInt(2);
                        if (id == specialty.getId()) {
                            diseaseIds.add(resultSet.getInt(1));
                        }
                    }

                    for (Integer id : diseaseIds) {
                        diseaseList.add(new Disease(id));
                    }

                    dModel.addAll(diseaseList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        addDisease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameArea.getText().isEmpty()) {
                    ArrayList<Disease> allDiseases = Disease.getAllDiseases();
                    boolean flag = true;
                    for (Disease aDisease : allDiseases) {
                        if (nameArea.getText().equalsIgnoreCase(aDisease.getName())) {
                            flag = false;
                            MyDialog.messageDialog("Repeated disease.\nAdd a new one.", "DISEASE ERROR", JOptionPane.ERROR_MESSAGE, null);
                            break;
                        }
                    }
                    if (flag) {
                        try {
                            preparedStatement = connection.prepareStatement("insert into Disease (d_name, description) values (?, ?)");
                            preparedStatement.setString(1, nameArea.getText());
                            preparedStatement.setString(2, descArea.getText());
                            preparedStatement.executeUpdate();
                            int id = -1;
                            resultSet = statement.executeQuery("select * from Disease");
                            while (resultSet.next()) {
                                id = resultSet.getInt(1);
                            }
                            Disease disease = new Disease(id);
                            preparedStatement = connection.prepareStatement("insert into DiseaseClassification (disease_id, specialty_id) values (?, ?)");
                            preparedStatement.setInt(1, disease.getId());
                            preparedStatement.setInt(2, specialty.getId());
                            preparedStatement.executeUpdate();
                            GUI.selectFromTable("Disease");
                            GUI.selectFromTable("DiseaseClassification");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        MyDialog.messageDialog("Added successfully.", "DISEASE", JOptionPane.INFORMATION_MESSAGE, null);
                        refresh.doClick();
                    }
                }
            }
        });
        addSymptom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameArea.getText().isEmpty()) {
                    ArrayList<Symptom> allSymptoms = Symptom.getAllSymptoms();
                    boolean flag = true;
                    for (Symptom aSymptom : allSymptoms) {
                        if (nameArea.getText().equalsIgnoreCase(aSymptom.getName())) {
                            flag = false;
                            MyDialog.messageDialog("Repeated symptom.\nAdd a new one.", "SYMPTOM ERROR", JOptionPane.ERROR_MESSAGE, null);
                            break;
                        }
                    }
                    if (flag) {
                        try {
                            preparedStatement = connection.prepareStatement("insert into Symptom (s_name, s_description) values (?, ?)");
                            preparedStatement.setString(1, nameArea.getText());
                            preparedStatement.setString(2, descArea.getText());
                            preparedStatement.executeUpdate();
                            int id = -1;
                            resultSet = statement.executeQuery("select * from Symptom");
                            while (resultSet.next()) {
                                id = resultSet.getInt(1);
                            }
                            Disease disease = (Disease) diseaseCB.getSelectedItem();
                            Symptom symptom = new Symptom(id);
                            preparedStatement = connection.prepareStatement("insert into SymptomClassification (symptom_id, disease_id) values (?, ?)");
                            preparedStatement.setInt(1, symptom.getId());
                            preparedStatement.setInt(2, disease.getId());
                            preparedStatement.executeUpdate();

                            GUI.selectFromTable("Symptom");
                            GUI.selectFromTable("SymptomClassification");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        MyDialog.messageDialog("Added successfully.", "SYMPTOM", JOptionPane.INFORMATION_MESSAGE, null);
                        refresh.doClick();
                    }
                }
            }
        });

        panel.add(panel2, gbc(0, 0, 5, 1, 0, 0));

        panel.add(new MyLabel(" "), gbc(0, 1, 1, 1, 0.1, 0.4));
        panel.add(scrollPane, gbc(1, 1, 3, 1, 0.8, 0.4));
        panel.add(new MyLabel(" "), gbc(4, 1, 1, 1, 0.1, 0.4));

        panel.add(panel3, gbc(1, 3, 2, 1, 0.7, 0.6));
        panel.add(panel4, gbc(3, 3, 1, 1, 0.3, 0.6));

        refresh.doClick();

        return panel;
    }

    public void setProfile() {
        try {
            resultSet = statement.executeQuery("select * from Doctor");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String user = resultSet.getString(10).trim();
                if ((id == getId()) || user.equals(getUsername()) ) {
                    setId(resultSet.getInt(1));
                    setFName(checkNull(resultSet.getString(2)));
                    setMName(checkNull(resultSet.getString(3)));
                    setLName(checkNull(resultSet.getString(4)));
                    setPhone(checkNull(resultSet.getString(5)));
                    setEmail(checkNull(resultSet.getString(6)));
                    setAddress(checkNull(resultSet.getString(7)));
                    setDob(resultSet.getObject(8).toString().trim());
                    this.specialtyId = resultSet.getInt(9);
                    setUsername(resultSet.getString(10).trim());
                    setPassword(resultSet.getString(11).trim());
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public static GridBagConstraints gbc(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty) {
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
