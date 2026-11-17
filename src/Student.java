/**
 * Represents one student. Fields are private (encapsulation) and
 * accessed through getters and setters.
 */
public class Student {
    private final int rollNo;
    private String name;
    private String course;
    private double marks; // percentage, 0 to 100

    public Student(int rollNo, String name, String course, double marks) {
        this.rollNo = rollNo;
        setName(name);
        setCourse(course);
        setMarks(marks);
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setCourse(String course) {
        if (course == null || course.isBlank()) {
            throw new IllegalArgumentException("Course cannot be empty.");
        }
        this.course = course.trim();
    }

    public void setMarks(double marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
        this.marks = marks;
    }

    /** Grade based on percentage. */
    public String getGrade() {
        if (marks >= 75) return "A";
        if (marks >= 60) return "B";
        if (marks >= 40) return "C";
        return "F";
    }

    /** One line in the data file: rollNo,name,course,marks */
    public String toCsv() {
        return rollNo + "," + name.replace(",", " ") + "," + course.replace(",", " ") + "," + marks;
    }

    public static Student fromCsv(String line) {
        String[] p = line.split(",");
        if (p.length != 4) {
            throw new IllegalArgumentException("Bad record: " + line);
        }
        return new Student(Integer.parseInt(p[0].trim()), p[1], p[2], Double.parseDouble(p[3].trim()));
    }

    @Override
    public String toString() {
        return String.format("%-6d %-20s %-12s %6.2f  %s", rollNo, name, course, marks, getGrade());
    }
}
