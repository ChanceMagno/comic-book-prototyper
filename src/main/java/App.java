import java.io.*;
import java.nio.file.*;
import java.util.List;
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

    File uploadDir = new File("src/main/resources/public/img/");
    uploadDir.mkdir();

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

    get("/layout2", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/page-layout-2.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/layout3", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/page-layout-3.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:book_id/pages/:page_id/panels/:panel_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      Page page = Page.find(panel.getPageId());
      List<Text> texts = panel.getTexts();
      model.put("page", page);
      model.put("panel", panel);
      model.put("texts", texts);
      model.put("template", "templates/edit-panel-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:book_id/pages/:page_id/panels/:panel_id", "multipart/form-data", (request, response) -> {
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
      String url = String.format("/books/%d/pages/%d/panels/%d", page.getBookId(), page.getId(), panel.getId());
      response.redirect(url);      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:book_id/pages/:page_id/panels/:panel_id/texts/:text_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      Text text = Text.find(Integer.parseInt(request.params(":text_id")));
      model.put("page", page);
      model.put("panel", panel);
      model.put("text", text);
      model.put("template", "templates/edit-text-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:book_id/pages/:page_id/panels/:panel_id/texts/:text_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      Text text = Text.find(Integer.parseInt(request.params(":text_id")));
      text.setBody(request.queryParams("body"));
      text.setBoxStyle(request.queryParams("box_style"));
      text.setFont(request.queryParams("font"));
      text.update();
      String url = String.format("/books/%d/pages/%d/panels/%d", page.getBookId(), page.getId(), panel.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
