import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Clients {
    public static void showClients(Connection conn) throws SQLException {
        try(PreparedStatement pst = conn.prepareStatement("SELECT * FROM Clients")) {
            try (ResultSet rs = pst.executeQuery()) {
                Ordering.displayMetadata(rs);
            }
        }
    }

    public static void addClients(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter client's name:");
        String name = scan.nextLine();
        System.out.println("Now enter client's address:");
        String address = scan.nextLine();
        System.out.println("Now enter client's phone number:");
        String phone = scan.nextLine();
        System.out.println("Finally enter client's credit card number:");
        String creditcard = scan.nextLine();
        String query = "INSERT INTO Clients (name, address, phone, creditcard) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.setString(4, creditcard);
            ps.executeUpdate();
            conn.commit();
        } catch (Exception e){
            e.printStackTrace();
            conn.rollback();
        } finally{
            conn.setAutoCommit(true);
        }
    }
}