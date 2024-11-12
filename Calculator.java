import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame frame;
    private JTextField resultTextField;

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        resultTextField = new JTextField();
        resultTextField.setEditable(false);
        resultTextField.setFont(resultTextField.getFont().deriveFont(50f));
        frame.add(resultTextField, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
             button.setFont(button.getFont().deriveFont(15f)); 
            buttonsPanel.add(button);
            

        }

        frame.add(buttonsPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        
         
      }
    

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

               if (command.equals("=")) {
                    calculateResult();
          } else if (command.equals("Backspace")) {
            backspace();
          } else if (command.equals("moveLeft")) {
            moveCursorLeft();
          } else if (command.equals("moveRight")) {
            moveCursorRight();
          } else {
            appendToResult(command);
          }
        }
     
          private void backspace() {
            String currentText = resultTextField.getText();
            if (!currentText.isEmpty()) {
                resultTextField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }

        private void moveCursorLeft() {
            int caretPosition = resultTextField.getCaretPosition();
            if (caretPosition > 0) {
                resultTextField.setCaretPosition(caretPosition - 1);
            }
        }

        private void moveCursorRight() {
            int caretPosition = resultTextField.getCaretPosition();
            int textLength = resultTextField.getText().length();
            if (caretPosition < textLength) {
                resultTextField.setCaretPosition(caretPosition + 1);
            }
        }
        private void calculateResult() {
            String expression = resultTextField.getText();
            try {
                double result = evaluateExpression(expression);
                resultTextField.setText(Double.toString(result));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid expression", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void appendToResult(String text) {
            resultTextField.setText(resultTextField.getText() + text);
        }
        
        
       
    private double evaluateExpression(String expression) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        try {
            return ((Number) engine.eval(expression)).doubleValue();
        } catch (ScriptException e) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
 }
         
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}

