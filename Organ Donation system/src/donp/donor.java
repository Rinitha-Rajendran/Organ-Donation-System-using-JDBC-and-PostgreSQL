package donp;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


abstract class person{
    public String name;
    public String email;
    public String password;
    public int age;
    public String organ;
    public String blood_group;
    public String family_member;
    public long phone_number;
}
public class donor extends person {
    donor(String name,String email,String password,int age,String organ,String blood_group,String family_member,long phone_number){
        this.name=name;
        this.email=email;
        this.password=password;
        this.age=age;
        this.organ=organ;
        this.blood_group=blood_group;
        this.family_member=family_member;
        this.phone_number=phone_number;
    }
    public static  boolean af,pf,ef;



    public boolean check_age_compatibility(){
        if(organ.equals("kidney") || organ.equals("liver")){
            if(age>18 && age<70){
                return true;
            }
            else{
                return false;
            }

        }
        else if(organ.equals("heart") || organ.equals("lungs")){
            if(age>18 && age<50){
                return true;
            }
            else{
                return false;
            }

        }
        else{

            System.out.println("pls enter correct organ");
            return false;
        }

    }

    public void registration_status(){
        af=check_age_compatibility();


        if(af){
            String jdbcUrl = "jdbc:postgresql://localhost:5432/ods";
            String username = "postgres";
            String pass_word = "rinitha@123";

            // JDBC objects
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, pass_word)) {
                // Insert data into the database
                String sql = "INSERT INTO donor (name,email,password,age,organ,blood_group,family_member,phone_number) VALUES (?, ?, ?,?, ?, ?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, password);
                    preparedStatement.setInt(4, age);
                    preparedStatement.setString(5, organ);
                    preparedStatement.setString(6, blood_group);
                    preparedStatement.setString(7, family_member);
                    preparedStatement.setLong(8, phone_number);

                    // Execute the update
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Data stored in the database successfully.");
                    } else {
                        System.out.println("Error storing data in the database.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully Register as donor");
        }

        else{
            System.out.println("We cannot accept organ from you due to your age");
        }

    }
    public static boolean getAf() {
        return af;
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    public static boolean isValidPassword(String password) {
        // Minimum 8 characters, at least one uppercase letter, one lowercase letter, and one digit
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return password.matches(passwordRegex);
    }
    public boolean getaf(){
        return af;
    }
    public static void main(String args[]){
        String name;
        String email;
        String password;
        int age;
        //System.out.println("Press d if yor u want to register as donor or Press r if you want to register as recipient ");
        Scanner scanner= new Scanner(System.in);
        // Retrieve the choice from command-line argument
        
        System.out.println("enter name");
        name=scanner.nextLine();
        System.out.println("enter email address");
        email=scanner.nextLine();
        boolean ef1=isValidEmail(email);
        while(!ef1){
            System.out.println("please enter correct email id");
            email=scanner.nextLine();
            ef1=isValidEmail(email);
        }
        System.out.println("enter password");
        password=scanner.nextLine();
        boolean pf1=isValidPassword(password);
        while(!pf1){
            System.out.println("please enter strong password");
            password=scanner.nextLine();
            pf1=isValidPassword(password);
        }
        System.out.println("enter age");
        age=scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter your blood group");
        String blood_group;
        blood_group=scanner.nextLine();
        while(!(blood_group.equals("a+")||blood_group.equals("a-")||blood_group.equals("b+")||blood_group.equals("b-")||blood_group.equals("o+")||blood_group.equals("o-")||blood_group.equals("ab+")||blood_group.equals("ab-"))){
            System.out.println("enter valid blood group");
            blood_group=scanner.nextLine();
        }
        System.out.println("enter your family member name");
        String family_member=scanner.nextLine();
        System.out.println("enter phone number");
        long phone_number=scanner.nextLong();
        int phl=String.valueOf(phone_number).length();
        while(!(phl==10)){
            System.out.println("enter vaild phone number");
            phone_number=scanner.nextInt();
            phl=String.valueOf(phone_number).length();
        }


        
        String organ;
        System.out.println("Enter organ you want to donate(heart or kidney or lungs or liver)");

        organ=scanner.nextLine();
        while (!(organ.equals("heart")||organ.equals("kidney")||organ.equals("liver")||organ.equals("lungs"))) {
                System.out.println("please enter valid organ");
                organ=scanner.nextLine();
        }
        donor d_d=new donor(name,email,password,age,organ,blood_group,family_member,phone_number);
        d_d.registration_status();
        
        
}

}

    


