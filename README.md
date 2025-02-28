# JAVA SQL
การเขียนโปรแกรมภาษา JAVA ทำงานร่วมกับ Database

# JAVA กับ SQLite

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
            conn.close();   // หยุดการเชื่อมต่อฐานข้อมูล
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
                conn.close();   // หยุดการเชื่อมต่อฐานข้อมูล
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

---

### **📌 คำสั่งสำหรับจัดการโครงสร้างตารางใน SQLite**  
SQLite ใช้ **SQL** มาตรฐานในการ **สร้าง (CREATE), ลบ (DROP), และแก้ไข (ALTER)** ตารางข้อมูล  

---

## **1️⃣ สร้างตาราง (`CREATE TABLE`)**  
ใช้เมื่อ **ต้องการสร้างตารางใหม่** ในฐานข้อมูล  

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,  -- รหัสอัตโนมัติ
    name TEXT NOT NULL,  -- ชื่อ (ต้องมีค่า)
    age INTEGER,  -- อายุ
    email TEXT UNIQUE  -- อีเมล (ต้องไม่ซ้ำกัน)
);
```

📌 **หมายเหตุ:**  
✅ `INTEGER PRIMARY KEY AUTOINCREMENT` → รันเลขอัตโนมัติ  
✅ `TEXT NOT NULL` → ห้ามเว้นว่าง  
✅ `UNIQUE` → ห้ามข้อมูลซ้ำ  

**🔹 ตัวอย่างโค้ด Java**
```java
String sql = "CREATE TABLE IF NOT EXISTS users ("
           + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
           + "name TEXT NOT NULL, "
           + "age INTEGER, "
           + "email TEXT UNIQUE);";
try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
     Statement stmt = conn.createStatement()) {
    stmt.execute(sql);
    System.out.println("สร้างตาราง users สำเร็จ!");
} catch (SQLException e) {
    System.out.println("เกิดข้อผิดพลาด: " + e.getMessage());
}
```

---

## **2️⃣ ลบตาราง (`DROP TABLE`)**  
ใช้เมื่อ **ต้องการลบตารางออกจากฐานข้อมูล**  

```sql
DROP TABLE IF EXISTS users;
```

📌 **หมายเหตุ:**  
- `IF EXISTS` → ป้องกัน Error ถ้าตารางไม่มีอยู่  
- คำสั่งนี้ **ลบข้อมูลทั้งหมดในตารางด้วย!**  

**🔹 ตัวอย่างโค้ด Java**
```java
String sql = "DROP TABLE IF EXISTS users;";
try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
     Statement stmt = conn.createStatement()) {
    stmt.execute(sql);
    System.out.println("ลบตาราง users สำเร็จ!");
} catch (SQLException e) {
    System.out.println("เกิดข้อผิดพลาด: " + e.getMessage());
}
```

---

## **3️⃣ แก้ไขโครงสร้างตาราง (`ALTER TABLE`)**  
### **🟢 เพิ่มคอลัมน์ (`ADD COLUMN`)**  
```sql
ALTER TABLE users ADD COLUMN phone TEXT;
```
📌 **หมายเหตุ:**  
- คำสั่งนี้ **ใช้เพิ่มคอลัมน์ใหม่เท่านั้น**  
- SQLite **ไม่รองรับ** การลบหรือแก้ไขชนิดข้อมูลโดยตรง  

**🔹 ตัวอย่างโค้ด Java**
```java
String sql = "ALTER TABLE users ADD COLUMN phone TEXT;";
try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
     Statement stmt = conn.createStatement()) {
    stmt.execute(sql);
    System.out.println("เพิ่มคอลัมน์ phone สำเร็จ!");
} catch (SQLException e) {
    System.out.println("เกิดข้อผิดพลาด: " + e.getMessage());
}
```

---

### **🛑 วิธีแก้ไขหรือลบคอลัมน์ใน SQLite**
เนื่องจาก **SQLite ไม่รองรับ `DROP COLUMN` หรือ `MODIFY COLUMN`**  
✅ วิธีแก้คือ **สร้างตารางใหม่ → คัดลอกข้อมูล → ลบตารางเก่า**  

**🔹 ตัวอย่าง: ลบคอลัมน์ `email` ออกจาก `users`**  
```sql
BEGIN TRANSACTION;

