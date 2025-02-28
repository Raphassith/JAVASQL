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
3. เลือก **Create Main Class** (หรือใช้ `Main.java` จาก Replit)  
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
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database.db"; // เชื่อมต่อฐานข้อมูล

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
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

### **5️⃣ รันโปรแกรมบน NetBeans**
กด **Run (Shift + F6)**  
📌 ถ้าไม่มี Error แสดงว่าโปรเจคทำงานได้ ✅  

---

### **6️⃣ ถ้าใช้ Swing GUI ให้เพิ่ม JFrame**
1. **สร้าง GUI**  
   - **File → New → Swing GUI Form → JFrame Form**  
   - ตั้งชื่อเช่น `UserForm`  
2. ใช้ **Swing GUI Builder** เพื่อเพิ่มปุ่มและช่องกรอกข้อมูล  
3. เขียนโค้ดให้เชื่อมต่อกับ SQLite  

---

## **✅ สรุป**
🔹 **NetBeans รองรับ Java + SQLite + Swing GUI**  
🔹 **เพิ่ม SQLite JDBC (`sqlite-jdbc.jar`) ใน Libraries**  
🔹 **คัดลอกโค้ดจาก Replit มาใช้ได้เลย**  
🔹 **ถ้าใช้ GUI ให้เพิ่ม JFrame**  

ลองทำดู ถ้ามีปัญหาบอกได้เลยครับ! 🚀😊
