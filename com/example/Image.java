package com.example;

import java.util.*;
import java.io.*;
import java.lang.*;


/**
 * Created by mlsfa on 6/29/2017.
 */

public class Image {
    public Pixel[][] image;
    public Image(Pixel[][] IMAGE, int WIDTH, int LENGTH){
        image = IMAGE;
        width = WIDTH;
        length = LENGTH;
    };
    public int width = -1;
    public int length = -1;
    public int getWidth(){return width;}
    public int getLength(){return length;}
    public Pixel[][] getArray(){return image;}
    public void alterImage(String action, int n)
    {
        if (action == "invert")
        {
            invert();
        }
        else if (action == "grayscale")
        {
            grayscale();
        }
        else if (action == "emboss")
        {
            emboss();
        }
        else if (action == "motionblur")
        {
            motionblur(n);
        }
    }
    public void invert()
    {
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                image[j][i].setRED(255 - image[j][i].getRED());
                image[j][i].setGREEN(255 - image[j][i].getGREEN());
                image[j][i].setBLUE(255 - image[j][i].getBLUE());
            }
        }
    }
    public void grayscale()
    {
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                Pixel p = image[j][i];
                int average = ((image[j][i].getRED()
                        + image[j][i].getGREEN()
                        + image[j][i].getBLUE()) / 3);
                image[j][i].setRED(average);
                image[j][i].setGREEN(average);
                image[j][i].setBLUE(average);
            }
        }
    }
    public void emboss()
    {
        for (int i = 0; i < length; i++)
        {
            if (i != 0)
            {
                for (int j = 0; j < width; j++)
                {
                    if (j != 0)
                    {
                        Pixel p = image[j][i];
                        Pixel upperLeft = image[j-1][i-1];
                        int largest_diff = 128 + findLargestDiff(p, upperLeft);

                        if (largest_diff > 255) {largest_diff = 255;}
                        if (largest_diff < 0) {largest_diff = 0;}

                        p.setRED(largest_diff);
                        p.setGREEN(largest_diff);
                        p.setBLUE(largest_diff);

                        image[j][i] = p;
                    }
                    else
                    {
                        image[j][i].setRED(128);
                        image[j][i].setGREEN(128);
                        image[j][i].setBLUE(128);
                    }
                }
            }
            else
            {
                for (int j = 0; j < width; j++)
                {
                    image[j][i].setRED(128);
                    image[j][i].setGREEN(128);
                    image[j][i].setBLUE(128);
                }
            }
        }
    }
    public void motionblur(int n)
    {
        int w = width;
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int rTotal = 0;
                int gTotal = 0;
                int bTotal = 0;
                int counter = 0;

                for (int k = j; k < (n+j); k++)
                {
                    if (k < width)
                    {
                        rTotal = rTotal + image[k][i].getRED();
                        gTotal = gTotal + image[k][i].getGREEN();
                        bTotal = bTotal + image[k][i].getBLUE();
                        counter++;
                    }
                }
                int rAverage = rTotal / counter;
                int gAverage = gTotal / counter;
                int bAverage = bTotal / counter;

                image[j][i].setRED(rAverage);
                image[j][i].setGREEN(gAverage);
                image[j][i].setBLUE(bAverage);
            }
        }
    }
    public int findLargestDiff(Pixel p, Pixel upperLeft)
    {
        int biggest_diff = -1;

        int r = p.getRED() - upperLeft.getRED();
        int g = p.getGREEN() - upperLeft.getGREEN();
        int b = p.getBLUE() - upperLeft.getBLUE();

        r = Math.abs(r);
        g = Math.abs(g);
        b = Math.abs(b);

        if (r > g && r > b){biggest_diff = r;}
        if (g > r && g > b){biggest_diff = g;}
        if (b > r && b > g){biggest_diff = b;}
        if (r == b && r > g) {biggest_diff = r;}
        if (r == g && r > b) {biggest_diff = r;}
        if (g == b && g > r) {biggest_diff = g;}
        if (r == g && r == b) {biggest_diff = r;}

        return biggest_diff;
    }
}