CREATE TABLE users_new (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    age INTEGER
);

INSERT INTO users_new (id, name, age)
SELECT id, name, age FROM users;

DROP TABLE users;

ALTER TABLE users_new RENAME TO users;

COMMIT;
```

📌 **หมายเหตุ:**  
- **สร้างตารางใหม่** (ไม่มี `email`)  
- **คัดลอกข้อมูลเดิม** มาใส่  
- **ลบตารางเก่า แล้วเปลี่ยนชื่อตารางใหม่**  

**🔹 ตัวอย่างโค้ด Java**
```java
String[] sqls = {
    "CREATE TABLE users_new (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER);",
    "INSERT INTO users_new (id, name, age) SELECT id, name, age FROM users;",
    "DROP TABLE users;",
    "ALTER TABLE users_new RENAME TO users;"
};
try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
     Statement stmt = conn.createStatement()) {
    for (String sql : sqls) {
        stmt.execute(sql);
    }
    System.out.println("ลบคอลัมน์ email สำเร็จ!");
} catch (SQLException e) {
    System.out.println("เกิดข้อผิดพลาด: " + e.getMessage());
}
```

---

## **✅ สรุป**
| คำสั่ง | คำอธิบาย |
|--------|----------|
| `CREATE TABLE` | สร้างตารางใหม่ |
| `DROP TABLE` | ลบตารางออกจากฐานข้อมูล |
| `ALTER TABLE ADD COLUMN` | เพิ่มคอลัมน์ใหม่ |
| **การลบ/แก้ไขคอลัมน์** | ต้องใช้วิธี **สร้างตารางใหม่ + คัดลอกข้อมูล** |

---

# JAVA กับ MySQL

## **📌 วิธีดาวน์โหลดและติดตั้ง MySQL JDBC**
### **1️⃣ ดาวน์โหลด MySQL JDBC Driver (`mysql-connector-java.jar`)**
🔗 [ดาวน์โหลดที่นี่](https://dev.mysql.com/downloads/connector/j/)  
เลือก **Platform Independent** → ดาวน์โหลดไฟล์ **`.zip`** แล้วแตกไฟล์  

---

### **2️⃣ เพิ่ม `mysql-connector-java.jar` ใน NetBeans**
1. เปิด **NetBeans**
2. ไปที่ **Projects → คลิกขวาที่ Libraries → Add JAR/Folder**
3. เลือกไฟล์ **`mysql-connector-java-x.x.x.jar`** ที่ดาวน์โหลดมา
4. กด **Open** → ตรวจสอบว่าไฟล์ถูกเพิ่มใน `Libraries` แล้ว  

---

### **3️⃣ ตั้งค่า `JDBC URL` ให้เชื่อมต่อ MySQL**
```java
String url = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&characterEncoding=utf8mb4&serverTimezone=UTC";
String user = "root"; // ใส่ชื่อผู้ใช้ MySQL
String password = ""; // ใส่รหัสผ่าน MySQL

try (Connection conn = DriverManager.getConnection(url, user, password)) {
    System.out.println("✅ เชื่อมต่อ MySQL สำเร็จ! (รองรับ utf8mb4)");
} catch (SQLException e) {
    System.out.println("❌ เกิดข้อผิดพลาด: " + e.getMessage());
}
```

---

## **✅ สรุป**
✅ **ถ้าใช้ SQLite JDBC เดิม → ต้องดาวน์โหลด MySQL JDBC (`mysql-connector-java.jar`)**  
✅ **ถ้าเคยมีแล้ว → ไม่ต้องโหลดใหม่ แค่เพิ่มไปที่ `Libraries`**  
✅ **ตั้งค่า `JDBC URL` ให้รองรับ `utf8mb4`**  

---
