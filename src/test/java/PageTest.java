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
    Book myBook = new Book("Comic One", 1);
    myBook.save();
    Page page = new Page(myBook.getId(), "layout1");
    assertTrue(page instanceof Page);
  }

  @Test
  public void page_instantiatesWithAllMembers_true() {
    Book myBook = new Book("Comic One", 1);
    myBook.save();
    Page page = new Page(myBook.getId(), "layout1");
    assertEquals(myBook.getId(), page.getbookId());
    assertEquals("layout1", page.getLayout());
  }

  @Test
  public void save_savesPageObject_true() {
    Book myBook = new Book("Comic One", 1);
    myBook.save();
    Book myBook1 = new Book("Comic One", 1);
    myBook1.save();
    Page page = new Page(myBook.getId(), "layout1");
    page.save();
    Page page1 = new Page(myBook1.getId(), "layout2");
    page1.save();
    assertEquals(page, Page.all().get(0));
    assertEquals(page1, Page.all().get(1));
  }

  @Test
  public void find_returnsClassByID_true () {
    Book myBook = new Book("comic one", 1);
    myBook.save();
    Page page = new Page(myBook.getId(), "layout1");
    page.save();
    assertEquals(page, Page.find(page.getId()));
  }

  @Test
  public void updatePageLayout_returnsUpdatedLayout_true() {
    Book myBook = new Book("comic one", 1);
    myBook.save();
    Page page1 = new Page(myBook.getId(), "layout1");
    page1.save();
    page1.updatePageLayout("layout3", page1.getId());
    assertEquals("layout3", Page.find(page1.getId()).getLayout());
  }

  @Test
  public void delete_deletesPage_true() {
    Book myBook = new Book("comic one", 1);
    myBook.save();
    Page page1 = new Page(myBook.getId(), "layout1");
    page1.save();
    page1.delete();
    assertEquals(null, Page.find(page1.getId()));
  }

  @Test
  public void getPanels_returnsAllPanelsBasedOnPageID_true() {
    Book myBook = new Book("comic one", 1);
    myBook.save();
    Page page1 = new Page(myBook.getId(), "layout1");
    page1.save();
    ComicPanel panel = new ComicPanel(page1.getId(), 1);
    assertEquals(panel, page1.getPanels(page1.getId()).get(0));
  }



}
