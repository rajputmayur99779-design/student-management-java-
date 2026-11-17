import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves and loads students from a CSV text file so data is not lost
 * when the program closes.
 */
public class FileStorage {
    private final Path path;

    public FileStorage(String fileName) {
        this.path = Paths.get(fileName);
    }

    public List<Student> load() {
        List<Student> students = new ArrayList<>();
        if (!Files.exists(path)) {
            return students; // first run, nothing saved yet
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                try {
                    students.add(Student.fromCsv(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipped a bad line in file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
        return students;
    }

    public void save(List<Student> students) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Student s : students) {
                writer.write(s.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save file: " + e.getMessage());
        }
    }
}
