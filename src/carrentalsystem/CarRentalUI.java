
package carrentalsystem;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CarRentalUI {

    private CarRentalSystem system;
    private JTextArea output;
    private JPanel carGrid;

    // ===== COLORS =====
    private static final Color BG_DARK      = new Color(26, 26, 46);
    private static final Color BG_CARD      = new Color(22, 33, 62);
    private static final Color BG_TERMINAL  = new Color(13, 17, 23);
    private static final Color ACCENT_RED   = new Color(233, 69, 96);
    private static final Color ACCENT_BLUE  = new Color(88, 166, 255);
    private static final Color ACCENT_GREEN = new Color(82, 196, 26);
    private static final Color TEXT_MUTED   = new Color(136, 136, 136);
    private static final Color TEXT_LIGHT   = new Color(238, 238, 238);
    private static final Color BORDER_COLOR = new Color(15, 52, 96);

    private static final String[] IMAGE_PATHS = {
        "/carrentalsystem/images/car1.png",
        "/carrentalsystem/images/car2.png",
        "/carrentalsystem/images/car3.png"
    };

    public CarRentalUI() {
        system = new CarRentalSystem();
        system.addCar(new Car("C001", "Toyota", "Camry", 60));
        system.addCar(new Car("C002", "Honda", "Accord", 70));
        system.addCar(new Car("C003", "Mahindra", "Thar", 150));

        JFrame frame = new JFrame("Car Rental System");
        frame.setSize(860, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BG_DARK);

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));

        JLabel title = new JLabel("CAR RENTAL SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(ACCENT_RED);
        title.setBorder(new EmptyBorder(16, 0, 4, 0));

        JLabel subtitle = new JLabel("Premium Fleet Management", JLabel.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setBorder(new EmptyBorder(0, 0, 12, 0));

        header.add(title, BorderLayout.CENTER);
        header.add(subtitle, BorderLayout.SOUTH);
        frame.add(header, BorderLayout.NORTH);

        // ===== CAR GRID =====
        carGrid = new JPanel(new GridLayout(1, 3, 12, 0));
        carGrid.setBackground(BG_DARK);
        carGrid.setBorder(new EmptyBorder(16, 16, 8, 16));
        buildCarCards();

        // ===== OUTPUT TERMINAL =====
        output = new JTextArea(5, 40);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setEditable(false);
        output.setBackground(BG_TERMINAL);
        output.setForeground(ACCENT_BLUE);
        output.setBorder(new EmptyBorder(10, 12, 10, 12));
        output.setText("> System ready. Select an action below.");

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(33, 38, 45)));

        JPanel outputWrap = new JPanel(new BorderLayout());
        outputWrap.setBackground(BG_DARK);
        outputWrap.setBorder(new EmptyBorder(4, 16, 8, 16));
        outputWrap.add(scroll, BorderLayout.CENTER);

        // ===== CENTER =====
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BG_DARK);
        center.add(carGrid, BorderLayout.NORTH);
        center.add(outputWrap, BorderLayout.CENTER);
        frame.add(center, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        btnPanel.setBackground(BG_CARD);
        btnPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        JButton showBtn   = makeButton("Show Cars",  BG_CARD,                   ACCENT_BLUE,  new Color(31, 111, 235));
        JButton rentBtn   = makeButton("Rent Car",   ACCENT_RED,                Color.WHITE,  ACCENT_RED);
        JButton returnBtn = makeButton("Return Car", new Color(26, 71, 42),      ACCENT_GREEN, new Color(46, 160, 67));

        btnPanel.add(showBtn);
        btnPanel.add(rentBtn);
        btnPanel.add(returnBtn);
        frame.add(btnPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====

        showBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("> Fleet Status\n\n");
            for (Car car : system.cars) {
                sb.append(String.format("  %-6s  %-22s  %s%n",
                    car.getCarId(),
                    car.getBrand() + " " + car.getModel(),
                    car.isAvailable() ? "[Available]" : "[Rented]"));
            }
            output.setText(sb.toString());
        });

        rentBtn.addActionListener(e -> {
            String carId = showInputDialog(frame, "Enter Car ID to rent:", "Rent a Car");
            if (carId == null) return;

            String name = showInputDialog(frame, "Enter your name:", "Rent a Car");
            if (name == null || name.trim().isEmpty()) return;

            String d = showInputDialog(frame, "Enter number of days:", "Rent a Car");
            if (d == null) return;

            try {
                int days = Integer.parseInt(d.trim());
                if (days <= 0) {
                    output.setText("> ERROR: Days must be greater than 0.");
                    return;
                }

                for (Car car : system.cars) {
                    if (car.getCarId().equalsIgnoreCase(carId.trim())) {
                        if (!car.isAvailable()) {
                            output.setText("> ERROR: Car " + car.getCarId() + " is already rented.");
                            return;
                        }
                        double total = car.calculatePrice(days);
                        car.rent();
                        output.setText(String.format(
                            "> Rental Confirmed!\n\n" +
                            "  Car      : %s %s (%s)\n" +
                            "  Customer : %s\n" +
                            "  Days     : %d\n" +
                            "  Total    : \u20B9%.2f",
                            car.getBrand(), car.getModel(), car.getCarId(),
                            name.trim(), days, total));
                        buildCarCards();
                        return;
                    }
                }
                output.setText("> ERROR: Car ID \"" + carId + "\" not found.");

            } catch (NumberFormatException ex) {
                output.setText("> ERROR: Please enter a valid number for days.");
            }
        });

        returnBtn.addActionListener(e -> {
            String carId = showInputDialog(frame, "Enter Car ID to return:", "Return a Car");
            if (carId == null) return;

            for (Car car : system.cars) {
                if (car.getCarId().equalsIgnoreCase(carId.trim())) {
                    if (car.isAvailable()) {
                        output.setText("> ERROR: Car " + car.getCarId() + " is not currently rented.");
                        return;
                    }
                    car.returnCar();
                    output.setText("> Return Successful!\n\n" +
                        "  Car ID : " + car.getCarId() + "\n" +
                        "  Model  : " + car.getBrand() + " " + car.getModel() + "\n" +
                        "  Status : Now available for rent.");
                    buildCarCards();
                    return;
                }
            }
            output.setText("> ERROR: Car ID \"" + carId + "\" not found.");
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ===== BUILD / REFRESH CAR CARDS =====
    private void buildCarCards() {
        carGrid.removeAll();
        int i = 0;
        for (Car car : system.cars) {
            carGrid.add(makeCarCard(car, IMAGE_PATHS[i++]));
        }
        carGrid.revalidate();
        carGrid.repaint();
    }

    // ===== CAR CARD =====
    private JPanel makeCarCard(Car car, String imagePath) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        // --- Image ---
        try {
            java.net.URL imgURL = getClass().getResource(imagePath);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(160, 95, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaled), JLabel.CENTER);
                card.add(imgLabel, BorderLayout.NORTH);
            } else {
                throw new Exception("Image not found");
            }
        } catch (Exception e) {
            JLabel fallback = new JLabel(car.getCarId().equals("C003") ? "🛻" :
                                         car.getCarId().equals("C002") ? "🚙" : "🚗", JLabel.CENTER);
            fallback.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
            fallback.setPreferredSize(new Dimension(160, 95));
            card.add(fallback, BorderLayout.NORTH);
        }

        // --- Info Panel ---
        JLabel badge = new JLabel(car.isAvailable() ? "● Available" : "● Rented", JLabel.CENTER);
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setForeground(car.isAvailable() ? ACCENT_GREEN : ACCENT_RED);

        JLabel carName = new JLabel(car.getBrand() + " " + car.getModel(), JLabel.CENTER);
        carName.setFont(new Font("Arial", Font.BOLD, 13));
        carName.setForeground(TEXT_LIGHT);

        JLabel carId = new JLabel(car.getCarId(), JLabel.CENTER);
        carId.setFont(new Font("Arial", Font.PLAIN, 11));
        carId.setForeground(TEXT_MUTED);

        JLabel price = new JLabel("\u20B9" + (int) car.calculatePrice(1) + " / day", JLabel.CENTER);
        price.setFont(new Font("Arial", Font.BOLD, 12));
        price.setForeground(ACCENT_RED);

        JPanel info = new JPanel(new GridLayout(4, 1, 0, 4));
        info.setBackground(BG_CARD);
        info.add(badge);
        info.add(carName);
        info.add(carId);
        info.add(price);

        card.add(info, BorderLayout.CENTER);
        return card;
    }

    // ===== STYLED INPUT DIALOG =====
    private String showInputDialog(JFrame parent, String message, String title) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(BG_CARD);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lbl = new JLabel(message);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(TEXT_LIGHT);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        UIManager.put("OptionPane.background", BG_CARD);
        UIManager.put("Panel.background", BG_CARD);

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        return (result == JOptionPane.OK_OPTION) ? field.getText() : null;
    }

    // ===== BUTTON FACTORY =====
    private JButton makeButton(String text, Color bg, Color fg, Color border) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(border, 1),
            new EmptyBorder(10, 0, 10, 0)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarRentalUI::new);
    }
}