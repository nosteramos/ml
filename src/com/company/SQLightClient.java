package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class SQLightClient implements IDal {
    private String url;
    Connection conn = null;

    SQLightClient(String url) throws SQLException {
        this.url = url;
        initConnection();
        createDB();
        createTable();
    }

    private void initConnection() throws SQLException {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
    }

    private void createDB() throws SQLException {
        //Create DB if needed
        DatabaseMetaData meta = conn.getMetaData();
    }

    private void createTable() throws SQLException {

        //Create Table if needed
        String sql = "CREATE TABLE IF NOT EXISTS words(value text PRIMARY KEY);";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    @Override
    public void insert(String word) throws SQLException {

        //Insert Word
        String sql = "INSERT INTO words(value) VALUES(?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, word);
        pstmt.executeUpdate();
    }

    @Override
    public void loadFromFile(String filePath) throws IOException, SQLException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(
                filePath));
        String line = reader.readLine();
        while (line != null) {
            insert(line);
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();

    }

    @Override
    public String[] getMatchingWords(String prefix, Long rowLimit) throws SQLException {

        //Get Matching Words
        ArrayList<String> matchingWords = new ArrayList<String>();
        String sql = "select value from words where value like  '" + prefix + "%' and value >= '" + prefix + "' order by value limit " + rowLimit.toString() +";";
        PreparedStatement  ptmt  = conn.prepareStatement(sql);
        ResultSet rs    = ptmt.executeQuery();
        while (rs.next()) {
            matchingWords.add(rs.getString("value"));
        }
        String[] mw = new String[matchingWords.size()];
        matchingWords.toArray(mw);
        matchingWords.clear();
        return mw;

    }
}


