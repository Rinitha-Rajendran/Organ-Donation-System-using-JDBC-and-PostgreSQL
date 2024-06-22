package fam1;
import java.sql.*;
import java.util.Scanner;
public class donar_family1{

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
    public static boolean check_familymember(String donorName, String r_name) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String selectQuery;
        selectQuery = "SELECT * FROM donor WHERE   name = ? AND family_member = ?";
        try {
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, donorName);
            preparedStatement.setString(2, r_name);


            resultSet = preparedStatement.executeQuery();


            return resultSet.next();
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
    public static void main(String[] args){

        System.out.println("enter donor name");
        Scanner scanner=new Scanner(System.in);
        String donorName=scanner.nextLine();
        System.out.println("enter family name");
        String r_name=scanner.nextLine();
        if (check_familymember(donorName,r_name)) {
            System.out.println("Do you give permission for organ donation? (yes/no):");
            String per=scanner.nextLine().toLowerCase();
            while (!(per.equals("yes") || per.equals("no"))) {
                System.out.println("Please enter a valid user type ('yes' or 'no'):");
                per = scanner.nextLine().toLowerCase();
            }
            updatepermission(donorName,per);


        } else {
            System.out.println("Invalid donor_name, family member name");
        }



    }
    public static void updatepermission(String donorName,String per) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        String updatequery = "UPDATE donor SET fmperm = ? WHERE name = ?";

        try {
            preparedStatement = connection.prepareStatement(updatequery);
            preparedStatement.setString(1, per);
            preparedStatement.setString(2, donorName);


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Permission successfully inserted into the database.");
            } else {
                System.out.println("Failed to insert permission into the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection(connection);
        }
    }


}

