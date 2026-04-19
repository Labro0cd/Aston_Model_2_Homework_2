import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsReader {

    public static List<Student> readStudents(String filePath) throws IOException {

        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    Student student = parseStudentLine(line);
                    students.add(student);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Ошибка в строке " + lineNumber + ": " + e.getMessage(), e);
                }

            }

        }
        return students;
    }

    public static Student parseStudentLine(String line) {
        String[] parts = line.split(":\\s*", 2);
        if (parts.length < 1) {
            throw new IllegalArgumentException(" Нет имени и фамилии");
        }

        String namePart = parts[0].trim();
        String[] nameTokens = namePart.split("\\s+");
        if (nameTokens.length < 2) {
            throw new IllegalArgumentException("Ожидается имя и фамилия, получено: " + namePart);
        }

        Student student = new Student(nameTokens[0], nameTokens[1]);

        if (parts.length == 2) {
            String booksPart = parts[1].trim();
            if (!booksPart.isEmpty()) {
                String[] bookEntries = booksPart.split(";");
                for (String entry : bookEntries) {
                    if (entry.trim().isEmpty()) continue;
                    Book book = parseBook(entry.trim());
                    student.addBook(book);
                }
            }
        }
        return student;

    }

    private static Book parseBook(String bookStr) {

        String[] tokens = bookStr.split("\\s*,\\s*");
        if (tokens.length < 4) {
            throw new IllegalArgumentException("Неверный формат книги: " + bookStr +
                    ". Ожидается: Название, Автор, Год, страницы");
        }
        String title = tokens[0].trim();
        String author = tokens[1].trim();
        int year;
        int countList;
        try {
            year = Integer.parseInt(tokens[2].trim());
            countList = Integer.parseInt(tokens[3].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Год и количество страниц должены быть числом: " + tokens[2] + ", " + tokens[3]);
        }
        return new Book(title, author, year, countList);
    }


}
