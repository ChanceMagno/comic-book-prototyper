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

public class PageTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void page_classinstatiates_true() {
    Book myBook = new Book("Comic One");
    myBook.save();
    Page page = new Page(myBook.getId(), "layout1");
    assertTrue(page instanceof Page);
  }

  @Test
  public void page_instantiatesWithAllMembers_true() {
    Book myBook = new Book("Comic One");
    myBook.save();
    Page page = new Page(myBook.getId(), "layout1");
    assertEquals(1, page.getbookId());
    assertEquals("layout1", page.getLayout());
  }

  @Test
  public void save_savesPageObject_true() {
    Book myBook = new Book("Comic One");
    myBook.save();
    Book myBook1 = new Book("Comic One");
    myBook.save();
    Page page = new Page(myBook.getId(), "layout1");
    page.save();
    Page page1 = new Page(myBook1.getId(), "layout2");
    page1.save();
    assertEquals(page, Page.all().get(0));
    assertEquals(page1, Page.all().get(1));
  }



}
