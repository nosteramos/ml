package com.company;

import java.io.IOException;
import java.sql.SQLException;

public interface IDal {

    String[] getMatchingWords(String prefix, Long rowLimit) throws SQLException;
    void insert(String word) throws SQLException;
    void loadFromFile(String filePath) throws IOException, SQLException;


}
