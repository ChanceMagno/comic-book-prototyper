// import org.sql2o.*;
// import org.junit.*;
// import static org.junit.Assert.*;
// import java.util.Arrays;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.Timer;
// import java.util.TimerTask;
// import java.sql.Timestamp;
//
// public class ComicPanelTest {
//
//   @Rule
//   public DatabaseRule database = new DatabaseRule();
//
//   @Test
//   public void comicPanel_instantiatesCorrectly_true() {
//     ComicPanel testComicPanel = new ComicPanel(1, 1);
//     assertTrue(testComicPanel instanceof ComicPanel);
//   }
//
//   @Test
//   public void getPageId_returnsPageId_int() {
//     ComicPanel testComicPanel = new ComicPanel(1, 2);
//     assertEquals(1, testComicPanel.getPageId());
//   }
//
//   @Test
//   public void getSequence_returnsSequence_int() {
//     ComicPanel testComicPanel = new ComicPanel(1, 2);
//     assertEquals(2, testComicPanel.getSequence());
//   }
//
//   @Test
//   public void setGetImagePath_setsAndGetsImagePath_String() {
//     ComicPanel testComicPanel = new ComicPanel(1, 2);
//     testComicPanel.setImagePath("/img/bozo.jpg");
//     assertEquals("/img/bozo.jpg", testComicPanel.getImagePath());
//   }
//
//   @Test
//   public void save_assignsIdAndSavesObjectToDatabase_true() {
//     Book testBook = new Book("Jefferson Conflict");
//     Page testPage = new Page();
//     ComicPanel testComicPanel = new ComicPanel(1, 2);
//     testComicPanel.setImagePath("/img/bozo.jpg");
//     testComicPanel.save();
//     ComicPanel savedComicPanel = ComicPanel.all().get(0);
//     assertEquals(testComicPanel.getId(), savedComicPanel.getId());
//   }
//
// }
