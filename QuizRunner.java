import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizRunner extends JFrame {
    private JPanel panel;
    private JLabel questionLabel;
    private ButtonGroup bg; //Button groups allow only one button to be selected
    private List<JRadioButton> optionButtons;//radio button best option for multiple choice selections imo
    private JButton nextButton;
    private List<MCQ> mcqList;
    private int currentQuestion = 0;
    private int numCorrect = 0;

    public QuizRunner(List<MCQ> mcqs) {
        mcqList = mcqs;
        setTitle("Quiz Application");
        createComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
        displayQuestion(mcqList.get(0));
    }

    private void createComponents() {
        panel = new JPanel();
        add(panel);

        questionLabel = new JLabel();
        panel.add(questionLabel);
        createOptionButtons();
        createNextButton();
    }

    private void createOptionButtons() {
        bg = new ButtonGroup();
        optionButtons = new ArrayList<>(); //use to change buttons for questions
        for (int i = 0; i < 4; i++) {
            JRadioButton optionButton = new JRadioButton();
            bg.add(optionButton);
            optionButtons.add(optionButton);
            panel.add(optionButton);
        }
    }

    private void createNextButton() {
        nextButton = new JButton("Next");
        panel.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkAnswer()) { //check answer ever time next is clicked
                    if (currentQuestion < 3) {
                        currentQuestion++;
                        displayQuestion(mcqList.get(currentQuestion));//Also display new question each time, EXCEPT when quiz is done.
                    } 
                    else {
                        finishQuiz();
                    }
                }
            }
        });
    }

    private void displayQuestion(MCQ mcq) {
        //This is what changes each question
        questionLabel.setText(mcq.getQuestion());
        String[] options = mcq.getChoice();//getChoice returns array of all options to use each time, use these to modify Radio buttons
        for (int i = 0; i < 4; i++) {
            optionButtons.get(i).setText(options[i]);
        }
    }

    private boolean checkAnswer() {
        int selectedInd = 0;
        //Have to manually search for which button they picked each time, then check if that's the correct one.
        for (int i = 0; i < 4; i++) {
            if (optionButtons.get(i).isSelected()) {
                selectedInd = i;
                break;
            }
        }
        boolean isCorrect = mcqList.get(currentQuestion).confirmAnswer(selectedInd);
        if (isCorrect) {
            numCorrect++;
            //Option Pane will show message without disrupting current frame.
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }

    private void finishQuiz() {
        JOptionPane.showMessageDialog(this, "Quiz completed! You scored " + numCorrect + " out of 4", "Quiz Finished", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        try { 
            List<MCQ> mcqs = readMCQs("Quiz.txt");
            QuizRunner quizRunner = new QuizRunner(mcqs);
        } catch (IOException e) {
            System.err.println("Quiz file was not found. Please be sure files are in the same directory.");
        }
    }

    public static List<MCQ> readMCQs(String filepath) throws IOException {
        List<MCQ> mcqList = new ArrayList<>();
        try (BufferedReader fileIn = new BufferedReader(new FileReader(filepath))) {
            String line;
            boolean nextQuestion = true;
            String[] questionArr = new String[5];
            int temp = 0;
            //each new question is indicated by an empty line, trim and check for empty line to see if next line is a question or not
            while ((line = fileIn.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    nextQuestion = true;  
                } 
                else if (nextQuestion) {
                    questionArr[0] = line;
                    temp = 1;
                    nextQuestion = false;  
                } 
                else if (temp <= 4) {
                    questionArr[temp] = line;
                    temp++;
                    if (temp == 5) {
                        mcqList.add(new MCQ(questionArr));//creates each mcqList object
                        nextQuestion = true;  
                    }
                }
            }
        }
        return mcqList;
    }
    
}
