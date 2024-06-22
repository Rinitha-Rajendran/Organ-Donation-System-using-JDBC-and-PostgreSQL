import java.sql.*;

public class registration {
    public static void main(String args[]) throws SQLException, ClassNotFoundException
    {

        Class.forName("org.postgresql.Driver");
        Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ods","postgres","rinitha@123");
        Statement st=con.createStatement();
        st.executeUpdate("create table donor(name varchar(50),email varchar(50),password varchar(50),age int,organ varchar(50),blood_group varchar(50),family_member varchar(50),phone_number bigint,fmperm varchar(50))");
        System.out.println("Table is created");
        con.close();



    }
}