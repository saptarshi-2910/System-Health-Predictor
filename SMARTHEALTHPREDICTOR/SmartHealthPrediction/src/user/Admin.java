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
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public JPanel viewDoctors() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        ArrayList<String[]> data = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select * from Doctor");

            while (resultSet.next()) {
                String[] row = {String.valueOf(resultSet.getInt(1)),
                        resultSet.getString(10).trim(),
                        resultSet.getString(2).trim() + " " + resultSet.getString(3).trim() + " " + resultSet.getString(4).trim(),
                        resultSet.getString(5).trim(), resultSet.getString(6).trim(), resultSet.getString(7).trim(),
                        resultSet.getString(8).trim(), String.valueOf(resultSet.getInt(9))
                };
                data.add(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (int i = 0; i < data.size(); i++) {
            Specialty specialty = new Specialty(Integer.parseInt(data.get(i)[data.get(i).length - 1]));
            data.get(i)[data.get(i).length - 1] = specialty.getName();
        }
        String[] columnNames = {"ID", "Username", "Name", "Phone", "Email", "Address", "DOB", "Specialty"};

        JTable table = new JTable(data.toArray(new String[0][]), columnNames);
        table.setRowHeight(30);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.setFont(new Font("Helvetica", Font.PLAIN, 20));

        DefaultTableModel model = new DefaultTableModel(data.toArray(new String[0][]), columnNames) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        table.setModel(model);

        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(50);
        tableColumnModel.getColumn(1).setPreferredWidth(125);
        tableColumnModel.getColumn(2).setPreferredWidth(175);

        for (int i = 3; i < tableColumnModel.getColumnCount(); i++) {
            tableColumnModel.getColumn(i).setPreferredWidth(150);
        }

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        tableHeader.setForeground(new Color(29, 182, 242));
        tableHeader.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1100, 700));
        scrollPane.setBackground(Color.BLACK);

        panel.add(scrollPane);
        return panel;
    }

    public JPanel viewPatients() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        ArrayList<String[]> data = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select * from Patient");

            while (resultSet.next()) {
                String[] row = {String.valueOf(resultSet.getInt(1)),
                        resultSet.getString(9).trim(),
                        resultSet.getString(2).trim() + " " + resultSet.getString(3).trim() + " " + resultSet.getString(4).trim(),
                        resultSet.getString(5).trim(), resultSet.getString(6).trim(), resultSet.getString(7).trim(),
                        resultSet.getString(8).trim()
                };
                data.add(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String[] columnNames = {"ID", "Username", "Name", "Phone", "Email", "Address", "DOB"};;

        JTable table = new JTable(data.toArray(new String[0][]), columnNames);
        table.setRowHeight(30);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.setFont(new Font("Helvetica", Font.PLAIN, 20));

        DefaultTableModel model = new DefaultTableModel(data.toArray(new String[0][]), columnNames) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        table.setModel(model);

        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(50);
        tableColumnModel.getColumn(1).setPreferredWidth(125);
        tableColumnModel.getColumn(2).setPreferredWidth(175);

        for (int i = 3; i < tableColumnModel.getColumnCount(); i++) {
            tableColumnModel.getColumn(i).setPreferredWidth(150);
        }

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        tableHeader.setForeground(new Color(29, 182, 242));
        tableHeader.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1100, 700));
        scrollPane.setBackground(Color.BLACK);

        panel.add(scrollPane);
        return panel;
    }

    public JPanel viewDiseases() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        ArrayList<Specialty> specialtyList = new ArrayList<>();
        specialtyList = Specialty.getAllSpecialties();
        MyComboBox<Specialty> specialtyMyComboBox = new MyComboBox<>(specialtyList.toArray(new Specialty[0]));
        specialtyMyComboBox.setSelectedIndex(0);
        specialtyMyComboBox.setRenderer(new MyComboBoxRenderer("Choose a specialty"));
        final Specialty[] specialty = {(Specialty) specialtyMyComboBox.getSelectedItem()};

        MyLabel label = new MyLabel("Diseases in ");
        label.setHeadingLabel();
        MyButton search = new MyButton("  Search Specialty  ");
        search.setLeftButton();
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2.setBackground(Color.BLACK);
        panel2.add(label);
        panel2.add(specialtyMyComboBox);
        panel2.add(search);

        ArrayList<String[]> data = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select * from Disease");

            while (resultSet.next()) {
                String[] row = {String.valueOf(resultSet.getInt(1)), "", ""};
                data.add(row);
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
        String[] columnNames = {"Disease", "Symptom", "Specialty"};

        JTable table = new JTable(data.toArray(new String[0][]), columnNames);
        table.setRowHeight(30);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.setFont(new Font("Helvetica", Font.PLAIN, 20));

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 300));
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

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialty[0] = (Specialty) specialtyMyComboBox.getSelectedItem();
                refresh.doClick();
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data.clear();
                try {
                    resultSet = statement.executeQuery("select * from DiseaseClassification");

                    while (resultSet.next()) {
                        if (resultSet.getInt(2) == specialty[0].getId()) {
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
                        if (id == specialty[0].getId()) {
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
                            preparedStatement.setInt(2, specialty[0].getId());
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

    public JPanel viewFeedback() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridBagLayout());

        ArrayList<String[]> data = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select * from Feedback");

            while (resultSet.next()) {
                String[] row = {String.valueOf(resultSet.getInt(1)), String.valueOf(resultSet.getInt(2)), resultSet.getString(3).trim(), resultSet.getString(4).trim()};
                data.add(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (int i = 0; i < data.size(); i++) {
            Patient patient = new Patient(Integer.parseInt(data.get(i)[0]));
            Doctor doctor = new Doctor(Integer.parseInt(data.get(i)[1]));
            data.get(i)[0] += " " + patient.getUsername();
            data.get(i)[1] += " " + doctor.getUsername();
        }
        String[] columnNames = {"Patient", "Doctor", "Feedback", "Date"};

        JTable table = new JTable(data.toArray(new String[0][]), columnNames);
        table.setRowHeight(30);
        table.setForeground(Color.WHITE);
        table.setBackground(Color.BLACK);
        table.setFont(new Font("Helvetica", Font.PLAIN, 20));

        DefaultTableModel model = new DefaultTableModel(data.toArray(new String[0][]), columnNames) {
            boolean[] canEdit = new boolean[]{false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        table.setModel(model);

        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(100);
        tableColumnModel.getColumn(1).setPreferredWidth(100);
        tableColumnModel.getColumn(2).setPreferredWidth(200);
        tableColumnModel.getColumn(3).setPreferredWidth(100);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Helvetica", Font.BOLD, 20));
        tableHeader.setForeground(new Color(29, 182, 242));
        tableHeader.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1100, 700));
        scrollPane.setBackground(Color.BLACK);

        panel.add(scrollPane);

        return panel;
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
