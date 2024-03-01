package org.example.zipMaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileMaker {

    private String destinationZipFile;

    public void setDestinationZipFile(String destinationZipFile) {
        this.destinationZipFile = destinationZipFile;
    }

    private boolean isDestinationZipFileSet() {
        return destinationZipFile != null && !destinationZipFile.isEmpty() && !destinationZipFile.isBlank();
    }

    public boolean universalZipper(List<String> filesAndDirectoryToZip) {
        try {
            if (isDestinationZipFileSet()) {
                FileOutputStream fos = new FileOutputStream(destinationZipFile);
                ZipOutputStream zos = new ZipOutputStream(fos);

                for(String fileOrDirectory : filesAndDirectoryToZip) {
                    addToZipFile(fileOrDirectory, zos, new File(fileOrDirectory).getParent());
                }

                zos.close();
                fos.close();

                return true;
            } else {
                System.out.println("Error: Destination zip file is not set");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private void addToZipFile(String fileName, ZipOutputStream zos, String rootPath) {
        try {
            File file = new File(fileName);
            String zipFilePath = fileName.substring(rootPath.length() + 1, fileName.length());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File fileInDir : Objects.requireNonNull(files)) {
                    addToZipFile(fileInDir.getPath(), zos, rootPath);
                }
            } else {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(zipFilePath);
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



}