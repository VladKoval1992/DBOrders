import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Ordering myOr = new Ordering();
        try (Connection conn = myOr.initDB()) {
            System.out.println("Database created");

            Scanner scan = new Scanner(System.in);
            boolean turn = true;
            while (turn) {
                System.out.println("1: view clients");
                System.out.println("2: add client");
                System.out.println("3: view items");
                System.out.println("4: add item");
                System.out.println("5: view orders");
                System.out.println("6: add orders");
                System.out.println("7: generate report");
                System.out.println("8: exit");
                System.out.print("-> ");
                int input = scan.nextInt();
                switch (input) {
                    case 1:
                        Clients.showClients(conn);
                        break;
                    case 2:
                        Clients.addClients(conn);
                        break;
                    case 3:
                        Items.showItems(conn);
                        break;
                    case 4:
                        Items.addItems(conn);
                        break;
                    case 5:
                        Orders.showOrders(conn);
                        break;
                    case 6:
                        Orders.addOrders(conn);
                        break;
                    case 7:
                        Ordering.generateReport(conn);
                        break;
                    default:
                        turn = false;
                        scan.close();
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}