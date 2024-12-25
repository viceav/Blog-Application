package cl.viceav.blog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.viceav.blog.service.BlogService;

@RestController
@RequestMapping("/blog")
public class BlogController {
  @Autowired
  private BlogService blogService;

  @GetMapping("/get/{number}")
  public Map<String, Object> getBlog(@PathVariable("number") Integer number) {

    return blogService.getBlog(number);
  }
}
