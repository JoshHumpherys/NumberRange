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
        
        System.out.println(list);
    }
    
    private RangeNode head = null;

    public void add(Range range) {
        if(range == null) return;
        if(head == null) {
            head = new RangeNode(range, null, null);
            return;
        }
        RangeNode last = findLast(range);
        if(last == null) {
            head = new RangeNode(range, null, head);
            head.getNext().setLast(head);
            return;
        }
        RangeNode newNode = new RangeNode(range, last, last.getNext());
        last.setNext(newNode);
        if(newNode.getNext() != null) {
            newNode.getNext().setLast(newNode);
        }
    }
    
    // Finds the RangeNode immediately before where the parameter Range should be placed
    private RangeNode findLast(Range range) {
        if(head == null) return null;
        RangeNode last = head;
        while(true) {
            if(range.beginsBefore(last)) {
                return last.getLast();
            }
            if(last.getNext() == null) {
                return last;
            }
            last = last.getNext();
        }
    }
    
    @Override
    public String toString() {
        if(head == null) return "[]";
        String s = "[" + head;
        RangeNode next = head;
        while((next = next.getNext()) != null) {
            s += ", " + next;
        }
        return s + "]";
    }

    // Parses a String to return a Range
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
    
    private static final class RangeNode extends Range {
        private RangeNode last = null;
        private RangeNode next = null;
        RangeNode(long start, long end, RangeNode last, RangeNode next) {
            super(start, end);
            this.last = last;
            this.next = next;
        }
        RangeNode(Range range, RangeNode last, RangeNode next) {
            super(range);
            this.last = last;
            this.next = next;
        }
        
        void setLast(RangeNode last) {
            this.last = last;
        }
        void setNext(RangeNode next) {
            this.next = next;
        }
        
        RangeNode getLast() {
            return last;
        }
        RangeNode getNext() {
            return next;
        }
    }

    protected static class Range {
        protected long start, end;
        Range(long start, long end) {
            if(start < end) {
                this.start = start;
                this.end = end;
            }
            else {
                this.start = end;
                this.end = start;
            }
        }
        Range(long startAndEnd) {
            start = end = startAndEnd;
        }
        Range(Range range) {
            if(range.getStart() < range.getEnd()) {
                start = range.getStart();
                end = range.getEnd();
            }
            else {
                start = range.getEnd();
                end = range.getStart();
            }
        }
        Range() {}

        // Returns whether this Range begins after the parameter Range
        boolean beginsBefore(Range range) {
            return start < range.getStart();
        }
        
        protected long getStart() {
            return start;
        }
        protected long getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return start == end ? String.valueOf(start) : start + "-" + end;
        }
    }
}