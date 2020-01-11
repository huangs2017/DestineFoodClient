package hunter;

import hunter.Student;
import hunter.Book;

interface BinderTest {
    int callAdd(int a, int b);
    List<Book> getBooks(in Student student);
    void getStudent(out Student student);
    void getStudentWithInOutTag(inout Student student);
}