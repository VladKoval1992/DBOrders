import java.sql.*;

public class Ordering {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/dborders";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "1111";

    public Connection initDB() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        try(Statement st = conn.createStatement()){
            st.execute("DROP TABLE IF EXISTS Clients");
            st.execute("DROP TABLE IF EXISTS Items");
            st.execute("DROP TABLE IF EXISTS Orders");
            String query = "CREATE TABLE Clients("+
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
                    "Name VARCHAR(20) NOT NULL, "+
                    "Address VARCHAR(30) NOT NULL, "+
                    "Phone VARCHAR(20) NOT NULL, "+
                    "CreditCard VARCHAR(22) NOT NULL);";
            st.execute(query);
            query = "CREATE TABLE Items("+
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
                    "Name VARCHAR(20) NOT NULL, "+
                    "Weight FLOAT(6,2) NOT NULL, "+
                    "Price FLOAT(8,2) NOT NULL); ";
            st.execute(query);
            query = "CREATE TABLE Orders("+
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
                    "Client_id INT NOT NULL, "+
                    "Item_id INT NOT NULL, "+
                    "Quantity INT NOT NULL);";
            st.execute(query);
        }
        return conn;
    }

    public static void generateReport(Connection conn) throws SQLException {
        String query = "SELECT Orders.id, Clients.name, Clients.address, Clients.phone, Clients.creditcard, " +
                "Items.name, Items.price, orders.quantity, Items.price*orders.quantity AS Total " +
                "FROM Clients, Items, Orders WHERE Orders.client_id=Clients.id AND Orders.item_id = Items.id;";
        try(PreparedStatement pst = conn.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                Ordering.displayMetadata(rs);
            }
        }

    }

    public static void displayMetadata(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        for (int i = 1; i <= md.getColumnCount(); i++)
            System.out.printf("%30s", md.getColumnName(i));
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.printf("%30s", rs.getString(i));
            }
            System.out.println();
        }
        System.out.println();
    }
}