import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.*;
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

    get("/pages/:page_id/panels/:panel_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      model.put("panel", panel);
      model.put("template", "templates/edit-panel-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    File uploadDir = new File("src/main/resources/public/img/");
    uploadDir.mkdir();

    post("/pages/:page_id/panels/:panel_id", "multipart/form-data", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      try (InputStream image = request.raw().getPart("uploaded_file").getInputStream()) {
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");
        Files.copy(image, tempFile, StandardCopyOption.REPLACE_EXISTING);
        panel.setImagePath(tempFile.toString().substring(25));
        panel.update();
      }
      model.put("page", page);
      model.put("template", "templates/edit-page.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
