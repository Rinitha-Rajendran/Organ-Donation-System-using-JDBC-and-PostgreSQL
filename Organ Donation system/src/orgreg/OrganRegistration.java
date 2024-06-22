package orgreg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Organ {
    public String organ_name;
    public String blood_group;

    Organ(String organ_name, String blood_group) {
        this.organ_name = organ_name;
        this.blood_group = blood_group;
    }

    public void storeOrganDetails() {

        String jdbcUrl = "jdbc:postgresql://localhost:5432/ods";
        String username = "postgres";
        String password = "rinitha@123";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            String selectDonorSql = "SELECT organ, blood_group FROM donor WHERE fmperm = 'yes'";
            try (PreparedStatement selectDonorStatement = connection.prepareStatement(selectDonorSql)) {
                ResultSet resultSet = selectDonorStatement.executeQuery();

                while (resultSet.next()) {
                    organ_name = resultSet.getString("organ");
                    blood_group = resultSet.getString("blood_group");


                    String insertOrganSql = "INSERT INTO OrganRegistration(organ_name, blood_group) VALUES (?, ?)";
                    try (PreparedStatement insertOrganStatement = connection.prepareStatement(insertOrganSql)) {
                        insertOrganStatement.setString(1, organ_name);
                        insertOrganStatement.setString(2, blood_group);

                        int rowsAffected = insertOrganStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Organ details stored in the database successfully.");
                        } else {
                            System.out.println("Error storing organ details in the database.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class OrganRegistration {
    public static void main(String[] args) {
        Organ organ = new Organ("", ""); // Initialize with empty values


        organ.storeOrganDetails();
    }
}