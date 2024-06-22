package orgav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static log.login.ema;

public class OrganAvailabilityCheck{
    public static boolean organAvailable;

    public static void checkOrganAvailabilityCheck(String recipientName) {

        organAvailable = false;


        String jdbcUrl = "jdbc:postgresql://localhost:5432/ods";
        String username = "postgres";
        String password = "rinitha@123";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            String selectRecipientSql = "SELECT organ, blood_group FROM recipient WHERE email = ?";
            try (PreparedStatement selectRecipientStatement = connection.prepareStatement(selectRecipientSql)) {
                selectRecipientStatement.setString(1, recipientName);
                ResultSet recipientResultSet = selectRecipientStatement.executeQuery();

                if (recipientResultSet.next()) {
                    String recipientOrgan = recipientResultSet.getString("organ");
                    String recipientBloodGroup = recipientResultSet.getString("blood_group");


                    String checkAvailabilitySql = "SELECT * FROM OrganRegistration WHERE organ_name = ? AND blood_group = ?";
                    try (PreparedStatement checkAvailabilityStatement = connection.prepareStatement(checkAvailabilitySql)) {
                        checkAvailabilityStatement.setString(1, recipientOrgan);
                        checkAvailabilityStatement.setString(2, recipientBloodGroup);
                        ResultSet availabilityResultSet = checkAvailabilityStatement.executeQuery();
                        organAvailable = availabilityResultSet.next();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        String recipientName = ema;
        System.out.println(ema);


        OrganAvailabilityCheck.checkOrganAvailabilityCheck(recipientName);

        if (OrganAvailabilityCheck.organAvailable) {
            System.out.println("Organ is available in our database.");
        } else {
            System.out.println("Organ is not available in our database.");
        }

        scanner.close();
    }
}





