// Code by John Esperancilla
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.util.*;

public class BusTrackingSystem extends JFrame implements ActionListener {
    private JComboBox<String> currentCityComboBox;
    private JComboBox<String> destinationCityComboBox;
    private JButton generateTicketButton;
    private JButton printTicketButton;
    private JButton clearButton;
    private JLabel busPlateNumberLabel;
    private JLabel vacantSeatsLabel;
    private JLabel driverNameLabel;
    private JLabel referenceNumberLabel;
    private JLabel nearestBusLabel;
    private JLabel estimatedTimeOfArrivalLabel;

    private String[] cities = {"Bacolod City", "Bago City", "Cadiz City", "Escalante City", "Himamaylan City", "Kabankalan City", "La Carlota City", "Sagay City", "San Carlos City", "Silay City", "Sipalay City", "Talisay City", "Victorias City", "Binalbagan", "Calatrava", "Candoni", "Cauayan", "Enrique B. Magalona", "Hinigaran", "inoba-an", "Ilog", "Is", "La Castellana", "Manapla", "Moises Padilla", "Murcia", "ontevedra", "Pulupandan", "Salvador Benedicto", "San Enrique", "Toboso", "Valladolid"};

    private ArrayList<Bus> buses = new ArrayList<Bus>();
    private Ticket currentTicket;

// Code by John Esperancilla

    public BusTrackingSystem() {
        setTitle("Public Bus Tracking System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        currentCityComboBox = new JComboBox<String>(cities);
        add(new JLabel("Current City/Municipality:"));
        add(currentCityComboBox);

        destinationCityComboBox = new JComboBox<String>(cities);
        add(new JLabel("City/Municipality of Destination:"));
        add(destinationCityComboBox);

        generateTicketButton = new JButton("Generate Ticket");
        generateTicketButton.addActionListener(this);
        add(generateTicketButton);

        printTicketButton = new JButton("Print Ticket");
        printTicketButton.addActionListener(this);
        printTicketButton.setEnabled(false);
        add(printTicketButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        add(clearButton);

// Code by John Esperancilla

        busPlateNumberLabel = new JLabel();
        add(new JLabel("Bus Plate Number:"));
        add(busPlateNumberLabel);

        vacantSeatsLabel = new JLabel();
        add(new JLabel("Vacant Seats:"));
        add(vacantSeatsLabel);

        driverNameLabel = new JLabel();
        add(new JLabel("Bus Driver:"));
        add(driverNameLabel);

        referenceNumberLabel = new JLabel();
        add(new JLabel("Reference Number:"));
        add(referenceNumberLabel);

        nearestBusLabel = new JLabel();
        add(new JLabel("Nearest Bus:"));
        add(nearestBusLabel);

        estimatedTimeOfArrivalLabel = new JLabel();
        add(new JLabel("Estimated Time of Arrival:"));
        add(estimatedTimeOfArrivalLabel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateTicketButton) {
            String currentCity = (String) currentCityComboBox.getSelectedItem();
            String destinationCity = (String) destinationCityComboBox.getSelectedItem();
            Bus bus = getRandomBus();
            currentTicket = new Ticket(currentCity, destinationCity);
            String referenceNumber = generateReferenceNumber();
            currentTicket.setReferenceNumber(referenceNumber);
            displayTicket(bus);
            displayNearestBus(bus);
            printTicketButton.setEnabled(true);
        } else if (e.getSource() == printTicketButton) {
            printTicket(currentTicket);
        } else if (e.getSource() == clearButton) {
            clearEntries();
        }
    }

// Code by John Esperancilla

    private Bus getRandomBus() {
        if (buses.isEmpty()) {
            buses.add(new Bus("ABC123", 7, "Juan Dela Cruz", "Bacolod City"));
            buses.add(new Bus("DEF456", 9, "Maria Garcia", "Bago City"));
            buses.add(new Bus("GHI789", 3, "Pedro Santos", "Cadiz City"));
            buses.add(new Bus("ADE431", 12, "John Paul Reyes", "Sagay City"));
            buses.add(new Bus("QEZ412", 14, "Joshua Magracia", "Municipality of Manapla"));
        }
        Random random = new Random();
        return buses.get(random.nextInt(buses.size()));
    }

    private String generateReferenceNumber() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

// Code by John Esperancilla

    private void displayTicket(Bus bus) {
        busPlateNumberLabel.setText(bus.getPlateNumber());
        vacantSeatsLabel.setText(Integer.toString(bus.getVacantSeats()));
        driverNameLabel.setText(bus.getDriverName());
        referenceNumberLabel.setText(currentTicket.getReferenceNumber());
    }

    private void displayNearestBus(Bus bus) {
        nearestBusLabel.setText(bus.getPlateNumber());
        estimatedTimeOfArrivalLabel.setText(generateEstimatedTimeOfArrival());
    }

    private String generateEstimatedTimeOfArrival() {
        Random random = new Random();
        int minutes = random.nextInt(30) + 1;
        return Integer.toString(minutes) + " minutes";
    }

    private void printTicket(Ticket ticket) {
        JTextArea textArea = new JTextArea();
        textArea.append("Reference Number: " + ticket.getReferenceNumber() + "\n");
        textArea.append("Current City/Municipality: " + ticket.getCurrentCity() + "\n");
        textArea.append("City/Municipality of Destination: " + ticket.getDestinationCity() + "\n");
        textArea.append("Bus Plate Number: " + busPlateNumberLabel.getText() + "\n");
        textArea.append("Vacant Seats: " + vacantSeatsLabel.getText() + "\n");
        textArea.append("Bus Driver: " + driverNameLabel.getText() + "\n");
        textArea.append("Nearest Bus: " + nearestBusLabel.getText() + "\n");
        textArea.append("Estimated Time of Arrival: " + estimatedTimeOfArrivalLabel.getText() + "\n");

        try {
            textArea.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    private void clearEntries() {
        currentCityComboBox.setSelectedIndex(0);
        destinationCityComboBox.setSelectedIndex(0);
        busPlateNumberLabel.setText("");
        vacantSeatsLabel.setText("");
        driverNameLabel.setText("");
        referenceNumberLabel.setText("");
        nearestBusLabel.setText("");
        estimatedTimeOfArrivalLabel.setText("");
        printTicketButton.setEnabled(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new BusTrackingSystem();
    }
}

// Code by John Esperancilla

class Bus {
    private String plateNumber;
    private int vacantSeats;
    private String driverName;
    private String currentCity;

    public Bus(String plateNumber, int vacantSeats, String driverName, String currentCity) {
        this.plateNumber = plateNumber;
        this.vacantSeats = vacantSeats;
        this.driverName = driverName;
        this.currentCity = currentCity;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getVacantSeats() {
        return vacantSeats;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCurrentCity() {
        return currentCity;
    }
}

// Code by John Esperancilla

class Ticket {
    private String referenceNumber;
    private String currentCity;
    private String destinationCity;

    public Ticket(String currentCity, String destinationCity) {
        this.currentCity = currentCity;
        this.destinationCity = destinationCity;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }
}
