import Service.ExporterService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Form {
    public JPanel mainPanel;
    private JButton submitButton;
    private JLabel label1;
    private JRadioButton CCARadioButton;
    private JRadioButton CSARadioButton;
    private JRadioButton TRRadioButton;
    private JTextArea textArea1;

    public Form() {
        label1.setFont(new Font("Serif", Font.BOLD, 18));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exporting SQL File");
                String pr = "";
                if (CCARadioButton.isSelected()){
                    pr = "CCA";
                }else if (CSARadioButton.isSelected()){
                    pr = "CSA";
                } else if (TRRadioButton.isSelected()) {
                    pr = "TR";
                }
                System.out.println("type : "+ pr);

                List<String> list = new ArrayList<String>(Arrays.asList(textArea1.getText().split(", ")));

                try {
                    ExporterService service = new ExporterService();
                    service.genFileNew(list, pr);
                    System.out.println("File Generated \n");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
