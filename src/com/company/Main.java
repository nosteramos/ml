package com.company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws IOException, SQLException {

        Scanner in = new Scanner(System.in);
        StringBuilder prefix = new StringBuilder();
        IDal dal = new SQLightClient("jdbc:sqlite:words.db");

        //dal.loadFromFile("./words.txt") <-- You can load a text file to the dictionary with one word in each line

        String[] matchingWords = new String[0];
        System.out.println("Press ENTER to get a list of suggested words.");
        System.out.println("Enter \\q +  ENTER to add the word to the dictionary if needed and quit.");
        System.out.print(">");
        prefix.append(in.nextLine());
        if (prefix.toString().endsWith("\\q")) {
            System.exit(0);
        }
        while (!prefix.toString().endsWith("\\q")) {

            matchingWords = dal.getMatchingWords(prefix.toString(), (long) 10);
            for (String sug: matchingWords ) {
                    System.out.println(sug);
            }
            System.out.println();
            System.out.print(">"  + prefix);
            prefix.append(in.nextLine());


        }
        //Finally Add the word if it doesn't exist
        String newWord = prefix.toString().replace("\\q", "");
        if (matchingWords.length < 1 || !(matchingWords[0].equals(newWord))) {

            dal.insert(newWord);
            System.out.println("Word: " + newWord + " was added to the dictionary.");
        }


    }


}
