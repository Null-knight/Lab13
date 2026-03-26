package com.mypackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecords {
    
    // Student class to hold record data (updated with correct column names)
    static class Student {
        String studentId;    // Student ID (STUDENTID)
        String lastName;     // Last Name (LAST)
        String firstName;    // First Name (FIRST)
        String street;       // Street address (STREET)
        String city;         // City (CITY)
        String state;        // State (STATE)
        String zip;          // ZIP code (ZIP)
        String startTerm;    // Start term (STARTTERM)
        Date birthDate;      // Birth date (BIRTHDATE)
        int facultyId;       // Faculty ID (FACULTYID) - NUMBER type
        int majorId;         // Major ID (MAJORID) - NUMBER type
        String phone;        // Phone number (PHONE)
        
        public Student(String studentId, String lastName, String firstName, String street, 
                      String city, String state, String zip, String startTerm, 
                      Date birthDate, int facultyId, int majorId, String phone) {
            this.studentId = studentId;
            this.lastName = lastName;
            this.firstName = firstName;
            this.street = street;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.startTerm = startTerm;
            this.birthDate = birthDate;
            this.facultyId = facultyId;
            this.majorId = majorId;
            this.phone = phone;
        }
        
        @Override
        public String toString() {
            return String.format("ID: %s, Name: %s %s, City: %s, Phone: %s, Faculty: %d, Major: %d", 
                               studentId, firstName, lastName, city, phone, facultyId, majorId);
        }
    }
    
    public static void main(String[] args) {
        // UPDATE THESE WITH YOUR CREDENTIALS
        String username = "system"; // Your Oracle username
        String password = "sara"; // YOUR ACTUAL PASSWORD
        
        System.out.println("🔍 Oracle Database Connection Tester");
        System.out.println("=====================================");
        
        // Step 1: Load driver
        if (!loadDriver()) {
            return;
        }
        
        // Step 2: Test all possible URLs to find the working one
        String workingUrl = findWorkingUrl(username, password);
        
        if (workingUrl == null) {
            System.out.println("\n❌ Could not connect with any URL.");
            return;
        }
        
        // Step 3: Connect with working URL and perform operations
        performDatabaseOperations(workingUrl, username, password);
    }
    
    private static boolean loadDriver() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("✅ Oracle JDBC Driver loaded successfully!");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Oracle JDBC Driver not found!");
            System.out.println("Make sure ojdbc11.jar is properly added to the project.");
            return false;
        }
    }
    
    private static String findWorkingUrl(String username, String password) {
        // Array of possible URLs to test
        String[] urlsToTest = {
            "jdbc:oracle:thin:@localhost:1521:XE",      // Most common for XE
            "jdbc:oracle:thin:@localhost:1521:XEPDB1",  // For pluggable database
            "jdbc:oracle:thin:@localhost:XE",           // Without port
            "jdbc:oracle:thin:@//localhost:1521/XE",    // Service name format
            "jdbc:oracle:thin:@//localhost:1521/XEPDB1" // Service name for PDB
        };
        
        System.out.println("\n🔍 Testing different connection URLs...");
        System.out.println("Username: " + username);
        System.out.println("----------------------------------------");
        
        for (int i = 0; i < urlsToTest.length; i++) {
            String testUrl = urlsToTest[i];
            System.out.printf("Test %d: %s\n", i+1, testUrl);
            
            try {
                Connection testConn = DriverManager.getConnection(testUrl, username, password);
                System.out.println("   ✅ SUCCESS! Connected successfully!");
                
                DatabaseMetaData metaData = testConn.getMetaData();
                System.out.println("   Database: " + metaData.getDatabaseProductName());
                System.out.println("   Version: " + metaData.getDatabaseProductVersion());
                
                testConn.close();
                System.out.println("   ✅ Using URL: " + testUrl);
                return testUrl;
                
            } catch (SQLException e) {
                String errorMsg = "   ❌ Failed: ";
                if (e.getErrorCode() == 1017) {
                    errorMsg += "Invalid username/password";
                } else if (e.getErrorCode() == 12505) {
                    errorMsg += "Invalid SID/Service name";
                } else if (e.getErrorCode() == 17002) {
                    errorMsg += "Database not running";
                } else {
                    errorMsg += e.getMessage().substring(0, Math.min(50, e.getMessage().length()));
                }
                System.out.println(errorMsg);
            }
        }
        
        return null;
    }
    
    private static void performDatabaseOperations(String url, String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();
        
        try {
            System.out.println("\n📊 Connecting to database with working URL...");
            System.out.println("URL: " + url);
            
            // Establish connection
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to Oracle Database successfully!");
            
            // Create statement
            stmt = conn.createStatement();
            
            // Execute query to fetch all student records
            String query = "SELECT * FROM student ORDER BY zip";
            System.out.println("\n📊 Executing query: " + query);
            
            rs = stmt.executeQuery(query);
            
            // Get result set metadata
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            
            System.out.println("\n📋 Table columns found:");
            for (int i = 1; i <= colCount; i++) {
                System.out.println("   " + i + ". " + rsmd.getColumnName(i) + 
                                 " (" + rsmd.getColumnTypeName(i) + ")");
            }
            
            System.out.println("\n📋 Student Records:");
            System.out.println("========================================================================================");
            System.out.printf("%-10s %-15s %-15s %-20s %-10s %-15s %-10s %-10s\n", 
                            "StudentID", "First Name", "Last Name", "City", "State", "Start Term", "Faculty", "Major");
            System.out.println("========================================================================================");
            
            // Process each row using the correct column names from your database
            while (rs.next()) {
                // Get values using actual column names from your table
                String studentId = rs.getString("STUDENTID");
                String lastName = rs.getString("LAST");
                String firstName = rs.getString("FIRST");
                String street = rs.getString("STREET");
                String city = rs.getString("CITY");
                String state = rs.getString("STATE");
                String zip = rs.getString("ZIP");
                String startTerm = rs.getString("STARTTERM");
                Date birthDate = rs.getDate("BIRTHDATE");
                int facultyId = rs.getInt("FACULTYID");
                int majorId = rs.getInt("MAJORID");
                String phone = rs.getString("PHONE");
                
                Student student = new Student(studentId, lastName, firstName, street, 
                                            city, state, zip, startTerm, 
                                            birthDate, facultyId, majorId, phone);
                students.add(student);
                
                // Display in formatted table
                System.out.printf("%-10s %-15s %-15s %-20s %-10s %-15s %-10d %-10d\n", 
                                studentId, firstName, lastName, city, state, startTerm, facultyId, majorId);
            }
            
            System.out.println("========================================================================================");
            System.out.println("Total records found: " + students.size());
            
            // Display summary statistics
            if (!students.isEmpty()) {
                System.out.println("\n📈 Summary Statistics:");
                System.out.println("   Total students: " + students.size());
                
                // Count students by state
                System.out.println("\n   Students by State:");
                java.util.Map<String, Integer> stateCount = new java.util.HashMap<>();
                for (Student s : students) {
                    stateCount.put(s.state, stateCount.getOrDefault(s.state, 0) + 1);
                }
                for (java.util.Map.Entry<String, Integer> entry : stateCount.entrySet()) {
                    System.out.println("      " + entry.getKey() + ": " + entry.getValue() + " student(s)");
                }
                
                // Count students by start term
                System.out.println("\n   Students by Start Term:");
                java.util.Map<String, Integer> termCount = new java.util.HashMap<>();
                for (Student s : students) {
                    termCount.put(s.startTerm, termCount.getOrDefault(s.startTerm, 0) + 1);
                }
                for (java.util.Map.Entry<String, Integer> entry : termCount.entrySet()) {
                    System.out.println("      " + entry.getKey() + ": " + entry.getValue() + " student(s)");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Database error occurred!");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) {
                    conn.close();
                    System.out.println("\n✅ Database connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}