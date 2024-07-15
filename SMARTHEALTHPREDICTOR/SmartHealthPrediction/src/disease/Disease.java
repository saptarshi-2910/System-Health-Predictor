package disease;

import java.sql.SQLException;
import java.util.ArrayList;

public class Disease extends DiseaseAbstract {
    public Disease() {
        super();
        setProfile("Disease");
    }

    public Disease(int id) {
        super(id);
        setProfile("Disease");
    }

    public Disease(int id, String name, String description) {
        super(id, name, description);
        setProfile("Disease");
    }

    public static ArrayList<Disease> getAllDiseases() {
        ArrayList<Disease> diseaseList = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select * from Disease");
            while (resultSet.next()) {
                String desc = "";
                if (resultSet.getString(3) != null)
                    desc = resultSet.getString(3).trim();
                Disease d = new Disease(resultSet.getInt(1),
                        resultSet.getString(2).trim(),
                        desc);
                diseaseList.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return diseaseList;
    }


}
