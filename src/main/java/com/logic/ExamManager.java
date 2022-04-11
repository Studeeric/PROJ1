package com.logic; import java.util.ArrayList;
import com.ui.UI;
import com.ui.exManagerUI;


/*
=======Class Explanation=======
This class manages creating, deleting & editing exams and the questions in them.
Thanks for coming to my TED talk.
*/
//TODO Deze class is fucked door de nieuwe Question format.

abstract public class ExamManager {

    public static void exManagerMenu(IScanner scanner) {
        exManagerLoop: while (true) {
            exManagerUI.printExManagerMenu();
            String userChoiceExManager = scanner.nextLine();
            switch(userChoiceExManager){
                case("1"):
                    exNewExam(scanner);
                    break;
                case("2"):
                    exRemoveExam(scanner, exChooseExamIndex(scanner,false));
                    break;
                case("3"):
                    exEditExam(scanner);
                    break;
                case("0"):
                    UI.clearScreen();
                    exManagerUI.printExReturnMainMenu(false);
                    Debug.wait(1,true);
                    break exManagerLoop;
                default:
                    exManagerUI.printExReturnMainMenu(true);
                    Debug.wait(2,true);
                    break;
            }
        }
    }

    

    //Action methods
    public static void exNewExam(IScanner scanner) {
        exManagerUI.printExExamInput(true);
        String newExName = scanner.nextLine();
        exManagerUI.printExExamInput(false);
        String newExCat = scanner.nextLine();
        Exam exManagerCreated = new Exam(newExName, newExCat);
        exManagerUI.printExAddOptions(true);
        if(scanner.nextLine().equals("1")){
            exAddQuestion(exManagerCreated,scanner);
        }
        exManagerUI.printExAddOptions(false);
        Debug.wait(2,true);
    }

    
    public static void exRemoveExam(IScanner scanner,int exToBeRemoved) {
        UI.clearScreen();
        exManagerUI.printExRemoveMenu(Exam.examList.get(exToBeRemoved).getName(),Exam.examList.get(exToBeRemoved).getCategory());
        exRemoveLoop: while (true){
            switch(scanner.nextLine()){
                case("y"):
                case("Y"):
                case("yes"):
                case("Yes"):
                case("j"):
                case("J"):
                case("ja"):
                case("Ja"):
                    Exam.examList.remove(exToBeRemoved);
                    exManagerUI.printExRemoveReact(true);
                    break exRemoveLoop;
                case("n"):
                case("N"):
                case("no"):
                case("No"):
                case("nee"):
                case("Nee"):
                    exManagerUI.printExRemoveReact(true);
                    break exRemoveLoop;
                default:
                exManagerUI.printExRemoveDefaultError();
                    break;

            }
        }
        
    }

    public static void exEditExam(IScanner scanner) {
        exEditMainLoop: while(true){
            exManagerUI.printExEditQuestionEditQuestion(true);
            int exIndex = exChooseExamIndex(scanner,true);
            if(exIndex != -1){
                Exam exActualExam = Exam.examList.get(exIndex);
                exEditSubLoop: while (true){
                    exManagerUI.printExEditMainMenu();

                    switch(scanner.nextLine()){
                        case("1"):
                            exAddQuestion(exActualExam, scanner);
                            break;
                        case("2"):
                            exRemoveQuestion(exActualExam, scanner);
                            break;
                        case("3"):
                            exManagerUI.printExEditQuestionEditQuestion(true);
                            for(int i = 0; i< exActualExam.questionList.size();i++){
                                exManagerUI.printExEditQuestionList(i, exActualExam.questionList.get(i).questionPrompt);
                            }
                            exManagerUI.printExEditQuestionList();

                            int exUserEditChoice = Integer.parseInt(scanner.nextLine())-1;
                            if(exUserEditChoice != -1){
                                Question exChosenQuest = exActualExam.questionList.get(exUserEditChoice);
                                UI.clearScreen();
                                //TODO Update this to new Question format
                                exPrintQuestArray(exChosenQuest.questionContents, true);
                                exManagerUI.printExEditQuestionEditQuestion(true);
                                exUserEditChoice = Integer.parseInt(scanner.nextLine())-1;
                                UI.clearScreen();
                                //TODO this too
                                exManagerUI.printExEditQuestionEditMenu(exChosenQuest.questionContents.get(exUserEditChoice));
                                exChosenQuest.questionContents.set(exUserEditChoice, scanner.nextLine());
                                exManagerUI.printExEditQuestionEditConfirm();
                                App.pauseMenu(scanner);
                            }
                            break;
                        case("0"):
                            break exEditSubLoop;
                            
                        default:
                            break;
                    }
                }
            }
            else{break exEditMainLoop;}

        }
        
    }

    

