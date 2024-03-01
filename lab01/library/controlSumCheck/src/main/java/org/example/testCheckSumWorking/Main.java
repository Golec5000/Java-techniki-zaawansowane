package org.example.testCheckSumWorking;

import org.example.checkSum.CheckSumController;

public class Main {

    public static void main(String[] args) {
        CheckSumController checkSumController = new CheckSumController();
        checkSumController.generateCheckSum("C:\\Users\\danie\\Desktop\\file2.txt");
        System.out.println(checkSumController.checkSum("C:\\Users\\danie\\Desktop\\file2.txt"
                , "C:\\Users\\danie\\Desktop\\file2.md5"));
    }



}
