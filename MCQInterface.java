interface MCQInterface {
  public void displayQuestion();
  public String getQuestion();
  public String[] getChoice();
  public boolean confirmAnswer(int userAnswer);

}
