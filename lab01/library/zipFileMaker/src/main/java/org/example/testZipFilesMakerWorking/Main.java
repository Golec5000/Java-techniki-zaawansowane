package org.example.testZipFilesMakerWorking;

import org.example.zipMaker.ZipFileMaker;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ZipFileMaker zipFileMaker = new ZipFileMaker();

        // Test making files to zip


        // Test making a files and directory to zip
        zipFileMaker.setDestinationZipFile("C:\\Users\\danie\\Desktop\\Project_buff\\zipFileMaker\\testFiles4.zip");

        zipFileMaker.universalZipper(List.of(
                "C:\\Users\\danie\\Desktop\\file3.txt",
                "C:\\Users\\danie\\Desktop\\file2.txt",
                "C:\\Users\\danie\\Desktop\\file1.txt",
                "C:\\Users\\danie\\Desktop\\test_dir"
        ));

    }
}
