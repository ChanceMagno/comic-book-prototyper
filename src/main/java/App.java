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

    get("/books/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/comic-panel-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String book = request.queryParams("new-book");
      Book newBook = new Book("new book", 1);
      model.put("template", "templates/comic-panel-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/pages/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/comic-panel-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String book = request.queryParams("new-book");
      Book newBook = new Book("new book", 1);
      model.put("template", "templates/.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/layout1", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/Page-layout-1.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
