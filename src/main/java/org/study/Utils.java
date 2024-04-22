package org.study;

// Utility class for colors and printing centered text
public class Utils {
    // ANSI COLORS AND RESET
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    // SPECIAL PRINTLN TO MAKE CENTERED TEXT
    public static void printCentered(String text, int width) {
        // Calculate padding to center the text within the specified width
        int padding = width - text.length() - 2;
        // Print the centered text within the specified width
        System.out.println("│  " + text + " ".repeat(padding) + "   │"); //CONSIDERING THE WIDTH OF THE LINE
    }
}
