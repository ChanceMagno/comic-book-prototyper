import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/comics_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTextQuery = "DELETE FROM texts *;";
      con.createQuery(deleteTextQuery).executeUpdate();
      String deletePanelQuery = "DELETE FROM panels *;";
      con.createQuery(deletePanelQuery).executeUpdate();
      String deletePageQuery = "DELETE FROM pages *;";
      con.createQuery(deletePageQuery).executeUpdate();
      String deleteBookQuery = "DELETE FROM books *;";
      con.createQuery(deleteBookQuery).executeUpdate();
    }
  }
}
