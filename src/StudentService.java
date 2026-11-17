import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic: add, find, update, delete and report on students.
 * Does not print or read input, so it is easy to test.
 */
public class StudentService {
    private final List<Student> students;
    private final FileStorage storage;

    public StudentService(FileStorage storage) {
        this.storage = storage;
        this.students = new ArrayList<>(storage.load());
    }

    public void add(Student student) {
        if (findByRollNo(student.getRollNo()).isPresent()) {
            throw new IllegalArgumentException("Roll number " + student.getRollNo() + " already exists.");
        }
        students.add(student);
        storage.save(students);
    }

    public Optional<Student> findByRollNo(int rollNo) {
        return students.stream().filter(s -> s.getRollNo() == rollNo).findFirst();
    }

    public List<Student> searchByName(String text) {
        String lower = text.toLowerCase();
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Student> getAllSortedByRollNo() {
        return students.stream()
                .sorted(Comparator.comparingInt(Student::getRollNo))
                .collect(Collectors.toList());
    }

    public List<Student> getRankList() {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getMarks).reversed())
                .collect(Collectors.toList());
    }

    public boolean update(int rollNo, String name, String course, double marks) {
        Optional<Student> found = findByRollNo(rollNo);
        if (found.isEmpty()) return false;
        Student s = found.get();
        s.setName(name);
        s.setCourse(course);
        s.setMarks(marks);
        storage.save(students);
        return true;
    }

    public boolean delete(int rollNo) {
        boolean removed = students.removeIf(s -> s.getRollNo() == rollNo);
        if (removed) storage.save(students);
        return removed;
    }

    public double averageMarks() {
        return students.stream().mapToDouble(Student::getMarks).average().orElse(0);
    }

    public int count() {
        return students.size();
    }
}
