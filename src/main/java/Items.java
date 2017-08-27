import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Items {
    public static void showItems(Connection conn) throws SQLException {
        try(PreparedStatement pst = conn.prepareStatement("SELECT * FROM Items")) {
            try (ResultSet rs = pst.executeQuery()) {
                Ordering.displayMetadata(rs);
            }
        }
    }

    public static void addItems(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter item's name:");
        String name = scan.nextLine();
        System.out.println("Now enter item's weight:");
        float weight = scan.nextFloat();
        System.out.println("Now enter item's price:");
        float price = scan.nextFloat();
        String query = "INSERT INTO Items (name, weight, price) VALUES(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setFloat(2, weight);
            ps.setFloat(3, price);
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