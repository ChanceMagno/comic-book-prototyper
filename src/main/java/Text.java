import org.sql2o.*;
import java.util.List;

public class Text {
  private int id;
  private int panel_id;
  private int sequence;
  private String box_style;
  private String font;
  private String orientation;
  private String speaker;

  public Text(int panel_id, int sequence, String box_style, String font) {
    this.panel_id = panel_id;
    this.sequence = sequence;
    this.box_style = box_style;
    this.font = font;
  }

  public int getId() {
    return id;
  }

  public int getPanelId() {
    return panel_id;
  }

  public int getSequence() {
    return sequence;
  }

  public String getBoxStyle() {
    return box_style;
  }

  public String getFont() {
    return font;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO texts (panel_id, sequence, box_style, font) VALUES (:panel_id, :sequence, :box_style, :font);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("panel_id", this.panel_id)
        .addParameter("sequence", this.sequence)
        .addParameter("box_style", this.box_style)
        .addParameter("font", this.font)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Text> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM texts;";
      return con.createQuery(sql)
        .executeAndFetch(Text.class);
    }
  }
}
