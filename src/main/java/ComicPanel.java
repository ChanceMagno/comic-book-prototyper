import org.sql2o.*;
import java.util.List;

public class ComicPanel {
  private int id;
  private int page_id;
  private int sequence;
  private String image_path;

  public ComicPanel(int page_id, int sequence) {
    this.page_id = page_id;
    this.sequence = sequence;
  }

  public int getId() {
    return id;
  }
  public int getPageId() {
    return page_id;
  }

  public void setPageId(int page_id) {
    this.page_id = page_id;
  }

  public int getSequence() {
    return sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  public String getImagePath() {
    return image_path;
  }

  public void setImagePath(String image_path) {
    this.image_path = image_path;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO panels (page_id, sequence, image_path) VALUES (:page_id, :sequence, :image_path);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("page_id", this.page_id)
        .addParameter("sequence", this.sequence)
        .addParameter("image_path", this.image_path)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE panels SET page_id = :page_id, sequence = :sequence, image_path = :image_path WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("page_id", this.page_id)
        .addParameter("sequence", this.sequence)
        .addParameter("image_path", this.image_path)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Text> getTexts() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM texts WHERE panel_id = :panel_id ORDER by sequence;";
      return con.createQuery(sql)
        .addParameter("panel_id", this.id)
        .executeAndFetch(Text.class);
    }
  }

  public void deleteTexts() {
    List<Text> texts = getTexts();
    for (Text text : texts) {
      text.delete();
    }
  }

  public void delete() {
    deleteTexts();
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM panels WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherComicPanel) {
    if(!(otherComicPanel instanceof ComicPanel)) {
      return false;
    } else {
      ComicPanel newComicPanel = (ComicPanel) otherComicPanel;
      return this.getPageId() == newComicPanel.getPageId() && this.getSequence() == newComicPanel.getSequence() && this.getImagePath().equals(newComicPanel.getImagePath());
    }
  }

  public static List<ComicPanel> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM panels;";
      return con.createQuery(sql)
        .executeAndFetch(ComicPanel.class);
    }
  }

  public static ComicPanel find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM panels WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(ComicPanel.class);
    }
  }
}
