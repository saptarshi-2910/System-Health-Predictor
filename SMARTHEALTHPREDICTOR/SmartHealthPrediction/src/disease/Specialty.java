package disease;

import java.sql.*;
import java.util.ArrayList;

public class Specialty {
    private static final String url = "jdbc:sqlserver://localhost;database=SHPS;integratedSecurity=true;";
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    private int id;
    private String name;

    public Specialty(int id) {
        this.id = id;
        getSpecialty();
    }

    public Specialty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void getSpecialty() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Specialty");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                if (this.id == id) {
                    this.name = resultSet.getString(2).trim();
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static ArrayList<Specialty> getAllSpecialties() {
        ArrayList<Specialty> specialtyList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Specialty");

            while (resultSet.next()) {
                Specialty s = new Specialty(resultSet.getInt(1),
                        resultSet.getString(2).trim());
                specialtyList.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return specialtyList;
    }
}
