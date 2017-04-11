import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TextTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void text_instantiatesCorrectly_true() {
    Text testText = new Text(345, 1, "speech", "comic sans");
    assertTrue(testText instanceof Text);
  }

  @Test
  public void getPanelId_returnsPanelId_int() {
    Text testText = new Text(345, 1, "speech", "comic sans");
    assertEquals(345, testText.getPanelId());
  }

  @Test
  public void getSequence_returnsSequence_int() {
    Text testText = new Text(345, 1, "speech", "comic sans");
    assertEquals(1, testText.getSequence());
  }

  @Test
  public void getBoxStyle_returnsBoxStyle_String() {
    Text testText = new Text(345, 1, "speech", "comic sans");
    assertTrue(testText.getBoxStyle().equals("speech"));
  }

  @Test
  public void getFont_returnsFont_String() {
    Text testText = new Text(345, 1, "speech", "comic sans");
    assertTrue(testText.getFont().equals("comic sans"));
  }

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

}
