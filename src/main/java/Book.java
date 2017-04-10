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

  public static List<Book> all() {
    String sql = "SELECT id, title FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public static Book find(int id) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM books where id=:id";
       Book book = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Book.class);
       return book;
     }
   }

   public List<Page> getPages() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM pages where bookId=:id";
     return con.createQuery(sql)
       .addParameter("id", this.id)
       .executeAndFetch(Page.class);
   }
 }

  @Override
   public boolean equals(Object otherBook) {
     if (!(otherBook instanceof Book)) {
       return false;
     } else {
       Book newBook = (Book) otherBook;
       return this.getId() == newBook.getId() &&
              this.getTitle().equals(newBook.getTitle());
     }
 }

  public void update(String title) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE books SET title = :title WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("title", title)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "DELETE FROM books WHERE id = :id;";
     con.createQuery(sql)
       .addParameter("id", id)
       .executeUpdate();
     }
   }
}
