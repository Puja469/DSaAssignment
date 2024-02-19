
package org.example.Six;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

// Creating a class for the Image Downloader application, extending JFrame
public class ImageDownloaderApp extends JFrame {
    // Declaring GUI components
    private JTextField urlField = new JTextField("");
    private JButton addButton = new JButton("Add Download");
    private DefaultListModel<DownloadInfo> listModel = new DefaultListModel<>();
    private JList<DownloadInfo> downloadList = new JList<>(listModel);
    private ExecutorService downloadExecutor = Executors.newFixedThreadPool(10); // 10 concurrent downloads


    // Constructor for the ImageDownloaderApp class
    public ImageDownloaderApp() {
        // Set dark theme
        super(" Image Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 400);
        layoutComponents();
        setVisible(true);
        setLocationRelativeTo(null); // Center the JFrame on the screen
    }

    // Method to layout GUI components
    private void layoutComponents() {
        // Set dark theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set background color to black and font to Roboto 14 for all components
        Font roboto14 = new Font("Roboto", Font.PLAIN, 16);
        Color whiteColor = Color.WHITE;
        Color pinkColor = Color.PINK;
        Color blackColor =Color.BLACK;
        Color greenColor = Color.GREEN;
        Color redColor = Color.RED;

        getContentPane().setBackground(whiteColor);
        urlField.setFont(roboto14);
        urlField.setForeground(blackColor);
        addButton.setFont(roboto14);
        addButton.setBackground(blackColor);
        addButton.setForeground(blackColor);

        JLabel titleLabel = new JLabel(" Image Downloader");
    titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
    titleLabel.setForeground(blackColor);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // Create and set properties for various GUI components
        JPanel addPanel = new JPanel();
        addPanel.setBackground(redColor);
        addPanel.setLayout(new BorderLayout());
        addPanel.add(urlField, BorderLayout.CENTER);
        addPanel.add(addButton, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(downloadList);
        downloadList.setCellRenderer(new DownloadImages());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(pinkColor);

        JButton pauseResumeButton = new JButton("Pause/Resume");
        pauseResumeButton.setFont(roboto14);
        pauseResumeButton.setBackground(redColor);
        pauseResumeButton.setForeground(pinkColor);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(roboto14);
        cancelButton.setBackground(greenColor);
        cancelButton.setForeground(redColor);

        JButton showDownloadsButton = new JButton("Downloads");
        showDownloadsButton.setFont(roboto14);
        showDownloadsButton.setBackground(whiteColor);
        showDownloadsButton.setForeground(greenColor);

        buttonPanel.add(pauseResumeButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(showDownloadsButton);

        addButton.addActionListener(e -> addDownload(urlField.getText().trim()));

        pauseResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DownloadInfo selectedInfo = downloadList.getSelectedValue();
                if (selectedInfo != null) {
                    selectedInfo.togglePauseResume();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DownloadInfo selectedInfo = downloadList.getSelectedValue();
                if (selectedInfo != null) {
                    selectedInfo.cancel();
                    listModel.removeElement(selectedInfo);
                }
            }
        });

        showDownloadsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("downloads"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
// Set layout and add components to the JFrame
        setLayout(new BorderLayout());
        

        add(addPanel, BorderLayout.SOUTH);
        add(titleLabel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        
    }

    // Method to add a download to the list
    private void addDownload(String url) {
        try {
            new URL(url);
            DownloadInfo info = new DownloadInfo(url);
            listModel.addElement(info);
            Download task = new Download(info, () -> SwingUtilities.invokeLater(this::repaint));
            info.setFuture(downloadExecutor.submit(task));
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(this, "Invalid URL: " + url, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageDownloaderApp::new);
    }
}
// Class to represent information about a download
class DownloadInfo {
    private final String url;
    private volatile String status = "Waiting..."; // Corrected the typo here
    private volatile long totalBytes = 0L;
    private volatile long downloadedBytes = 0L;
    private Future<?> future;
    private final AtomicBoolean paused = new AtomicBoolean(false);

    // Constructor
    public DownloadInfo(String url) {
        this.url = url;
    }

    // Getter for URL
    public String getUrl() {
        return url;
    }


    // Synchronized method to check if the download is
    public synchronized boolean isPaused() {
        return paused.get();
    }

    // Synchronized method to toggle pause/resume
    public synchronized void togglePauseResume() {
        paused.set(!paused.get());
        notifyAll();
    }
    // Getter for download status
    public String getStatus() {
        return status;
    }


    // Synchronized method to set download status
    public synchronized void setStatus(String status) {
        this.status = status;
    }


    // Method to set the future for the download
    public void setFuture(Future<?> future) {
        this.future = future;
    }



    // Method to cancel the download
    public void cancel() {
        if (future != null)
            future.cancel(true);
    }

    // Synchronized method to set total bytes
    public synchronized void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    // Synchronized method to add downloaded bytes
    public synchronized void addDownloadedBytes(long bytes) {
        this.downloadedBytes += bytes;
    }


    // Getter for downloaded bytes
    public long getDownloadedBytes() {
        return downloadedBytes;
    }

    // Getter for total bytes
    public long getTotalBytes() {
        return totalBytes;
    }
}

// Class representing a download task
class Download implements Callable<Void> {
    private final DownloadInfo info;
    private final Runnable updateUI;

    public Download(DownloadInfo info, Runnable updateUI) {
        this.info = info;
        this.updateUI = updateUI;
    }




    // Call method to execute the download task
    @Override

    public Void call() throws Exception {
        // Set download status
        info.setStatus("Downloading");
        @SuppressWarnings("deprecation")
        // Create URL connection
        URL url = new URL(info.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        long fileSize = connection.getContentLengthLong();
        info.setTotalBytes(fileSize);

        try (InputStream in = new BufferedInputStream(connection.getInputStream())) {
            Path targetPath = Paths.get("downloads", new File(url.getPath()).getName());
            Files.createDirectories(targetPath.getParent());
            try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(targetPath))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    synchronized (info) {
                        while (info.isPaused())
                            info.wait();
                    }
                    out.write(buffer, 0, bytesRead);
                    info.addDownloadedBytes(bytesRead);
                    updateUI.run();
                    Thread.sleep(200);
                }
                info.setStatus("Completed");
            }
        } catch (IOException | InterruptedException e) {
            info.setStatus("Error: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        } finally {
            updateUI.run();
        }
        return null;
    }
}

class DownloadImages extends JPanel implements ListCellRenderer<DownloadInfo> {
    @Override
    public Component getListCellRendererComponent(JList<? extends DownloadInfo> list, DownloadInfo value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        this.removeAll();
        setLayout(new BorderLayout());
        JLabel urlLabel = new JLabel(value.getUrl());
        JProgressBar progressBar = new JProgressBar(0, 100);
        if (value.getTotalBytes() > 0) {
            int progress = (int) ((value.getDownloadedBytes() * 100) / value.getTotalBytes());
            progressBar.setValue(progress);
        }
        JLabel statusLabel = new JLabel(value.getStatus());
        add(urlLabel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}
