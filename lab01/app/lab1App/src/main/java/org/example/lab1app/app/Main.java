package org.example.lab1app.app;

/**
 * @Author: Daniel Ryszkowski aka BWP
 * @Project: lab1App
 * @Date: 2020-10-07
 * @Version: 1.0
 *
 * @Description:
 *
 * On new machine, you need to run: mvn clean install, in project:
 * controlSumCheck
 * zipFileMaker
 * to create jar files and dependencies in local maven repository for this project
 *
 * For clean project structure in console run: mvn clean
 * For build project in console run: mvn compile
 * For run project in console run: mvn javafx:run
 * For link project in console run: mvn javafx:jlink
 *
 * After creating zip file with jlink, unzip it
 * go to bin folder and in console run:
 * java -m org.example.lab1app/org.example.lab1app.app.Main
 *
 */
public class Main {
    public static void main(String[] args) {
        Lab01Application.main(args);
    }
}
