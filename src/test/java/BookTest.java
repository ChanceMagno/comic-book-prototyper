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
  public void getBookName_returnsBookName_string() {
    assertEquals("Comic One", myBook.getTitle());
  }

  @Test
   public void getId_bookInstantiateWithAnId_1() {
     Book myBook = new Book("Comic One", 1);
     myBook.save();
     assertTrue(myBook.getId() > 0);
   }
 }
