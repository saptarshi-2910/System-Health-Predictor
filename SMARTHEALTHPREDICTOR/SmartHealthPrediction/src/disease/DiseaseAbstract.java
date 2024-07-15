package disease;

import java.sql.*;

public abstract class DiseaseAbstract {
    public static final String url = "jdbc:sqlserver://localhost;database=SHPS;integratedSecurity=true;";
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

    private int id;
    private String name;
    private String description;
    private int classId;

    public DiseaseAbstract() {

    }

    public DiseaseAbstract(int id) {
        this.id = id;
    }

    public DiseaseAbstract(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClassId() {
        return classId;
    }

    public int getClassId(String profile) {
        try {
            resultSet = statement.executeQuery("select * from " + profile + "Classification");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                if (getId() == id) {
                    setClassId(resultSet.getInt(2));
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void setProfile(String profile) {
        try {
            resultSet = statement.executeQuery("select * from " + profile);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                if (getId() == id) {
                    setName(resultSet.getString(2).trim());
                    if (resultSet.getString(3) == null)
                        setDescription("");
                    else
                        setDescription(resultSet.getString(3).trim());
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
