package org.example.checkSum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSumController {

    public boolean generateCheckSum(String pathToFileToCheck) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(pathToFileToCheck)));
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            String checkSum = sb.toString();

            pathToFileToCheck = pathToFileToCheck.replace(".zip", "");

            Files.write(Paths.get(pathToFileToCheck + ".md5"), checkSum.getBytes());

            return true;

        } catch (IOException e) {
            System.out.println("File to create checksum not found");
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm found");
            return false;
        }
    }

    public boolean checkSum(String pathToFileToCheck, String checkSumFile) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(pathToFileToCheck)));
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            String calculatedCheckSum = sb.toString();

            String storedCheckSum = new String(Files.readAllBytes(Paths.get(checkSumFile)));

            return calculatedCheckSum.equals(storedCheckSum.trim());

        } catch (IOException e) {
            System.out.println("File to create checksum not found");
            return false;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm found");
            return false;
        }

    }

}