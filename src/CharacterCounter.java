import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CharacterCounter extends JFrame {

    JSplitPane splitPane;
    JScrollPane scrollPane;
    JTextArea textArea;
    JPanel panel;
    JButton aboutButton;
    JCheckBox checkBox;
    JTextField textField;

    public static void main(String[] args) {
        new CharacterCounter().setVisible(true);
    }

    public CharacterCounter() {
        setTitle("中文字数统计工具");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        scrollPane = new JScrollPane();
        textArea = new JTextArea("在这里粘贴需要检测的文本");
        scrollPane.setViewportView(textArea);
        scrollPane.setSize(new Dimension(800, 600));

        panel = new JPanel(new GridLayout(1, 3));

        aboutButton = new JButton("关于");
        checkBox = new JCheckBox("包含标点", false);
        textField = new JTextField();

        textField.setEditable(false);

        panel.add(aboutButton);
        panel.add(checkBox);
        panel.add(textField);

        splitPane.setDividerLocation(0.2d);

        //splitPane.setTopComponent(scrollPane);
        //splitPane.setBottomComponent(panel);
        //add(splitPane);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        aboutButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("About Button Clicked !");
                String message = "中文字数统计工具\n@William Xie\n有bug联系QQ\n祝大家WA拿个好成绩!";
                JOptionPane.showMessageDialog(CharacterCounter.this, message, "关于", JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("Text Inserted !");
                refreshCounter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("Text Removed!");
                refreshCounter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("Text Property Changed !");
            }
        });

        checkBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!checkBox.isSelected()) {
                    System.out.print("Do not ");
                }
                System.out.println("Contains Symbols !");
                refreshCounter();
            }
        });

        refreshCounter();
    }

    private void refreshCounter() {
        String text = textArea.getText();
        String countedText = "";
        boolean isEmpty = true;
        boolean countSymbols = checkBox.isSelected();

        if (text.length() != 0) {
            if (!countSymbols) {
                //[\u4e00-\u9fa5]|
                countedText = text.replaceAll("[\\（\\）\\《\\》\\——\\；\\，\\。\\“\\”\\<\\>\\！\\n\\r]", "");
                if (countedText.length() != 0) {
                    isEmpty = false;
                }
            } else {
                isEmpty = false;
                countedText = text;
            }
        }

        if (isEmpty) {
            textField.setText("字数：" + 0);
        } else {
            textField.setText("字数：" + countedText.length());
        }
    }
}