    //Support methods
    public static void exAddQuestion(Exam exam,IScanner scanner) {
        exAddQuestLoop1: while(true){
            UI.clearScreen();
            exManagerUI.printExAddQMenu(exam.getQuestionList().size());
            switch (scanner.nextLine()) {
                case ("1"):
                    exam.addQuestion(exFormatQuestion(exGetQuestCont(scanner)));
                    break;
                case("2"):
                    int counter = 1;
                    //TODO Update this
                    for(Question question : exam.questionList){
                        exManagerUI.printExAddQLoop(true, null,counter);
                        for(String content : question.questionContents){
                            exManagerUI.printExAddQLoop(false, content,0);
                        }
                        counter++;
                        System.out.println();
                    }
                    App.pauseMenu(scanner);
                    break;
                case ("0"):
                    break exAddQuestLoop1;
                default:
                    break;
            }
        }
    }

    public static void exRemoveQuestion(Exam exActualExam, IScanner scanner) {
        System.out.println("Welke vraag wil u verwijderen?");
        for(int i = 0; i< exActualExam.questionList.size();i++){
            System.out.println((i+1)+") "+ exActualExam.questionList.get(i).questionPrompt);
        }
        System.out.println();
        int exToBeRemoved = Integer.parseInt(scanner.nextLine());
        UI.clearScreen();
        System.out.println("Weet u zeker dat u deze vraag wil verwijderen\n" + exActualExam.questionList.get(exToBeRemoved).questionPrompt);
        System.out.println("Y\\N?");
        exRemoveQuestLoop: while (true){
            switch(scanner.nextLine()){
                case("y"):
                case("Y"):
                case("yes"):
                case("Yes"):
                case("j"):
                case("J"):
                case("ja"):
                case("Ja"):
                    exActualExam.questionList.remove(exToBeRemoved);
                    System.out.println("Vraag verwijderd.\nReturning to menu...");
                    break exRemoveQuestLoop;
                case("n"):
                case("N"):
                case("no"):
                case("No"):
                case("nee"):
                case("Nee"):
                    System.out.println("Vraag verwijderen geannuleerd.\nReturning to menu...");
                    break exRemoveQuestLoop;
                default:
                System.out.println("Kies tussen: Yes(Y) of No(N)");
                    break;

            }
        }
        
    }

    public static int exChooseExamIndex(IScanner scanner, boolean exit) {
        Exam.printAllExams(scanner);
        if(exit){System.out.println("0) Keer terug naar het hoofdmenu");}
        Integer examNr;
        while (true){
            String exChooseExChoice = scanner.nextLine();
            try{
                examNr = Integer.parseInt(exChooseExChoice);
                break;
            }
            catch(Exception e){System.out.println("Kies een optie uit de lijst");}
        }
        return examNr-1;
    }

    public static ArrayList<String> exGetQuestCont(IScanner scanner) {
        ArrayList<String> exQuestContents = new ArrayList<>();
        System.out.println("Voer de vraag in:");
        exQuestContents.add(scanner.nextLine());
        exGetQuestLoop: while (true){
            System.out.println("Enter a option & press return to confirm. Press 0 to stop adding options");
            String exUserChoice = scanner.nextLine();
            switch(exUserChoice){
                case("0"):
                    break exGetQuestLoop;
                default:
                    exQuestContents.add(" "+exUserChoice);
                    break;
            }
        }
        UI.clearScreen();
        System.out.println("Welke vraag is het juiste antwoord?");
        exPrintQuestArray(exQuestContents,false);
        exQuestContents.add(scanner.nextLine());
        return exQuestContents;
    }

    public static Question exFormatQuestion(ArrayList<String> contents) {
        ArrayList<String> questionOptions = new ArrayList<>();
        for(int i =1;i<contents.size()-1;i++){
            questionOptions.add(contents.get(i));
        }
        return new Question(contents.get(0), questionOptions, contents.get(contents.size()-1));
        
    }

    public static void exPrintQuestArray(ArrayList<String> contents,boolean numbered) {
        System.out.println("Vraag 1:");
        System.out.println();
        for(int i=0;i<contents.size();i++){
            if(numbered){
                System.out.println((i+1)+" "+contents.get(i));
            }
            else{
            System.out.println(contents.get(i));
            }
        }
        
    }
}
