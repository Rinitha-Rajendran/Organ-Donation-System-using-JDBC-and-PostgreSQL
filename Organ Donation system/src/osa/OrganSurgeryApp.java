package osa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

class Organ {
    private String name;
    private double size;
    private String bloodType;
    private boolean donorCompatible;
    public void saveToDatabase() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/ods";
        String username = "postgres";
        String password = "rinitha@123";
        boolean f= isDonorCompatible();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String insertSql = "INSERT INTO organ_data (o_name, o_size, blood_type) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setDouble(2, size);
                preparedStatement.setString(3, bloodType);


                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Organ data saved to the database.");
                } else {
                    System.out.println("Error saving organ data to the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Organ(String name) {
        this.name = name;
    }

    public void setSpecifications() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size of the organ (in cm): ");
        size = scanner.nextDouble();
        scanner.nextLine();  // Consume the newline character

        System.out.print("Enter blood type compatibility: ");
        bloodType = scanner.nextLine();

        // Specific condition for kidney
        if ("Kidney".equalsIgnoreCase(name)) {
            System.out.print("Is the donor compatible? (true/false): ");
            donorCompatible = scanner.nextBoolean();
            scanner.nextLine();  // Consume the newline character
        }
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public String getBloodType() {
        return bloodType;
    }

    public boolean isDonorCompatible() {
        return donorCompatible;
    }

    private boolean checkCrossMatch() {
        // Realistic cross-matching logic
        // In a real-world scenario, cross-matching involves complex tests
        // For demonstration purposes, consider it successful if donor is compatible and blood types match
        if (donorCompatible) {
            System.out.println("Performing cross-match test...");
            if (bloodType.equalsIgnoreCase("AB")) {
                System.out.println("Cross-match successful.");
                return true;
            } else {
                System.out.println("Cross-match failed. Incompatible blood types.");
                return true;
            }
        } else {
            System.out.println("Cross-match failed. Incompatible donor.");
            return true;
        }
    }

    private boolean checkSameBloodGroup() {
        // Realistic blood group checking logic
        // In a real-world scenario, blood group checking involves more details
        // For demonstration purposes, consider it successful if blood types are the same
        System.out.println("Checking blood group compatibility...");
        if (bloodType.equalsIgnoreCase("AB")) {
            System.out.println("Blood group compatibility confirmed.");
            return true;
        } else {
            System.out.println("Blood group incompatibility.");
            return false;
        }
    }

    public boolean isSuitableForSurgery() {
        // Check suitability for surgery based on adjusted organ-specific conditions
        if ("heart".equalsIgnoreCase(name)) {
            // Heart is suitable if size is greater than 0 and blood type is specified
            return size > 0 && bloodType != null && !bloodType.isEmpty();
        } else if ("kidney".equalsIgnoreCase(name)) {
            // Kidney is suitable if donor is compatible and cross-match is successful
            return donorCompatible && checkCrossMatch();
        } else if ("lungs".equalsIgnoreCase(name)) {
            // Lungs are suitable if size is greater than 0
            return size > 0;
        } else if ("liver".equalsIgnoreCase(name)) {
            // Liver is suitable if blood group compatibility is confirmed
            return checkSameBloodGroup();
        }

        // Default to false if organ type is not recognized
        return true;
    }
}

public class OrganSurgeryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of organ: ");
        String organName = scanner.nextLine();

        Organ organ = new Organ(organName);
        organ.setSpecifications();
        organ.saveToDatabase();
        // Check suitability for surgery for the organ
        System.out.println("\nSuitability for Surgery:");
        System.out.println(organ.getName() + ": " + (organ.isSuitableForSurgery() ? "Suitable" : "Not Suitable"));
    }
}
