// NumberRange.java
// Developed by Josh Humpherys as Assignment0 for COS230
// Reads a list of number ranges and prints the combined ranges

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NumberRange {
    public static void main(String[] args) {
        NumberRange list = new NumberRange();

        if(args.length != 1) {
            System.err.println("Error: Invalid arguments.");
            System.err.println("Please pass the name of the file to be read.");
            return;
        }

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(args[0]));
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(IOException e) {
            System.err.println("Error: Unable to find or read file.");
            System.err.println("Please make sure the file exists and can be read.");
            return;
        }
    }
}
