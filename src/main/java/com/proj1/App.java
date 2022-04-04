package com.proj1; import java.util.Scanner; import java.io.IOException;

public class App {
    public static void main( String[] args){
        Scanner scannakin = new Scanner(System.in);
        Init.init(false,scannakin);
        mainMenu(scannakin);
        SaveManager.exitSave();
    }

    //mainMenu
    public static void mainMenu(Scanner james) {
        mainMenuLoop: while (true) {
            printMainMenu();
            int chooseAction = 10;//Any non valid option will work
            try{
            chooseAction = Integer.parseInt(james.nextLine());
            } catch(NumberFormatException e){
                System.out.println("Please choose a valid option");
                pauseMenu(james);
            }
            try {
                switch (chooseAction) {
                    case (1):
                        clearScreen();
                        Exam.printAllExams(james);
                        pauseMenu(james);
                        break;
                    case (2):
                        clearScreen();
                        getStudents(james);
                        break;
                    case (3):
                        clearScreen();
                        Student.newStudent(james);
                        break;
                    case (4):
                        clearScreen();
                        Student.deleteStudent(james);
                        break;
                    case (5):
                        clearScreen();
                        startExams(james);
                        break;
                    case (6):
                        clearScreen();
                        studentExamStatus(james);
                        break;
                    case (7):
                        clearScreen();
                        studentExamPassed(james);
                        break;
                    case (8):
                        clearScreen();
                        Student.studentMostPassed();
                        break;
                    case(0):
                        System.out.println("Saving Data.....");
                        break mainMenuLoop;
                    default:
                        System.out.println("Invalid option. Please choose a listed option");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error in the mainMenu method!");
                System.out.println(e);
            }
        }
    }

    // printMainMenu
    private static void printMainMenu() {
        clearScreen();
        System.out.println("1) Lijst met examens");
        System.out.println("2) Lijst met studenten");
        System.out.println("3) Nieuwe student inschrijven");
        System.out.println("4) Student verwijderen");
        System.out.println("5) Examen afnemen");
        System.out.println("6) Is student geslaagd voor test?"); //non readable reference
        System.out.println("7) Welke examens heeft student gehaald?");
        System.out.println("8) Welke student heeft de meeste examens gehaald?");
        System.out.println("0) Exit");
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    // getStudents
    private static void getStudents(Scanner scanner) {
        for (Student e : Student.studentList) {
            System.out.println(e.getName());
        }
        pauseMenu(scanner);
    }

     //studentExamStatus
     public static void studentExamStatus(Scanner scanner){
        int studentNumber = askStudentNumber(scanner);
        boolean studentFound = false;
        // For loop veranderd de variabele studentnumber naar index van studentList.
        for (int i = 0; i < Student.studentList.size(); i++) {
            if (Student.studentList.get(i).getStudentNumber() == studentNumber) {
                studentNumber = i;
                studentFound = true;
                break;
            }
        }
        if (studentFound) {
            // Print alle examens even
            System.out.println("Examens beschikbaar:");
            int counter = 1;
            for (Exam exam : Exam.examList) {
                System.out.println(counter + ")" + exam.getName() + " - " + exam.getCategory());
                counter++;
            }
            System.out.println("Voer het nunmmer van het examen in:");
            int examNummer = scanner.nextInt() - 1;
            scanner.nextLine();
            boolean gehaald = false;
            for (int i = 0; i < Student.studentList.get(studentNumber).behaaldeExamens.size(); i++) {
                if (Student.studentList.get(studentNumber).behaaldeExamens.get(i)
                        .equals(Exam.examList.get(examNummer))) {
                    gehaald = true;
                }
            }
            if (gehaald) {
                System.out.println("De student heeft het examen gehaald.");
            } else {
                if (examNummer<0||examNummer>=Student.studentList.get(studentNumber).behaaldeExamens.size()){
                    System.out.println("Wat denk je zelf, mafklapper? Dat examen bestaat helemaal niet.");
                } else{
                System.out.println("De student heeft het examen niet gehaald.");
                }
            }
        } else {
            System.out.println("Student is niet gevonden. U zal nu terugkeren naar het hoofdmenu.");
        }
        pauseMenu(scanner);
    }

    //studentExamPassed
    public static void studentExamPassed(Scanner scanner){
        int studentNumber = askStudentNumber(scanner);
        boolean studentFound = false;
        for (int i = 0; i < Student.studentList.size(); i++) {
            if (Student.studentList.get(i).getStudentNumber() == studentNumber) {
                studentNumber = i;
                studentFound = true;
                break;
            }
        }
        if (studentFound) {
            int counter = 1;
            if (Student.studentList.get(studentNumber).behaaldeExamens.size() > 0) {
                for (Exam exam : Student.studentList.get(studentNumber).behaaldeExamens) {
                    System.out.println(counter + ") " + exam.getName() + " - " + exam.getCategory());
                    counter++;
                }
            } else {
                System.out.println("De student heeft helaas geen examens gehaald.");
            }
        } else {
            System.out.println("Student is niet gevonden. Keer terug naar het hoofdmenu");
        }
        pauseMenu(scanner);
    }

    //StartExams
    public static void startExams(Scanner scanner){
        int studentNumber = askStudentNumber(scanner);
        for (int i = 0; i < Student.studentList.size(); i++) {
            if(studentNumber == Student.studentList.get(i).getStudentNumber()){
                studentGegevensAanwezig(Student.studentList.get(i), scanner);
            }            
        }
        pauseMenu(scanner);
    }

    private static void studentGegevensAanwezig(Student student,Scanner scanner){
        try{
            
            System.out.println("Kies uw examen:");
            Exam.printAllExams(scanner);
            int keuze = scanner.nextInt();
            scanner.nextLine();

            if (keuze > Exam.examList.size() || keuze < 0){
                System.out.println("Wat denk je zelf, mafklapper? Je kan niet een ander getal geven dan dat jou gepresenteerd is.");
            } else {
                Exam.examList.get(keuze-1).startExam(student,scanner);
            }
        }
        catch(Exception e){
            System.out.println("Error in studentGegevensAanwezig");
            System.out.println(e);
        }
    }

    private static void studentGegevensAfwezigMessage() {
        System.out.println("Studentgegevens kloppen niet, of bestaan niet.");
        System.out.println("Kies een van de volgende opties:");
        System.out.println("1) Probeer opnieuw");
        System.out.println("2) Nieuwe student aanmaken");
        System.out.println("3) Terug naar hoofdmenu");
    }

    public static void pauseMenu(Scanner scanner) {
        System.out.println("Press return to continue.");
        try {
            scanner.nextLine(); // This is just here to wait for input
        } catch (Exception e) {
            System.out.println("Exception in method pauseMenu");
            System.out.println(e);
        }
    }

    private static int askStudentNumber(Scanner scanner) {
        while (true) {
            try
            {
                System.out.println("Voer je studentnummer in:");
                int input = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < Student.studentList.size(); i++) {
                    if(input == Student.studentList.get(i).getStudentNumber()){
                        return input;
                    }
                }
                studentGegevensAfwezig(scanner);
            }
            catch (NumberFormatException e){
                System.out.println("Voer een studentnummer in, zonder tekst.");
                pauseMenu(scanner);
                clearScreen();
            }
        }
    }

    public static void studentGegevensAfwezig(Scanner scanner){
        studentGegevensAfwezigMessage();
        int studentNotFoundKeuze = scanner.nextInt();
        scanner.nextLine();
            switch(studentNotFoundKeuze){
                case 1:
                    break;//NO. No recursion
                case 2:
                    Student.newStudent(scanner);
                    break;//Break the for loop & run this method again
                case 3: 
                    break;//Break the while loop & go back to mainMenu
                default:
                    System.out.println("Verkeerde optie. Ga terug naar main menu.");
                    break;
            }     
    }
}
