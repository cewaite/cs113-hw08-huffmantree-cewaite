package edu.miracosta.cs113;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HuffmanTreeDriver {
    public static void main(String[] args) {
        try {
            TextFileGenerator fileGenerator = new TextFileGenerator();
            fileGenerator.makeCleanFile("https://kissthegroundmovie.com/", "src/original.txt");


            String characters = "";
            Scanner myReader = new Scanner(new File("src/original.txt"));
            while (myReader.hasNextLine()) {
                characters += myReader.nextLine();
            }

            HuffmanTree huffmanTree = new HuffmanTree(characters);

            myReader.close();

            myReader = new Scanner(new File("src/original.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                BufferedWriter out = new BufferedWriter(new FileWriter("src/encoded.txt"));
                out.write(huffmanTree.encode(data));
                out.close();
                System.out.println("File encoded successfully");
            }

            myReader.close();

            myReader = new Scanner(new File("src/encoded.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                BufferedWriter out = new BufferedWriter(new FileWriter("src/decoded.txt"));
                out.write(huffmanTree.decode(data));
                out.close();
                System.out.println("File decoded successfully");
            }

            myReader.close();

            int originalByteCount = 0;
            myReader = new Scanner(new File("src/original.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++)
                {
                    originalByteCount += 18;
                }


                System.out.println("Original file bite count successful");
            }

            myReader.close();

            int decodedBitCount = 0;
            myReader = new Scanner(new File("src/decoded.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++)
                {
                    decodedBitCount += 18;
                }


                System.out.println("Decoded file bite count successful");
            }

            myReader.close();

            int encodedBitCount = 0;
            myReader = new Scanner(new File("src/encoded.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++)
                {
                    encodedBitCount++;
                }


                System.out.println("Encoded file bite count successful");
            }
            System.out.println("Encoded file bite count successful");

            myReader.close();

            System.out.println("\nThe Original File bit count: " + originalByteCount);
            System.out.println("The Decoded File bit count: " + decodedBitCount);
            System.out.println("The Encoded File bit count: " + encodedBitCount);
            System.out.println("The Percentage of Compression: " + (originalByteCount / encodedBitCount));

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
