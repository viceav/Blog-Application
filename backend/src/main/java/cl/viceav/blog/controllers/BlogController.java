package cl.viceav.blog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.viceav.blog.services.BlogService;
import cl.viceav.blog.validators.ValidFile;

@RestController
@RequestMapping("/blog")
public class BlogController {
  @Autowired
  private BlogService blogService;

  @GetMapping("/get/{number}")
  public Map<String, Object> getBlog(@PathVariable("number") Integer number) {
    return blogService.getBlog(number);
  }

  @PostMapping("/add")
  public String addEntry(@RequestParam("entry") @ValidFile MultipartFile file, @RequestParam("title") String title) {
    return blogService.addEntry(file, title);
  }

  @PostMapping("/delete")
  public String deleteEntry(@RequestParam("id") Integer id) {
    return blogService.deleteEntry(id);
  }
}
