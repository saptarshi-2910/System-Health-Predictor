package disease;

import java.sql.*;
import java.util.ArrayList;

public class Symptom extends DiseaseAbstract {
    public Symptom() {
        super();
        setProfile("Symptom");
    }

    public Symptom(int id) {
        super(id);
        setProfile("Symptom");
    }

    public Symptom(int id, String name, String description) {
        super(id, name, description);
        setProfile("Symptom");
    }

    public static ArrayList<Symptom> getAllSymptoms() {
        ArrayList<Symptom> symptomList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            resultSet = statement.executeQuery("select * from Symptom");

            while (resultSet.next()) {
                String desc = "";
                if (resultSet.getString(3) != null)
                    desc = resultSet.getString(3).trim();
                Symptom s = new Symptom(resultSet.getInt(1),
                        resultSet.getString(2).trim(),
                        desc);
                symptomList.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return symptomList;
    }
}
