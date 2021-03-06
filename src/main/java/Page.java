import org.sql2o.*;
import java.util.List;

public class Page {
private int id;
private int book_id;
private String layout;
private int sequence;

  public Page(int book_id, String layout, int sequence) {
    this.book_id = book_id;
    this.layout = layout;
    this.sequence = sequence;
  }

  public int getBookId() {
    return book_id;
  }

  public String getLayout() {
    return layout;
  }

  public int getId() {
    return id;
  }

  public int getSequence(){
    return sequence;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO pages (book_id, layout, sequence) VALUES (:book_id, :layout, :sequence);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("book_id", this.book_id)
      .addParameter("layout", this.layout)
      .addParameter("sequence", this.sequence)      .executeUpdate()
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
      this.getBookId() == newPage.getBookId() && this.getLayout().equals(newPage.getLayout()) &&
      this.getSequence() == newPage.getSequence();
    }
}

  public static Page find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM pages WHERE id = :id;";
      Page page = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Page.class);
      return page;
    }
  }

  public void updatePageLayout(String newLayout, int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE pages SET layout = :layout WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("layout", newLayout)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(int book_id, String layout) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE pages SET book_id = :book_id, layout = :layout WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("book_id", book_id)
      .addParameter("layout", layout)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    deletePanels();
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM pages WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void deletePanels() {
    List<ComicPanel> panels = getPanels();
    for (ComicPanel panel : panels) {
      panel.delete();
    }
  }

  public List<ComicPanel> getPanels() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM panels WHERE page_id = :page_id ORDER BY sequence;";
      return con.createQuery(sql)
        .addParameter("page_id", this.id)
        .executeAndFetch(ComicPanel.class);
    }
  }
}
