package ru.sumenkov.dae;

import jxl.read.biff.BiffException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        JRadioButton radioButtonDBF = new JRadioButton("DBF to Excel", true);
        JRadioButton radioButtonExcel = new JRadioButton("Excel to DBF");
        JPanel panel_top = new JPanel();
        panel_top.setBorder(BorderFactory.createTitledBorder("Направление конвертации"));
        panel_top.add(radioButtonDBF);
        panel_top.add(radioButtonExcel);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonDBF);
        buttonGroup.add(radioButtonExcel);

// isSelected() : он вернет логическое значение true или false, если выбран JRadioButton, он вернет true, в противном случае false.
//        System.out.println(radioButtonDBF.isSelected());
//        System.out.println(radioButtonExcel.isSelected());


        JLabel label = new JLabel("Выберите директорию с файлами:");
        label.setFont(new Font("Serif", Font.PLAIN, 18));
        JLabel label2 = new JLabel("");
        label2.setFont(new Font("Serif", Font.PLAIN, 18));
        JButton button = new JButton("Выбрать");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = chooser.showDialog(null, "Выбрать директорию");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    Path dirIn = chooser.getSelectedFile().toPath();
                    label2.setText(dirIn.toString());
                }
            }
        });
        JPanel panel_center = new JPanel();
        panel_center.add(label);
        panel_center.add(button);
        panel_center.add(label2);


        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setWheelScrollingEnabled(true);

        JPanel panel_log = new JPanel(new GridLayout(1, 1));
        panel_log.setBorder(BorderFactory.createTitledBorder("log:"));
        panel_log.add(scrollPane);


        JButton starButton = new JButton("Запуск");
        starButton.setBounds(95, 40, 85, 30);
        starButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButtonDBF.isSelected()) {
                    try (DirectoryStream<Path> files = Files.newDirectoryStream(Path.of(label2.getText()))) {
                        for (Path file : files) {
                            String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                            if (substring.equalsIgnoreCase("dbf")) {
                                ReaderDBF reader = new ReaderDBF();
                                textArea.append("Конвертируем файл: " + file.getFileName() + " ... ");
                                reader.readDBFFile(file.toString());
                                textArea.append("Готово.\n");
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try (DirectoryStream<Path> files = Files.newDirectoryStream(Path.of(label2.getText()))) {
                        for (Path file : files) {
                            String substring = file.toString().substring(file.toString().lastIndexOf(".") + 1);
                            if (substring.equalsIgnoreCase("xls")) {
                                ReaderExcel reader = new ReaderExcel();
                                textArea.append("Конвертируем файл: " + file.getFileName() + " ... ");
                                reader.readExcel(file.toString());
                                textArea.append("Готово.\n");
                            }
                        }
                    } catch (IOException | BiffException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                textArea.append("Закончили.\n");
            }
        });
        JPanel panel_bottom = new JPanel();
        panel_bottom.add(starButton);

        JFrame frame = new JFrame("DBF and Excel");
        frame.setPreferredSize(new Dimension(600, 400));

        var container = frame.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(panel_top);
        container.add(panel_center);
        container.add(panel_log);
        container.add(panel_bottom);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
