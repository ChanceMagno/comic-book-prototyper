import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class ComicPanelTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void comicPanel_instantiatesCorrectly_true() {
    ComicPanel testComicPanel = new ComicPanel(1, 1);
    assertTrue(testComicPanel instanceof ComicPanel);
  }

  @Test
  public void getPageId_returnsPageId_int() {
    ComicPanel testComicPanel = new ComicPanel(1, 2);
    assertEquals(1, testComicPanel.getPageId());
  }

  @Test
  public void getSequence_returnsSequence_int() {
    ComicPanel testComicPanel = new ComicPanel(1, 2);
    assertEquals(2, testComicPanel.getSequence());
  }

  @Test
  public void setGetImagePath_setsAndGetsImagePath_String() {
    ComicPanel testComicPanel = new ComicPanel(1, 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    assertEquals("/img/bozo.jpg", testComicPanel.getImagePath());
  }

  @Test
  public void save_assignsIdAndSavesObjectToDatabase_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);

    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    ComicPanel savedComicPanel = ComicPanel.all().get(0);
    assertEquals(testComicPanel.getId(), savedComicPanel.getId());
  }

  @Test
  public void all_returnsAllInstancesOfComicPanel_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel firstComicPanel = new ComicPanel(testPage.getId(), 2);
    firstComicPanel.setImagePath("/img/bozo.jpg");
    firstComicPanel.save();
    ComicPanel secondComicPanel = new ComicPanel(testPage.getId(), 2);
    secondComicPanel.setImagePath("/img/kazoo.jpg");
    secondComicPanel.save();
    assertTrue(ComicPanel.all().get(0).equals(firstComicPanel));
    assertTrue(ComicPanel.all().get(1).equals(secondComicPanel));
  }

  @Test
  public void find_returnsAnInstanceOfComicPanel_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    ComicPanel savedComicPanel = ComicPanel.find(testComicPanel.getId());
    assertTrue(savedComicPanel.equals(testComicPanel));
  }

  @Test
  public void getTexts_returnsTexts_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    Text firstText = new Text(testComicPanel.getId(), 1, "speech", "caption", "comic sans");
    firstText.save();
    Text secondText = new Text(testComicPanel.getId(), 2, "thought", "caption", "console");
    secondText.save();
    assertTrue(testComicPanel.getTexts().get(0).equals(firstText));
    assertTrue(testComicPanel.getTexts().get(1).equals(secondText));
  }

  @Test
  public void deleteTexts_deletesTexts_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    Text firstText = new Text(testComicPanel.getId(), 1, "speech", "caption", "comic sans");
    firstText.save();
    Text secondText = new Text(testComicPanel.getId(), 2, "thought", "caption", "console");
    secondText.save();
    testComicPanel.deleteTexts();
    assertEquals(0, testComicPanel.getTexts().size());
  }

  @Test
  public void delete_deletesPanelAndTexts_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    Text firstText = new Text(testComicPanel.getId(), 1, "speech", "caption", "comic sans");
    firstText.save();
    Text secondText = new Text(testComicPanel.getId(), 2, "thought", "caption", "console");
    secondText.save();
    testComicPanel.delete();
    assertEquals(0, Text.all().size());
    assertEquals(0, ComicPanel.all().size());
  }

  @Test
  public void update_updates_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page firstPage = new Page(testBook.getId(), "layout1");
    firstPage.save();
    Page secondPage = new Page(testBook.getId(), "layout1");
    secondPage.save();
    ComicPanel testComicPanel = new ComicPanel(firstPage.getId(), 2);
    testComicPanel.save();
    testComicPanel.setPageId(secondPage.getId());
    testComicPanel.setSequence(1);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.update();
    ComicPanel savedComicPanel = ComicPanel.find(testComicPanel.getId());
    assertEquals(secondPage.getId(), savedComicPanel.getPageId());
    assertEquals(1, savedComicPanel.getSequence());
    assertEquals("/img/bozo.jpg", savedComicPanel.getImagePath());
  }


}
