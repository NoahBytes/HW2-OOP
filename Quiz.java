import java.io.*;
import java.util.*;

public class Quiz {
    private BufferedReader fileIn;
    private String quizTitle;
    private List<MCQ> mcqList;
    private int numCorrect;

    public Quiz(String filepath) throws FileNotFoundException{
        fileIn = new BufferedReader(new FileReader("Quiz.txt"));
        mcqList = new LinkedList<MCQ>(); //HW2: Updated to LinkedList data structure
        numCorrect = 0;
    }

    //Reads questions into individual MCQ object and adds to the MCQ list.
    //Returns false if file is empty, returns true if file is read w/o issue
    //Precondition: If file is not empty, we assume it is formatted correctly and has at least one question.
    public boolean ReadInQuestions() {
        //catching case where file is empty and exiting program
        try {
			quizTitle = fileIn.readLine();
            fileIn.readLine(); //skip empty line
		} catch (IOException e) {
			System.out.println("File is empty! Ensure Quiz.txt has questions next time.");
            return false; //failure exit
		}

        //File guaranteed to have at least one question, so begin reading
        //Catch block is for when there are no more questions left, to exit reading file
        try {
            String tempQuestion[] = new String[5]; //Store questions to be passed into constructor
            String tempLine = null;

            do {
                for (int i = 0; i < 5; i++) {
                    tempLine = fileIn.readLine();
                    tempQuestion[i] = tempLine;
                }
                mcqList.add(new MCQ(tempQuestion));
                tempLine = fileIn.readLine(); //skips empty line 
            } while (tempLine != null);

        } catch (IOException e) {
            System.out.println("Error while reading in questions.");
            return false; //failure exit
        }

        return true; //successful exit
    }

    /*runQuiz takes care of the active user interaction part of the exam: displaying
     *the questions, reading in answers, and finally, displaying the score. */
    public void runQuiz() {
        Scanner userIn = new Scanner(System.in);
        int userAnswer = 0;

        System.out.println("Welcome to the " + quizTitle);
        System.out.println("Please enjoy these questions!\n");
        
        
        /* for-each loop displays the questions, reads in answer (checking for bounds),
         * and updates numCorrect.
         * HW2: for loop replaced with for-each loop utilizing function of Collections framework.
         */
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
