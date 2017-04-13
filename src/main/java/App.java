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

    get("/books", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("books", Book.all());
      model.put("template", "templates/library.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/book-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:book_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":book_id")));
      model.put("book", book);
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String bookTitle = request.queryParams("title");
      Book book = new Book(bookTitle, 1);
      book.save();
      model.put("book", book);
      model.put("template", "templates/pages.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:book_id/pages", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":book_id")));
      List<Page> pages = book.getPages();
      model.put("book", book);
      model.put("pages", pages);
      model.put("template", "templates/page-list.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:book_id/pages/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":book_id")));
      model.put("book", book);
      model.put("template", "templates/pages.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:book_id/pages/:page_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":book_id")));
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      model.put("page", page);
      model.put("book", book);
      model.put("panel", page.getPanels());
      String layoutSelected = page.getLayout();
      model.put("template", "templates/page-" + layoutSelected + ".vtl");
      model.put("navtemplate", "templates/page-navigation.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/pages", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.queryParams("book_id")));
      String layoutPicked = request.queryParams("layout");
      Page page = new Page(book.getId(), layoutPicked);
      page.save();
        int panelsNeeded = 0;
        if (layoutPicked.equals("layout1")) {
             panelsNeeded = 6;
        } else if (layoutPicked.equals("layout2")) {
          panelsNeeded = 5;
        } else if (layoutPicked.equals("layout3")) {
          panelsNeeded = 4;
        }
          Integer panelSequence = 0;
        for(int i = 0; i < panelsNeeded; i++ ) {
          panelSequence += i;
          ComicPanel panel = new ComicPanel(page.getId(), i);
          panel.save();
        }
      model.put("page", page);
      model.put("book", book);
      model.put("panel", page.getPanels());
      response.redirect("/books/" + book.getId() + "/pages/" + page.getId());
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

    get("/books/:book_id/pages/:page_id/panels/:panel_id/texts/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      model.put("page", page);
      model.put("panel", panel);
      model.put("template", "templates/new-text-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/texts", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Page page = Page.find(Integer.parseInt(request.queryParams("page_id")));
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.queryParams("panel_id")));
      int panel_id = panel.getId();
      int sequence = panel.getTexts().size() + 1;
      String body = request.queryParams("body");
      String orientation = request.queryParams("orientation");
      Text text = new Text(panel_id, sequence, body, orientation);
      text.save();
      String url = String.format("/books/%d/pages/%d/panels/%d", page.getBookId(), page.getId(), panel.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
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
      text.setOrientation(request.queryParams("orientation"));
      text.update();
      String url = String.format("/books/%d/pages/%d/panels/%d", page.getBookId(), page.getId(), panel.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:book_id/pages/:page_id/panels/:panel_id/texts/:text_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      ComicPanel panel = ComicPanel.find(Integer.parseInt(request.params(":panel_id")));
      Text text = Text.find(Integer.parseInt(request.params(":text_id")));
      text.delete();
      String url = String.format("/books/%d/pages/%d/panels/%d", page.getBookId(), page.getId(), panel.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/lost", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/building-comic.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/reader/books/:book_id/pages/:page_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":book_id")));
      Page page = Page.find(Integer.parseInt(request.params(":page_id")));
      model.put("page", page);
      model.put("book", book);
      model.put("panel", page.getPanels());
      String layoutSelected = page.getLayout();
      model.put("template", "templates/read-page-" + layoutSelected + ".vtl");
      model.put("navtemplate", "templates/page-navigation.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
}
