# JAVASQL
การเขียนโปรแกรมภาษา JAVA ทำงานร่วมกับ Database

### **1️⃣ ติดตั้ง Apache NetBeans**
ดาวน์โหลดได้ที่  
🔗 [https://netbeans.apache.org/download/](https://netbeans.apache.org/download/)  

✅ เลือกเวอร์ชันที่มี **Java SE**  

---

### **2️⃣ สร้างโปรเจคใหม่ใน NetBeans**
1. เปิด **NetBeans** แล้วเลือก  
   **File → New Project → Java with Ant → Java Application**  
2. ตั้งชื่อโปรเจค เช่น `SQLiteProject`  
3. เลือก **Create Main Class**  
4. กด **Finish**  

---

### **3️⃣ เพิ่ม SQLite JDBC เข้าไปใน NetBeans**
SQLite ใช้ JDBC Driver (`sqlite-jdbc.jar`)  

🔹 **วิธีเพิ่ม SQLite JDBC ใน NetBeans**  
1. ดาวน์โหลดไฟล์ **sqlite-jdbc.jar** จาก  
   🔗 [https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/)  
2. ใน NetBeans ไปที่  
   **Projects → คลิกขวาที่ Libraries → Add JAR/Folder**  
3. เลือก **sqlite-jdbc.jar** แล้วกด **Open**  
4. ตรวจสอบว่าไลบรารีถูกเพิ่มเข้าไปใน `Libraries` แล้ว  

---

### **4️⃣ คัดลอกโค้ดจาก Replit ไป NetBeans**
เปิดไฟล์ `Main.java` พิมพ์ชุดคำสั่ง  

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.db"; // เชื่อมต่อฐานข้อมูล

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA encoding = 'UTF-8';");  // กำหนดให้สามารถรองรับ UTF8
                System.out.println("เชื่อมต่อ SQLite สำเร็จ!");
                createTable(conn); // สร้างตาราง
            }
        } catch (SQLException e) {
            System.out.println("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }

    // ฟังก์ชันสร้างตาราง
    public static void createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "name TEXT NOT NULL, " +
                     "age INTEGER);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("สร้างตาราง users สำเร็จ!");
        } catch (SQLException e) {
            System.out.println("เกิดข้อผิดพลาดในการสร้างตาราง: " + e.getMessage());
        }
    }
}
```

---

### **5️⃣ ตัวอย่างคำสั่งการอ่านและการบันทึกข้อมูล**
ชุดคำสั่ง

```java
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute("PRAGMA encoding = 'UTF-8';");  // กำหนดให้สามารถรองรับ UTF8
                System.out.println("เชื่อมต่อ SQLite สำเร็จ!");
                insertData(conn, "Alice", 25);
                insertData(conn, "Bob", 30);
                fetchData(conn);
            }
        } catch (SQLException e) {
            System.out.println("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }

    // ฟังก์ชันเพิ่มข้อมูล
    public static void insertData(Connection conn, String name, int age) {
        String sql = "INSERT INTO users (name, age) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
            System.out.println("เพิ่มข้อมูล: " + name);
        } catch (SQLException e) {
            System.out.println("เกิดข้อผิดพลาดในการเพิ่มข้อมูล: " + e.getMessage());
        }
    }

    // ฟังก์ชันดึงข้อมูลจาก SQLite
    public static void fetchData(Connection conn) {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   ", Name: " + rs.getString("name") + 
                                   ", Age: " + rs.getInt("age"));
            }
        } catch (SQLException e) {
            System.out.println("เกิดข้อผิดพลาดในการดึงข้อมูล: " + e.getMessage());
        }
    }
}
```
