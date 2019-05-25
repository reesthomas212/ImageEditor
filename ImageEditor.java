package com.example;

import java.io.*;
import java.util.*;

public class ImageEditor {

    public static Image createImage(Scanner file)
    {
        //start reading in ppm file
        //ignore comments and whitespace

        System.out.println("First token in file: " + file.next());
        int width = 0;
        int length = 0;
        CheckForComments(file);
        width = Integer.parseInt(file.next());
        CheckForComments(file);
        length = Integer.parseInt(file.next());
        file.next();
        System.out.println("Width is: " + width + ", and length is: " + length);

        Pixel[][] array = new Pixel[width][length];
        for (int i = 0; i < length; i++)
        {
            for(int j = 0; j < width; j++)
            {

                CheckForComments(file);
                //System.out.println("j = " + j + "and i = " + i);
                array[j][i] = new Pixel();
                array[j][i].setRED(Integer.parseInt(file.next()));
                array[j][i].setGREEN(Integer.parseInt(file.next()));
                array[j][i].setBLUE(Integer.parseInt(file.next()));
            }
        }
        Image image = new Image(array, width, length);
        return image;
    }
    public static void fileWriter(Image image, String outFile) throws IOException
    {
        System.out.println("Do we get here??");
        File temp = new File(outFile);
        try {

            PrintWriter fileWriter = new PrintWriter(temp);
            fileWriter.write("P3");
            fileWriter.write('\n');
            fileWriter.write(Integer.toString(image.getWidth()));
            fileWriter.write('\n');
            fileWriter.write(Integer.toString(image.getLength()));
            fileWriter.write('\n');
            fileWriter.write("255");
            fileWriter.write('\n');


            //fileWriter.close();


            for (int i = 0; i < image.getLength(); i++)
            {
                for (int j = 0; j < image.getWidth(); j++)
                {
                    Pixel p = image.getArray()[j][i];
                    fileWriter.write(' ');
                    fileWriter.write(Integer.toString(p.getRED()));
                    fileWriter.write(' ');
                    fileWriter.write(Integer.toString(p.getGREEN()));
                    fileWriter.write(' ');
                    fileWriter.write(Integer.toString(p.getBLUE()));
                }
            }
            //fileWriter.flush();
            fileWriter.close();
        }
        catch(FileNotFoundException ex){System.err.println(ex);}
        //System.out.println("Do we get here?");

    }
    public static void CheckForComments(Scanner file)
    {
        while (!file.hasNextInt())
        {
            file.nextLine();
        }
    }

    public static void main(String[] args) throws IOException{
        //grab command line values
        if (args.length != 3 && args.length != 4)
        {
            System.out.println(args.length);
            return;
        }

        String inFile = args[0]; //System.out.println("inFile is: " + inFile);
        String outFile = args[1];
        String command = args[2];
        int n = -1;
        if (args.length == 4)
        {
            n = Integer.parseInt(args[3]);
        }
        //Create Scanner object
        File temp = new File(inFile);//System.out.println("Do we get here?");
        Scanner file = new Scanner(temp);
        Image image = createImage(file);
        image.alterImage(command, n);
        fileWriter(image, outFile);
        file.close();
    }
}
