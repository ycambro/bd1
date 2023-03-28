package tec.bd.gui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.*;

import tec.bd.ApplicationContext;
import tec.bd.weather.model.ReportType;
import tec.bd.weather.storage.InMemoryWeatherReportStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeFrame extends JDialog implements ActionListener {
    private JButton cancel;

    private JButton get;

    private JDialog dialog;

    private JTextField text;

    private JLabel panel;

    private ImageIcon background;

    private Image myIcon;

    private String input;

    private static ApplicationContext APP_CONTEXT = ApplicationContext.init();

    private InMemoryWeatherReportStorage storage = new InMemoryWeatherReportStorage();

    public CodeFrame(MainFrame father, boolean modal) {
        dialog = new JDialog(father, modal);
        dialog.setSize(1000, 500);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null);
        dialog.getContentPane().setBackground(Color.CYAN);

        myIcon = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/logo.png"));
        dialog.setIconImage(myIcon);

        cancel = new JButton("Cancel");
        cancel.setBounds(200, 400, 150, 50);
        cancel.addActionListener(this);
        cancel.setBackground(Color.GRAY);

        get = new JButton("Get Weather");
        get.setBounds(650, 400, 150, 50);
        get.addActionListener(this);
        get.setBackground(Color.GRAY);

        background = new ImageIcon(MainFrame.class.getResource("/back.jpg"));
        panel = new JLabel(" ", background, SwingConstants.CENTER);
        panel.setSize(1000, 500);
        panel.setLocation(new Point(1, 1));

        text = new JTextField();
        text.setBounds(150, 300, 700, 75);
        panel.setLocation(new Point(1, 2));
        Font font = new FontUIResource("Serif", 1, 16);
        text.setFont(font);

        dialog.add(panel);
        dialog.add(text);
        dialog.add(get);
        dialog.add(cancel);

        dialog.setVisible(true);
    }

    public void getReport(String zipCode) {
        try {

            var now = new Date();
            var formats = new SimpleDateFormat("dd-MM-YY");

            var report = storage.find((formats.format(now)) + "-" + ReportType.BY_ZIPCODE + "-" + zipCode);

            if (report == null) {
                var weatherService = APP_CONTEXT.getWeatherService();
                var newReport = weatherService.getByZipCode(zipCode);
                text.setText("Temperature: " + newReport.getTemperature() + "\n" +
                        "Humidity: " + newReport.getHumidity() + "\n" +
                        "Pressure: " + newReport.getPressure() + "\n" +
                        "Temperature Min: " + newReport.getMinTemperature() + "\n" +
                        "Temperature Max: " + newReport.getMaxTemperature() + "\n\n");
                storage.save(newReport, zipCode);
            } else {
                text.setText("Temperature: " + report.getTemperature() + "\n" +
                        "Humidity: " + report.getHumidity() + "\n" +
                        "Pressure: " + report.getPressure() + "\n" +
                        "Temperature Min: " + report.getMinTemperature() + "\n" +
                        "Temperature Max: " + report.getMaxTemperature() + "\n\n");
            }

        } catch (Exception e) {
            text.setText("Insert a valid value!\n");
        }

    }

    /* Observador */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            dialog.dispose();
        }
        if (e.getSource() == get) {
            input = text.getText();
            if (input.equals("")) {

            } else {
                getReport(input);
            }
        }
    }
}
