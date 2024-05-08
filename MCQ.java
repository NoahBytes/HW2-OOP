import java.util.Arrays;

public class MCQ {

    private String[] questionArr;
    private int correctInd; 

    public MCQ(String[] qstn) {
        questionArr = new String[5];
        for (int i = 0; i < 5; i++) {
            if (qstn[i] != null && !qstn[i].isEmpty()) {
                if (qstn[i].charAt(0) == '-') {
                    correctInd = i;
                    questionArr[i] = qstn[i].substring(1);
                } else {
                    questionArr[i] = qstn[i];
                }
            } else {
                questionArr[i] = "";
            }
        }
    }
    

     public void displayQuestion() {
         System.out.println(questionArr[0]);
         for (int i = 1; i < 5; i++) {
             System.out.println(i + ".) " + questionArr[i]);
        }
    }
    

    public String getQuestion() {
        return questionArr[0];
    }
    public String[] getChoice() {
        return Arrays.copyOfRange(questionArr, 1, 5);
    }

    public boolean confirmAnswer(int userAnswer) {
        return userAnswer == correctInd - 1;
        // if (userAnswer == correctInd) {
        //     System.out.println("That is the correct answer. Good job!\n");
        //     return true;
        // }
        // System.out.println("That is the wrong answer. Moving on...\n");
        // return false;
    }
}