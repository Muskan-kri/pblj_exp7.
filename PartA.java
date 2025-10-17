import java.sql.*;

public class PartA {
    public static void main(String[] args) {
        String url = "jdbc:mysql://bytexldb.com:5051/db_43zx2tadw";
        String username = "user_43zx2tadw";
        String password = "p43zx2tadw";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to Database
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to Database Successfully!");

            stmt = conn.createStatement();

            // Insert Employee Record
            String insertQuery = "INSERT INTO Employee (Name, Salary) VALUES ('Riya Sharma', 45000.00)";
            int rowsInserted = stmt.executeUpdate(insertQuery);
            if (rowsInserted > 0) {
                System.out.println("👩‍💼 New employee inserted successfully!");
            }

            // Fetch All Employees
            String selectQuery = "SELECT EmpID, Name, Salary FROM Employee";
            rs = stmt.executeQuery(selectQuery);

            System.out.println("\nEmployee Details:");
            System.out.println("----------------------------");
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println("EmpID: " + empId + " | Name: " + name + " | Salary: " + salary);
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}
