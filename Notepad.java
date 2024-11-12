import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad {
    private JFrame frame;
    private JTextArea textArea;

    public Notepad() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 30));
        JScrollPane scrollPane = new JScrollPane(textArea);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
         
          
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
         JMenu editMenu = new JMenu("Edit");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
          JMenuItem copyMenuItem = new JMenuItem("Copy");
         JMenuItem pasteMenuItem = new JMenuItem("Paste");
         JMenu viewMenu = new JMenu("View");
         JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");
          JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");


        openMenuItem.addActionListener(new OpenMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuItemListener());
        exitMenuItem.addActionListener(new ExitMenuItemListener());
        cutMenuItem.addActionListener(new CutMenuItemListener());
        copyMenuItem.addActionListener(new CopyMenuItemListener());
        pasteMenuItem.addActionListener(new PasteMenuItemListener());
        zoomInMenuItem.addActionListener(new ZoomInMenuItemListener());
        zoomOutMenuItem.addActionListener(new ZoomOutMenuItemListener());
        

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        

        editMenu.add(cutMenuItem);
      editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

      viewMenu.add(zoomInMenuItem);
          viewMenu.add(zoomOutMenuItem);

         menuBar.add(fileMenu); 
        menuBar.add(editMenu);
            menuBar.add(viewMenu);

        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private class OpenMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    reader.close();
                    textArea.setText(content.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class SaveMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ExitMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    private class CutMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.cut();
        }
    }

    private class CopyMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.copy();
        }
    }

    private class PasteMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.paste();
        }
    }

    private class ZoomInMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Font currentFont = textArea.getFont();
            float newSize = currentFont.getSize() + 2;
            textArea.setFont(currentFont.deriveFont(newSize));
        }
    }

    private class ZoomOutMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Font currentFont = textArea.getFont();
            float newSize = currentFont.getSize() - 2;
            textArea.setFont(currentFont.deriveFont(newSize));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Notepad();
            }
        });
    }
}
