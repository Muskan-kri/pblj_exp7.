import java.sql.*;
import java.util.Scanner;

public class PartB {
    static String url = "jdbc:mysql://bytexldb.com:5051/db_43zx2tadw";
    static String username = "user_43zx2tadw";
    static String password = "p43zx2tadw";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to database successfully");

            int choice;
            do {
                System.out.println("\n=== PRODUCT MANAGEMENT ===");
                System.out.println("1. Insert Product");
                System.out.println("2. Display All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1 -> insertProduct(conn, sc);
                    case 2 -> displayProducts(conn);
                    case 3 -> updateProduct(conn, sc);
                    case 4 -> deleteProduct(conn, sc);
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } while (choice != 5);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
            sc.close();
        }
    }

    private static void insertProduct(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Product inserted successfully!");
            ps.close();
        } catch (SQLException e) {
            System.out.println("❌ Error inserting: " + e.getMessage());
        }
    }

    private static void displayProducts(Connection conn) {
        try {
            String sql = "SELECT * FROM Product";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Qty");
            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10.2f %-10d%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
            rs.close(); stmt.close();
        } catch (SQLException e) {
            System.out.println("❌ Error displaying: " + e.getMessage());
        }
    }

    private static void updateProduct(Connection conn, Scanner sc) {
        try {
            conn.setAutoCommit(false);
            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter new Price: ");
            double newPrice = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int newQty = sc.nextInt();

            String sql = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newPrice);
            ps.setInt(2, newQty);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product updated successfully!");
            } else {
                conn.rollback();
                System.out.println("⚠️ Product not found!");
            }
            ps.close();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    private static void deleteProduct(Connection conn, Scanner sc) {
        try {
            conn.setAutoCommit(false);
            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM Product WHERE ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product deleted successfully!");
            } else {
                conn.rollback();
                System.out.println("⚠️ Product not found!");
            }
            ps.close();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }
}
