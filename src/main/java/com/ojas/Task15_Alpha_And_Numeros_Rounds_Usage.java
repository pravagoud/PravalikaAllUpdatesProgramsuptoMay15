package com.ojas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;

public class Task15_Alpha_And_Numeros_Rounds_Usage {
    private static int maxLength;
    private static LocalDateTime currentDateTime;
    private static File outputFolder;
    private static FileReader alphabetFileReader;
    private static FileReader numericFileReader;
    private static FileWriter outputFileWriter;

    public static void main(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Select Alphabet File");
        int alphabetFileResult = fileChooser.showOpenDialog(null);
        if (alphabetFileResult != JFileChooser.APPROVE_OPTION) {
            System.out.println("No alphabet file selected. Exiting...");
            return;
        }
        File alphabetFile = fileChooser.getSelectedFile();

        fileChooser.setDialogTitle("Select Numeric File");
        int numericFileResult = fileChooser.showOpenDialog(null);
        if (numericFileResult != JFileChooser.APPROVE_OPTION) {
            System.out.println("No numeric file selected. Exiting...");
            return;
        }
        File numericFile = fileChooser.getSelectedFile();

//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter number of rounds: ");
//        maxLength = sc.nextInt();

        outputFolder = new File("output");
        outputFolder.mkdir();

        currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDateTime = currentDateTime.format(formatter);

        alphabetFileReader = new FileReader(alphabetFile);
        numericFileReader = new FileReader(numericFile);

        File outputFile = new File(outputFolder, "Output_" + "_" + formattedDateTime + ".txt");
        outputFileWriter = new FileWriter(outputFile);

        BufferedReader alphabetReader = new BufferedReader(alphabetFileReader);
        //BufferedReader numericReader = new BufferedReader(numericFileReader);

        String alphabetLine = null;
        String numericLine = null;
        
        while ((alphabetLine = alphabetReader.readLine()) != null) {
            BufferedReader numericReader1 = new BufferedReader(new FileReader(numericFile));
            while ((numericLine = numericReader1.readLine()) != null) {
                String alpha = alphabetLine.trim();
                String num = numericLine.trim();

              // String outputLine = "alpha=" + alpha + ", num=" + num;
                System.out.println("alpha=" + alpha + ", num=" + num);
                //outputFileWriter.write(outputLine + "\n");
            }
            numericReader1.close();
        }

        alphabetReader.close();
        
        outputFileWriter.close();

        alphabetFileReader.close();
        numericFileReader.close();
    }
}
