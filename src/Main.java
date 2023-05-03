import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Form");
        frame.setContentPane(new Form().mainPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,350);

    }
}