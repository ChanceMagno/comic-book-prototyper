import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class BookTest {

@Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void BookInstantiatesCorrectly_true() {
    Book myBook = new Book("Comic One", 1);
    assertEquals(true, myBook instanceof Book);
  }

  @Test
  public void getBookTitle_returnsBookTitle_string() {
    Book myBook = new Book("Comic One", 1);
    assertEquals("Comic One", myBook.getTitle());
  }

  @Test
   public void getId_bookInstantiatesWithAnId_1() {
     Book myBook = new Book("Comic One", 1);
     myBook.save();
     assertTrue(myBook.getId() > 0);
   }

   @Test
  public void equals_returnsTrueIfTitleIsTheSame_true() {
    Book firstBook = new Book("Comic One", 1);
    Book anotherBook = new Book("Comic One", 1);
    assertTrue(firstBook.equals(anotherBook));
  }

  @Test
 public void save_assignsIdToObjectAndSavesObjectToDatabase() {
   Book testBook = new Book("Comic One", 1);
   testBook.save();
   Book savedBook = Book.all().get(0);
   assertEquals(testBook.getId(), savedBook.getId());
 }

 @Test
 public void all_returnsAllInstancesOfBook_true() {
   Book firstBook = new Book("Comic One", 1);
   firstBook.save();
   Book secondBook = new Book("Comic Two", 1);
   secondBook.save();
   assertEquals(true, Book.all().get(0).equals(firstBook));
   assertEquals(true, Book.all().get(1).equals(secondBook));
 }

 @Test
 public void find_returnsBookWithSameId_secondBook() {
   Book firstBook = new Book("Comic One", 1);
   firstBook.save();
   Book secondBook = new Book("Comic Two", 1);
   secondBook.save();
   assertEquals(Book.find(secondBook.getId()), secondBook);
 }

 @Test
 public void updateTitle_updatesBookTitleInDatabase_String() {
   Book testBook = new Book("Comic One", 1);
   testBook.save();
   testBook.update("Comic Three");
   assertEquals("Comic Three", Book.find(testBook.getId()).getTitle());
 }

 @Test
 public void delete_deletesBookFromDatabase_0() {
   Book testBook = new Book("Comic One", 1);
   testBook.save();
   testBook.delete();
   assertEquals(0, Book.all().size());
 }

 @Test
 public void find_returnsNullWhenNoBookFound_null() {
   assertTrue(Book.find(999) == null);
 }
}
