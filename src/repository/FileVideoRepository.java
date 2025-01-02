package repository;

import model.Video;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileVideoRepository implements VideoRepository {
    private static final Logger LOGGER = Logger.getLogger(FileVideoRepository.class.getName());
    private final File file;

    public FileVideoRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    @Override
    public void save(Video video) {
        if (video == null) {
            LOGGER.warning("Attempted to save a null video.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(video.toString());
            bw.newLine();
        } catch (IOException e) {
            handleIOException("Error saving video to file", e);
        }
    }

    @Override
    public List<Video> findAll() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Video video = Video.fromString(line);
                if (video != null) {
                    videos.add(video);
                } else {
                    LOGGER.warning("Skipped invalid video entry: " + line);
                }
            }
        } catch (IOException e) {
            handleIOException("Error reading videos from file", e);
        }
        return videos;
    }

    private void handleIOException(String message, IOException e) {
        LOGGER.log(Level.SEVERE, message, e);
    }

    private void ensureFileExists() {
        try {
            if (!file.exists()) {
                Files.createFile(file.toPath());
                LOGGER.info("File created: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            handleIOException("Error creating file: " + file.getAbsolutePath(), e);
        }
    }
}