import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    // Use this setting only for development
    externalStaticFileLocation(String.format("%s/src/main/resources/public", System.getProperty("user.dir")));

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/book/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/book-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/book/page", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String bookTitle = request.queryParams("title");
      Book book = new Book(bookTitle, 1);
      book.save();
      model.put("book", book);
      model.put("template", "templates/pages.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/book/:id/layout", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      String layoutPicked = request.queryParams("layout");
      Page page = new Page(book.getId(), layoutPicked);
      page.save();
        int panelsNeeded = 0;
        if (layoutPicked.equals("layout1")) {
             panelsNeeded = 6;
        }
          Integer panelSequence = 0;
        for(int i = 0; i < panelsNeeded; i++ ) {
          panelSequence += i;
          ComicPanel panel = new ComicPanel(page.getId(), i);
          panel.save();
        }
      model.put("book", book);
      model.put("page", page);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/layout1", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/Page-layout-1.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
