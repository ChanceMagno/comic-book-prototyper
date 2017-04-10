import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Book {
  private int id;
  private String title;
  private int user_id;

<<<<<<< HEAD
=======

>>>>>>> master
  public Book(String title, int user_id) {
    this.title = title;
    this.user_id = user_id;
  }

  public String getTitle() {
    return title;
  }

  public int getId() {
    return id;
  }

  public int getUserId() {
    return user_id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books(title, user_id) VALUES(:title, :user_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("user_id", this.user_id)
        .executeUpdate()
        .getKey();
    }
  }
<<<<<<< HEAD


=======
>>>>>>> master
}
