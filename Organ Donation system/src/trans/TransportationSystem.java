package trans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

class Organ {
    private String name;

    public Organ(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface Transportable {
    void transport(Organ organ, Hospital hospital);
}

abstract class Vehicle implements Transportable {
    // Common vehicle attributes and methods
}

class Ambulance extends Vehicle {
    @Override
    public void transport(Organ organ, Hospital hospital) {
        System.out.println("Ambulance is delivering " + organ.getName() + " to " + hospital.getName());
        // Additional logic for organ delivery

        // Example: Log the organ delivery to the database
        logOrganDelivery(organ, hospital);
    }

    private void logOrganDelivery(Organ organ, Hospital hospital) {
        // Database connection details
        String jdbcUrl = "jdbc:postgresql://localhost:5432/ods";
        String username = "postgres";
        String password = "rinitha@123";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "INSERT INTO Transportationsystem (organ, hospital) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, organ.getName());
                preparedStatement.setString(2, hospital.getName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Hospital {
    private String name;

    public Hospital(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void receiveOrgan(Organ organ) {
        System.out.println(name + " is receiving the organ: " + organ.getName());
        // Additional logic for organ reception at the hospital
    }
}

class OrganBank {
    public void requestOrgan(Ambulance ambulance, Organ organ, Hospital hospital) {
        // Simulate a hospital requesting an organ
        ambulance.transport(organ, hospital);
    }
}

public class TransportationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input for hospital name
        String hospitalName = getUserInput(scanner, "Enter hospital name: ");
        Hospital hospitalA = new Hospital(hospitalName);

        OrganBank organBank = new OrganBank();
        Ambulance ambulance = new Ambulance();

        // User input for organ type with validation
        String organType = getUserInputWithValidation(scanner, "Enter organ type (Heart, Lungs, Liver, Kidney): ",
                new String[]{"Heart", "Lungs", "Liver", "Kidney"});
        Organ organ = new Organ(organType);

        // Request an organ from the OrganBank for Hospital A using an Ambulance
        organBank.requestOrgan(ambulance, organ, hospitalA);
    }

    private static String getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String getUserInputWithValidation(Scanner scanner, String prompt, String[] validOrgans) {
        String userInput;
        while (true) {
            userInput = getUserInput(scanner, prompt);
            for (String validOrgan : validOrgans) {
                if (userInput.equalsIgnoreCase(validOrgan)) {
                    return userInput;
                }
            }
            System.out.println("Invalid organ type. Please enter a valid organ type.");
        }
    }
}
