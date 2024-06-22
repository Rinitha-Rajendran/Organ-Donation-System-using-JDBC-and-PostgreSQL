import java.sql.*;
import java.util.Scanner;
public class login1 {
    // JDBC URL, username, and password of PostgreSQL server
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ods";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "rinitha@123";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish a database connection: " + e.getMessage());
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean authenticateUser(String email, String password, String userType) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String selectQuery;
        if(userType.equals("d")){
            selectQuery = "SELECT * FROM donor WHERE email = ? AND password = ?";
        }
        else{
            selectQuery = "SELECT * FROM recipient WHERE email = ? AND password = ?";
        }

        try {
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);


            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is a match, the user is authenticated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection(connection);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Press 'd' for donor login or 'r' for recipient login:");
        String userType = scanner.nextLine();

        while (!(userType.equals("d") || userType.equals("r"))) {
            System.out.println("Please enter a valid user type ('d' or 'r'):");
            userType = scanner.nextLine();
        }

        if (authenticateUser(email, password, userType)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email, password, or user type. Login failed.");
        }
    }
}