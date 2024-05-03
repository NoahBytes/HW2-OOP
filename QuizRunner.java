import java.io.*;

public class QuizRunner {
    static Quiz quiz;
    public static void main(String[] args) { //in the future, could implement naming quiz filepath through args
        try {
            quiz = new Quiz("Quiz.txt");
        } catch (FileNotFoundException fnf) {
            System.out.println("Quiz file was not found. Please be sure files are in the same directory.");
            System.out.println("Exiting quiz program...");
            return;
        } //try-catch block to catch exception where the file is not found.

        if (quiz.ReadInQuestions() == false) {
            System.out.println("Error while reading questions. Exiting quiz program...");
            return;
        }

        quiz.runQuiz();

        return;
    }
}
