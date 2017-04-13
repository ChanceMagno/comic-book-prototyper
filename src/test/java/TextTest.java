import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TextTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void text_instantiatesCorrectly_true() {
    Text testText = new Text(345, 1, "speech", "center");
    assertTrue(testText instanceof Text);
  }

  @Test
  public void getPanelId_returnsPanelId_int() {
    Text testText = new Text(345, 1, "speech", "center");
    assertEquals(345, testText.getPanelId());
  }

  @Test
  public void getSequence_returnsSequence_int() {
    Text testText = new Text(345, 1, "speech", "center");
    assertEquals(1, testText.getSequence());
  }

  @Test
  public void getOrientation_returnsOrientation_String() {
    Text testText = new Text(345, 1, "speech", "center");
    assertTrue(testText.getOrientation().equals("center"));
  }

  // @Test
  // public void getFont_returnsFont_String() {
  //   Text testText = new Text(345, 1, "speech", "center");
  //   assertTrue(testText.getFont().equals("comic sans"));
  // }

  // @Test
  // public void save_assignsIdAndSavesObjectToDatabase_true() {
  //   Book testBook = new Book("Jefferson Conflict", 1);
  //   testBook.save();
  //   Page testPage = new Page(testBook.getId(), "layout1");
  //   testPage.save();
  //   ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
  //   testComicPanel.setImagePath("/img/bozo.jpg");
  //   testComicPanel.save();
  //   Text testText = new Text(testComicPanel.getId(), 1, "speech", "comic sans");
  //   testText.save();
  //   Text savedText = Text.all().get(0);
  //   assertEquals(testText.getId(), savedText.getId());
  // }

  @Test
  public void all_returnsAllInstancesOfText_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    Text firstText = new Text(testComicPanel.getId(), 1, "speech", "center");
    firstText.save();
    Text secondText = new Text(testComicPanel.getId(), 2, "thought", "topright");
    secondText.save();
    assertTrue(Text.all().get(0).equals(firstText));
    assertTrue(Text.all().get(1).equals(secondText));
  }

  @Test
  public void find_returnsAnInstanceOfText_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel testComicPanel = new ComicPanel(testPage.getId(), 2);
    testComicPanel.setImagePath("/img/bozo.jpg");
    testComicPanel.save();
    Text testText = new Text(testComicPanel.getId(), 1, "speech", "center");
    testText.save();
    Text savedText = Text.find(testText.getId());
    assertTrue(savedText.equals(testText));
  }

  @Test
  public void update_updates_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel firstComicPanel = new ComicPanel(testPage.getId(), 1);
    firstComicPanel.setImagePath("/img/bozo.jpg");
    firstComicPanel.save();
    ComicPanel secondComicPanel = new ComicPanel(testPage.getId(), 2);
    secondComicPanel.setImagePath("/img/kazoo.jpg");
    secondComicPanel.save();
    Text testText = new Text(firstComicPanel.getId(), 1, "speech", "center");
    testText.save();
    testText.setSequence(2);
    testText.setPanelId(secondComicPanel.getId());
    testText.setOrientation("topleft");
    testText.update();
    Text savedText = Text.find(testText.getId());
    assertEquals(secondComicPanel.getId(), savedText.getPanelId());
    assertEquals(2, savedText.getSequence());
    assertTrue(savedText.getOrientation().equals("topleft"));
  }

  @Test
  public void delete_deletes_true() {
    Book testBook = new Book("Jefferson Conflict", 1);
    testBook.save();
    Page testPage = new Page(testBook.getId(), "layout1");
    testPage.save();
    ComicPanel firstComicPanel = new ComicPanel(testPage.getId(), 1);
    firstComicPanel.setImagePath("/img/bozo.jpg");
    firstComicPanel.save();
    ComicPanel secondComicPanel = new ComicPanel(testPage.getId(), 2);
    secondComicPanel.setImagePath("/img/kazoo.jpg");
    secondComicPanel.save();
    Text testText = new Text(firstComicPanel.getId(), 1, "speech", "center");
    testText.save();
    assertEquals(1, Text.all().size());
    testText.delete();
    assertEquals(0, Text.all().size());
  }

}
