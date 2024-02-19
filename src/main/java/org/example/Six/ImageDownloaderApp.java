package org.example.Six;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloaderApp extends JFrame {

    private ExecutorService executorService; // Thread pool for managing downloads
    private JPanel downloadPanel;

    private JButton btnStartDownload;
    private JTextField txtImageURL;
    private JButton btnCancelReset;
    private JLabel lblTitle;
    private JLabel lblStatus;


    // Constructor to initialize components
    public ImageDownloaderApp() {
        initComponents();
        executorService = Executors.newCachedThreadPool(); // Initializing thread pool
        setSize(800, 600); // Set initial size of the window

    }

    // Initializing GUI components
    private void initComponents() {
        // Initializing buttons, text fields, and labels
        btnStartDownload = new JButton("Start Download");
        txtImageURL = new JTextField("https://wallpapercave.com/wp/wp13553744.jpg");
        txtImageURL.setVisible(true); // Make the text field visible
        txtImageURL.setEditable(true); // Make the text field editable
        btnCancelReset = new JButton("Cancel All");
        lblTitle = new JLabel("Multithreaded Asynchronous Image Downloader ", JLabel.CENTER);
        lblStatus = new JLabel("Downloads:", JLabel.CENTER);

        btnStartDownload.addActionListener(this::btnStartDownloadActionPerformed);
        btnCancelReset.addActionListener(this::btnCancelResetActionPerformed);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(lblTitle, BorderLayout.NORTH);
// Creating main and control panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());

        controlPanel.add(btnStartDownload);
        controlPanel.add(txtImageURL); // Add the text field to the control panel

        controlPanel.add(btnCancelReset);
        mainPanel.add(controlPanel, BorderLayout.NORTH);

// Creating download panel and add it to a scroll pane
        downloadPanel = new JPanel();
        downloadPanel.setLayout(new BoxLayout(downloadPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(downloadPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        add(lblStatus, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }


    // Validating the image URL
    private boolean isValidImageUrl(String url) {
        // Define a list of valid image extensions
        String[] validExtensions = {".jpg", ".jpeg", ".png", ".tif", ".svg", ".webp", ".tiff", ".bmp"};

        // Check if the URL ends with a valid image extension
        for (String extension : validExtensions) {
            if (url.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    // Action performed when the "Start Download" button is clicked
    private void btnStartDownloadActionPerformed(ActionEvent evt) {
        String imageUrl = txtImageURL.getText();

        // Checking if the URL is valid
        if (!isValidImageUrl(imageUrl)) {
            JOptionPane.showMessageDialog(this, "Enter Valid Image URL", "Invalid URL", JOptionPane.ERROR_MESSAGE);
            return;
        }


// Creating a download entry and add it to the panel
        DownloadEntry downloadEntry = new DownloadEntry(imageUrl);
        downloadPanel.add(downloadEntry);
        downloadPanel.revalidate();
        downloadPanel.repaint();

        // Creating a download task and submit it to the thread pool
        DownloadTask downloadTask = new DownloadTask(imageUrl, downloadEntry);
        executorService.submit(downloadTask);
    }


    // Action performed when the "Cancel All" button is clicked
    private void btnCancelResetActionPerformed(ActionEvent evt) {
        // Cancel all downloads
        Component[] components = downloadPanel.getComponents();
        for (Component component : components) {
            if (component instanceof DownloadEntry) {
                DownloadEntry downloadEntry = (DownloadEntry) component;
                downloadEntry.cancelDownload();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageDownloaderApp form = new ImageDownloaderApp();
            form.setVisible(true);
        });
    }


    // Shutdown the thread pool when the application is closed
    @Override
    public void dispose() {
        super.dispose();
        executorService.shutdown();
    }
}

// Class representing a download entry in the UI
class DownloadEntry extends JPanel {

    private String imageUrl;
    private JLabel lblUrl;
    private JProgressBar progressBar;
    private JButton btnPause;
    private JButton btnCancel;
    private volatile boolean isPaused;
    private volatile boolean isCancelled;

    public DownloadEntry(String imageUrl) {
        this.imageUrl = imageUrl;
        initComponents();
    }


    // Initializing GUI components
    private void initComponents() {
        setLayout(new BorderLayout());
        lblUrl = new JLabel(imageUrl);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        btnPause = new JButton("Pause");
        btnCancel = new JButton("Cancel");

        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownload();
            }
        });
        // Create button panel and add buttons to it
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnPause);
        buttonPanel.add(btnCancel);

        // Add components to the panel
        add(lblUrl, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Set the progress of the download
    public void setProgress(int progress) {
        progressBar.setValue(progress);
        if (progress == 100) {
            removeEntry();
        }
    }

    // Toggle the pause/resume state of the download
    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            btnPause.setText("Resume");
        } else {
            btnPause.setText("Pause");
        }
    }

    // Cancel the download
    public void cancelDownload() {
        isCancelled = true;
    }


    // Checking if the download is paused
    public boolean isPaused() {
        return isPaused;
    }

    // Checking if the download is cancelled
    public boolean isCancelled() {
        return isCancelled;
    }

    // Getting the image URL associated with the download
    public String getImageUrl() {
        return imageUrl;
    }


    // Removing the download entry from the UI
    private void removeEntry() {
        SwingUtilities.invokeLater(() -> {
            Container parent = getParent();
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        });
    }
}

// Class representing a download task to be executed in a separate thread
class DownloadTask implements Runnable {

    private static final int TOTAL_BYTES = 1000; // Dummy total bytes for download
    private static final int DOWNLOAD_INCREMENT = 10; // Increment in downloaded bytes

    private String imageUrl;
    private DownloadEntry downloadEntry;

    public DownloadTask(String imageUrl, DownloadEntry downloadEntry) {
        this.imageUrl = imageUrl;
        this.downloadEntry = downloadEntry;
    }

    @Override
    public void run() {
        int downloadedBytes = 0;
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            // Save the image to a file
            saveImage(image);

            // Simulate progress updates
            while (downloadedBytes < TOTAL_BYTES) {
                if (downloadEntry.isCancelled()) {
                    downloadEntry.setProgress(0);
                    return;
                }
                if (!downloadEntry.isPaused()) {
                    int progress = (int) ((double) downloadedBytes / TOTAL_BYTES * 100);
                    SwingUtilities.invokeLater(() -> downloadEntry.setProgress(progress));
                    Thread.sleep(100); // Simulating download delay
                    downloadedBytes += DOWNLOAD_INCREMENT; // Increment downloaded bytes
                }
            }

            // Set progress to 100% when download is complete
            SwingUtilities.invokeLater(() -> downloadEntry.setProgress(100));
        } catch (IOException | InterruptedException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    private void saveImage(BufferedImage image) throws IOException {
        // Create a unique filename
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";

        // Specify the directory to save the images
        Path directory = Paths.get("download_images");
        Files.createDirectories(directory); // Create the directory if it doesn't exist

        // Specify the full path for the image file
        Path destination = directory.resolve(fileName);

        // Save the image to the specified directory
        ImageIO.write(image, "jpg", destination.toFile());
    }
}