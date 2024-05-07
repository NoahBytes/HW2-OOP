import java.io.*;
import java.util.*;

public class Quiz {
    private BufferedReader fileIn;
    private String quizTitle;
    private List<MCQ> mcqList;
    private int numCorrect;

    public Quiz(String filepath) throws FileNotFoundException{
        fileIn = new BufferedReader(new FileReader("Quiz.txt"));
        mcqList = new LinkedList<MCQ>();
        numCorrect = 0;
    }

    public boolean ReadInQuestions() {
 
        try {
			quizTitle = fileIn.readLine();
            fileIn.readLine();
		} catch (IOException e) {
			System.out.println("File is empty! Ensure Quiz.txt has questions next time.");
            return false;
		}

        try {
            String tempQuestion[] = new String[5];
            String tempLine = null;

            do {
                for (int i = 0; i < 5; i++) {
                    tempLine = fileIn.readLine();
                    tempQuestion[i] = tempLine;
                }
                mcqList.add(new MCQ(tempQuestion));
                tempLine = fileIn.readLine();
            } while (tempLine != null);

        } catch (IOException e) {
            System.out.println("Error while reading in questions.");
            return false;
        }

        return true;
    }


    public void runQuiz() {
        Scanner userIn = new Scanner(System.in);
        int userAnswer = 0;

        System.out.println("Welcome to the " + quizTitle);
        System.out.println("Please enjoy these questions!\n");

        for(MCQ q : mcqList)
        {
            q.displayQuestion();
            System.out.print("Please input your answer (1-4): ");
            userAnswer = userIn.nextInt();
            while (userAnswer < 1 || userAnswer > 4) {
                System.out.println(userAnswer + " is an invalid choice.");
                System.out.print("Please enter an integer between 1 and 4: ");
                userAnswer = userIn.nextInt();
            }
            if (q.confirmAnswer(userAnswer)){
                numCorrect++;
            }
        }

        /* NOTE: below code implements the for-each loop above using an iterator.
         * Implementation was done for practice.
        Iterator<MCQ> q = mcqList.iterator();
        while (q.hasNext())
        {
            MCQ mcq = q.next();
            mcq.displayQuestion();
            System.out.print("Please input your answer (1-4): ");
            userAnswer = userIn.nextInt();
            while (userAnswer < 1 || userAnswer > 4) {
                System.out.println(userAnswer + " is an invalid choice.");
                System.out.print("Please enter an integer between 1 and 4: ");
                userAnswer = userIn.nextInt();
            }
            if (mcq.confirmAnswer(userAnswer)){
                numCorrect++;
            }
        } */

        //calculate quiz grade and output.
        System.out.print("Thank you for taking this quiz. You scored ");
        System.out.println(numCorrect + " out of " + mcqList.size());
        System.out.print("That has earned you a grade of " + ((double)numCorrect / mcqList.size()) * 100);
        System.out.println("%\n");

        System.out.println("That concludes this quiz. Goodbye!");
        userIn.close();
    }
}