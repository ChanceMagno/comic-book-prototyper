import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Page {
private int id;
private int book_id;
private String layout;


  public Page(int book_id, String layout) {
    this.book_id = book_id;
    this.layout = layout;
  }

  public int getbookId() {
    return book_id;
  }

  public String getLayout() {
    return layout;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO pages (book_id, layout) VALUES (:book_id, :layout);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("book_id", this.book_id)
      .addParameter("layout", this.layout)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Page> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM pages;";
      return con.createQuery(sql).executeAndFetch(Page.class);
    }
  }

  @Override
  public boolean equals(Object otherPage){
    if(!(otherPage instanceof Page)) {
      return false;
    } else {
      Page newPage = (Page) otherPage;
      return
      this.getbookId() == newPage.getbookId() && this.getLayout().equals(newPage.getLayout());
    }
}

}
