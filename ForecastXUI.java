import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ForecastXUI extends JFrame {
    private JTextField searchField;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public ForecastXUI() {
        setTitle("ForecastX");
        setSize(1200, 800); // Increased size to accommodate travel plans
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Use CardLayout for multiple pages
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        
        // Main panel (home page)
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250),
                        0, getHeight(), new Color(176, 224, 230));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, "home");
        
        // Create a centered content panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Taskbar panel at the top
        JPanel taskbarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        taskbarPanel.setOpaque(false);
        taskbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton homeBtn = createTaskbarButton("Home");
        JButton forecastBtn = createTaskbarButton("Forecast");
        JButton alertsBtn = createTaskbarButton("Alerts");
        JButton mapsBtn = createTaskbarButton("Maps");
        JButton settingsBtn = createTaskbarButton("Settings");
        
        taskbarPanel.add(homeBtn);
        taskbarPanel.add(forecastBtn);
        taskbarPanel.add(alertsBtn);
        taskbarPanel.add(mapsBtn);
        taskbarPanel.add(settingsBtn);
        
        centerPanel.add(taskbarPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Header with title - properly centered
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel title = new JLabel("ForecastX", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        headerPanel.add(title, BorderLayout.CENTER);
        
        // Auth buttons panel
        JPanel authPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        authPanel.setOpaque(false);
        
        JButton signIn = new JButton("Sign In");
        JButton register = new JButton("Register");
        signIn.setBackground(new Color(0, 123, 255));
        signIn.setForeground(Color.WHITE);
        register.setBackground(new Color(0, 123, 255));
        register.setForeground(Color.WHITE);
        authPanel.add(signIn);
        authPanel.add(register);
        
        headerPanel.add(authPanel, BorderLayout.SOUTH);
        centerPanel.add(headerPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Subtitle (centered)
        JPanel subtitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        subtitlePanel.setOpaque(false);
        
        JLabel subtitle = new JLabel("<html><div style='text-align:center;'>A Fresh, 1st and 2nd Analysis and Prediction System<br>making forecasts useful for everyday life, travel, and agriculture.</div></html>");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitlePanel.add(subtitle);
        
        centerPanel.add(subtitlePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Search bar (above events)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchField = new JTextField(30);
        searchField.setPreferredSize(new Dimension(300, 35));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 123, 255));
        searchButton.setForeground(Color.WHITE);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        centerPanel.add(searchPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Weather Events Panel - properly centered with larger boxes
        JPanel eventsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        eventsPanel.setOpaque(false);
        eventsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Create larger event cards
        eventsPanel.add(createEventCard("Heavy Rainfall in Mumbai", "Expect waterlogging in low-lying areas. Travel with caution."));
        eventsPanel.add(createEventCard("Heatwave Alert in Delhi", "Temperature expected to cross 42°C. Stay hydrated and avoid direct sun."));
        eventsPanel.add(createEventCard("Cyclone Warning in Chennai", "Strong winds and heavy rainfall predicted along coastal areas."));

        centerPanel.add(eventsPanel);

        // Search action - show forecast page
        searchButton.addActionListener(e -> {
            String city = searchField.getText().trim();
            if (!city.isEmpty()) {
                showForecastPage(city);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a city!");
            }
        });
        
        // Create the forecast page (initially hidden)
        createForecastPage();
    }

    private JButton createTaskbarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private JPanel createEventCard(String title, String description) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased padding
        card.setOpaque(true);
        
        // Set larger preferred size to accommodate text
        card.setPreferredSize(new Dimension(280, 180)); // Increased width and height
        card.setMaximumSize(new Dimension(280, 180)); // Ensure consistent size

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger font
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Use HTML with proper width and text alignment
        JLabel descLabel = new JLabel("<html><div style='text-align:center; width: 240px; padding: 5px;'>" + description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15))); // More space between title and description
        card.add(descLabel);

        return card;
    }
    
    private void createForecastPage() {
        JPanel forecastPanel = new JPanel(new BorderLayout());
        
        // Forecast panel background
        forecastPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250),
                        0, getHeight(), new Color(176, 224, 230));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(new Color(0, 123, 255));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "home"));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(backButton);
        forecastPanel.add(topPanel, BorderLayout.NORTH);
        
        // Forecast content will be added dynamically in showForecastPage()
        add(forecastPanel, "forecast");
    }
    
    private void showForecastPage(String city) {
        // Get the forecast panel
        JPanel forecastPanel = (JPanel) getContentPane().getComponent(1);
        forecastPanel.removeAll(); // Clear previous content
        
        // Use a scroll pane for the forecast page
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        scrollPane.setViewportView(contentPanel);
        forecastPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add back button
        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(new Color(0, 123, 255));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(getContentPane(), "home"));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(backButton);
        forecastPanel.add(topPanel, BorderLayout.NORTH);
        
        // Create forecast content
        
        // City name
        JLabel cityLabel = new JLabel("Weather Forecast for " + city);
        cityLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        cityLabel.setForeground(Color.WHITE);
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(cityLabel);
        
        // Weather icon (placeholder)
        JLabel weatherIcon = new JLabel("☀️");
        weatherIcon.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        weatherIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(weatherIcon);
        
        // Temperature
        JLabel tempLabel = new JLabel("28°C");
        tempLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        tempLabel.setForeground(Color.WHITE);
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(tempLabel);
        
        // Weather description
        JLabel descLabel = new JLabel("Sunny");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        descLabel.setForeground(Color.WHITE);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(descLabel);
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        detailsPanel.add(createDetailCard("Humidity", "60%"));
        detailsPanel.add(createDetailCard("Wind Speed", "10 km/h"));
        detailsPanel.add(createDetailCard("Pressure", "1013 hPa"));
        detailsPanel.add(createDetailCard("Visibility", "10 km"));
        
        contentPanel.add(detailsPanel);
        
        // 3-day forecast
        JLabel forecastTitle = new JLabel("3-Day Forecast");
        forecastTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        forecastTitle.setForeground(Color.WHITE);
        forecastTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(forecastTitle);
        
        JPanel forecastDays = new JPanel(new GridLayout(1, 3, 20, 20));
        forecastDays.setOpaque(false);
        forecastDays.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
        forecastDays.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        forecastDays.add(createForecastDay("Tomorrow", "32°C/25°C", "Partly Cloudy"));
        forecastDays.add(createForecastDay("Day After", "31°C/24°C", "Light Rain"));
        forecastDays.add(createForecastDay("Next Day", "33°C/26°C", "Sunny"));
        
        contentPanel.add(forecastDays);
        
        // Travel Plans Section - NEW
        JLabel travelTitle = new JLabel("Travel Recommendations");
        travelTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        travelTitle.setForeground(Color.WHITE);
        travelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(travelTitle);
        
        JPanel travelPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        travelPanel.setOpaque(false);
        travelPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        travelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        travelPanel.add(createTravelCard("🚗 Road Conditions", "Ideal driving conditions. No weather-related advisories in effect."));
        travelPanel.add(createTravelCard("✈️ Flight Impact", "No weather-related flight delays expected. Visibility is excellent."));
        travelPanel.add(createTravelCard("🧳 Packing Advice", "Light clothing recommended. Don't forget sunscreen and sunglasses."));
        travelPanel.add(createTravelCard("🏨 Outdoor Activities", "Perfect weather for sightseeing. UV index is moderate."));
        
        contentPanel.add(travelPanel);
        
        forecastPanel.revalidate();
        forecastPanel.repaint();
        
        // Show the forecast panel
        cardLayout.show(getContentPane(), "forecast");
    }
    
    private JPanel createDetailCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 180));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createForecastDay(String day, String temp, String desc) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 180));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel dayLabel = new JLabel(day);
        dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel tempLabel = new JLabel(temp);
        tempLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(dayLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(tempLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(descLabel);
        
        return card;
    }
    
    // NEW: Method to create travel recommendation cards
    private JPanel createTravelCard(String title, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 200));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><div style='text-align:center;'>" + description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(descLabel);
        
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ForecastXUI().setVisible(true));
    }
}

