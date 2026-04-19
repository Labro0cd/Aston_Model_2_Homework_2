import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        try {
            List<Student> list = StudentsReader.readStudents("/Users/kirillkrivobokov/Downloads/Aston_Model_2_Homework_2/src/StudentsList");

            // Вывести в консоль каждого студента (переопределите toString)
            list.forEach(e -> System.out.print(e.getFirstName() + " " + e.getLastName() + ", "));

            // Получить для каждого студента список книг
            list.stream().peek(Student::getBooks).forEach(System.out::println);

            //Получить книги
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .collect(Collectors.toList());

            //Отсортировать книги по количеству страниц (Не забывайте про условия для сравнения объектов)
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .sorted(Comparator.comparingInt(Book::getPages))
                    .collect(Collectors.toList());

            //Оставить только уникальные книги
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .distinct()
                    .collect(Collectors.toList());

            //Отфильтровать книги, оставив только те, которые были выпущены после 2000 года
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .filter(b -> b.getYear() > 2000)
                    .collect(Collectors.toList());

            //Ограничить стрим на 3 элементах
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .filter(b -> b.getYear() > 2000)
                    .limit(3)
                    .collect(Collectors.toList());

            //Получить из книг годы выпуска
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .map(Book::getYear)
                    .collect(Collectors.toList());

            //При помощи методов короткого замыкания вернуть Optional от года
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .map(Book::getYear)
                    .limit(2);

            //При помощи методов получения значения из Optional вывести в консоль год выпуска найденной книги, либо запись о том, что такая книга отсутствует
            list.stream()
                    .flatMap(s -> s.getBooks().stream())
                    .map(Book::getYear)
                    .filter(year -> year > 2000)
                    .findFirst()
                    .map(year -> "Год выпуска: " + year)
                    .orElse("Книга, удовлетворяющая условию, отсутствует");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}