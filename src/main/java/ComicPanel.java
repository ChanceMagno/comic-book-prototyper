import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class ComicPanel {
  private int id;
  private int page_id;
  private int sequence;
  private String dialog1;
  private String dialog2;
  private String narration;
  private String image_path;

  public ComicPanel(int page_id, int sequence) {
    this.page_id = page_id;
    this.sequence = sequence;
  }

  public int getPageId() {
    return page_id;
  }

  public int getSequence() {
    return sequence;
  }

  public String getImagePath() {
    return image_path;
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

  public int getId() {
    return id;
  }



}
