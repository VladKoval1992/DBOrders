import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Orders {
    public static void showOrders(Connection conn) throws SQLException {
        try(PreparedStatement pst = conn.prepareStatement("SELECT * FROM Orders")) {
            try (ResultSet rs = pst.executeQuery()) {
                Ordering.displayMetadata(rs);
            }
        }
    }

    public static void addOrders(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter customer's id:");
        int  clientId = scan.nextInt();
        System.out.println("Now enter item's id:");
        int itemId = scan.nextInt();
        System.out.println("Finally enter quantity of a purchased item:");
        int quant = scan.nextInt();
        String query = "INSERT INTO Orders (client_id, item_id, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, clientId);
            ps.setInt(2, itemId);
            ps.setInt(3, quant);
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