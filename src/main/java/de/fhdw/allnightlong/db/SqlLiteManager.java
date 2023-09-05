package de.fhdw.allnightlong.db;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlLiteManager implements DatabaseManager {

    private Connection conn;

    @Override
    public void connect() {
        try {
            
            conn = DriverManager.getConnection("jdbc:sqlite:your-database-file.db");
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMessage(String username, String message) {
    try {
       // System.out.println("Saving message with username: " + username + ", message: " + message);  // Debugging-Ausgabe
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO messages (username, message) VALUES (?, ?)");
        pstmt.setString(1, username);
        pstmt.setString(2, message);
        pstmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

     public void createTable() {
        try {
            PreparedStatement pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, username TEXT, message TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getRecentMessages(String username, int limit) {
        List<String> messages = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT message, timestamp FROM messages WHERE username = ? OR username LIKE 'bot_%_' || ? ORDER BY timestamp ASC LIMIT ?"
            );
            pstmt.setString(1, username);
            pstmt.setString(2, username);
            pstmt.setInt(3, limit);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String message = rs.getString("message");
                Timestamp timestamp = rs.getTimestamp("timestamp");  
                messages.add(((Timestamp) timestamp).toString() + " - " + message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    


   
}
