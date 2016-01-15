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
                list.add(getRange(line));
            }
        }
        catch(IOException e) {
            System.err.println("Error: Unable to find or read file.");
            System.err.println("Please make sure the file exists and can be read.");
            return;
        }
    }

    public void add(Range range) {
         System.out.println(range);
    }

    private static Range getRange(final String input) {
        if(input.indexOf("//") == 0) return null;
        String line = input.trim();
        if(line.length() == 0) return null;
        try {
            int index = line.indexOf(" ");
            if(index == -1) return new Range(Long.parseLong(line));
            long start = Long.parseLong(line.substring(0, index));
            long end = Long.parseLong(line.substring(index + 1).trim());
            return new Range(start, end);
        }
        catch(NumberFormatException e) {
            System.err.println("Error: Unable to parse line of file.");
            System.err.println("Contents of line: " + input);
            return null;
        }
    }

    private static class Range {
        protected long start, end;
        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }
        Range(long startAndEnd) {
            start = end = startAndEnd;
        }

        @Override
        public String toString() {
            return start == end ? String.valueOf(start) : start + "-" + end;
        }
    }
}
