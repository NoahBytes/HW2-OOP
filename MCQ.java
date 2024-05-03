public class MCQ {
    //First string in question array will be the question. The last four strings are the answers.
    private String[] questionArr;
    private int correctInd; //correctAns stores the index of the correct answer.

    public MCQ(String[] qstn) {
        questionArr = new String[5];
        
        //"-" delimits correct answer on Quiz.txt file. Account for it by checking each question for it,
        //and excluding the character when it is found.
        for (int i = 0; i < 5; i++) {
            if (qstn[i].substring(0, 1).equals("-")) {
                correctInd = i;
                questionArr[i] = qstn[i].substring(1);
            }
            else {
                questionArr[i] = qstn[i];
            }
        }
    }

    public void displayQuestion() {
        System.out.println(questionArr[0]);
        for (int i = 1; i < 5; i++) {
            System.out.println(i + ".) " + questionArr[i]);
        }
    }

    /*Compares input integer to correct answer index. If integer matches,
    returns true. If not, returns false.*/
    public boolean confirmAnswer(int userAnswer) {
        if (userAnswer == correctInd) {
            System.out.println("That is the correct answer. Good job!\n");
            return true;
        }
        System.out.println("That is the wrong answer. Moving on...\n");
        return false;
    }
}
