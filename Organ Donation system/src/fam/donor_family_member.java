package fam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class donor_family_member {
    private String donorName;
    private String name;
    private int age;
    private String relationship;

    public donor_family_member(String donorName, String name, int age, String relationship) {
        this.donorName = donorName;
        this.name = name;
        this.age = age;
        this.relationship = relationship;
    }



    public void insertFamilyMemberDetails() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/ods";
        String username = "postgres";
        String passWord = "rinitha@123";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, passWord)) {
            // Insert data into donor_family_member table
            String insertFamilyMemberSql = "INSERT INTO donor_family_member (donorname, name, age, relationship) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertFamilyMemberSql)) {
                preparedStatement.setString(1, donorName);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, relationship);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Family member details stored successfully.");
                } else {
                    System.out.println("Error storing family member details.");
                }
            }

            // Update donor table with permission information
            String updateDonorSql = "UPDATE donor SET fmperm = ? WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateDonorSql)) {
                System.out.print("Do you give permission for organ donation? (yes/no): ");
                String permission = new Scanner(System.in).nextLine().toLowerCase();

                preparedStatement.setBoolean(1, permission.equals("yes"));
                preparedStatement.setString(2, donorName);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Permission information updated successfully.");
                } else {
                    System.out.println("Error updating permission information.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter donor name:");
        String donorName = scanner.nextLine();

        System.out.println("Enter family member details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Relationship to the family: ");
        String relationship = scanner.nextLine();

        donor_family_member familyMember = new donor_family_member(donorName, name, age, relationship);
        familyMember.insertFamilyMemberDetails();
    }
}
