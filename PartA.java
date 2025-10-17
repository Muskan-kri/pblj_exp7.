import java.sql.*;

public class PartA {
    public static void main(String[] args) {
        String url = "jdbc:mysql://bytexldb.com:5051/db_43zqcp639";
        String username = "user_43zqcp639";
        String password = "p43zqcp639";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1️ Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️ Establish connection
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");

            // 3️ Create a Statement
            stmt = conn.createStatement();

            // 4️ INSERT query (add a new employee record)
            String insertQuery = "INSERT INTO Employee (Name, Salary) VALUES ('Riya Sharma', 45000.00)";
            int rowsInserted = stmt.executeUpdate(insertQuery);
            if (rowsInserted > 0) {
                System.out.println("New employee inserted successfully!");
            }

            // 5️ SELECT query to display all employee data
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

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
