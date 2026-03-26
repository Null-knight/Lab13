Output of both codes except the output pictures
==================================================

1.PS C:\Users\Rahul\Downloads\Lab13\src> javac com\mypackage\AnimationApp.java
PS C:\Users\Rahul\Downloads\Lab13\src> java com.mypackage.AnimationApp
Current directory: C:\Users\Rahul\Downloads\Lab13\src
Looking for images in: C:\Users\Rahul\Downloads\Lab13\src\..\images
Checking: C:\Users\Rahul\Downloads\Lab13\src\..\images\image1.jpg - Exists? true
? Loaded: ../images/image1.jpg
Checking: C:\Users\Rahul\Downloads\Lab13\src\..\images\image2.jpg - Exists? true
? Loaded: ../images/image2.jpg
Checking: C:\Users\Rahul\Downloads\Lab13\src\..\images\image3.jpg - Exists? true
? Loaded: ../images/image3.jpg
Checking: C:\Users\Rahul\Downloads\Lab13\src\..\images\image4.jpg - Exists? true
? Loaded: ../images/image4.jpg
? Successfully loaded 4 images
?? Animation started
?? Animation stopped
2.PS C:\Users\Rahul\Downloads\Lab13\src> javac -cp ".;../lib/ojdbc11.jar" com\mypackage\StudentRecords.java
PS C:\Users\Rahul\Downloads\Lab13\src> java -cp ".;../lib/ojdbc11.jar" com.mypackage.StudentRecords      
? Oracle Database Connection Tester
=====================================
? Oracle JDBC Driver loaded successfully!

? Testing different connection URLs...
Username: system
----------------------------------------
Test 1: jdbc:oracle:thin:@localhost:1521:XE
   ? SUCCESS! Connected successfully!
   Database: Oracle
   Version: Oracle Database 21c Express Edition Release 21.0.0.0.0 - Production
Version 21.3.0.0.0
   ? Using URL: jdbc:oracle:thin:@localhost:1521:XE

? Connecting to database with working URL...
URL: jdbc:oracle:thin:@localhost:1521:XE
? Connected to Oracle Database successfully!

? Executing query: SELECT * FROM student ORDER BY zip

? Table columns found:
   1. STUDENTID (VARCHAR2)
   2. LAST (VARCHAR2)
   3. FIRST (VARCHAR2)
   4. STREET (VARCHAR2)
   5. CITY (VARCHAR2)
   6. STATE (VARCHAR2)
   7. ZIP (VARCHAR2)
   8. STARTTERM (VARCHAR2)
   9. BIRTHDATE (DATE)
   10. FACULTYID (NUMBER)
   11. MAJORID (NUMBER)
   12. PHONE (VARCHAR2)

? Student Records:
========================================================================================
StudentID  First Name      Last Name       City                 State      Start Term      Faculty    Major
========================================================================================
00105      Amir            Khan            Clifton              NJ         WN03            222        200
00102      Rajesh          Patel           Edison               NJ         WN03            111        400
00103      Deborah         Rickles         Iselin               NJ         FL02            555        500
00100      Jose            Diaz            Hill                 NJ         WN03            123        100
00101      Mickey          Tyler           Bronx                NY         SP03            555        500
00104      Brian           Lee             Hope                 NY         WN03            345        600
========================================================================================
Total records found: 6

? Summary Statistics:
   Total students: 6

   Students by State:
      NY: 2 student(s)
      NJ: 4 student(s)

   Students by Start Term:
      WN03: 4 student(s)
      SP03: 1 student(s)
      FL02: 1 student(s)

? Database connection closed.
