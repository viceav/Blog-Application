package cl.viceav.blog.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.viceav.blog.data.Entry;
import cl.viceav.blog.data.EntryRepository;
import cl.viceav.blog.exceptions.FileException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Service
public class BlogService {

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  private FileService fileService;

  public Map<String, Object> getBlog(Integer number) {
    Page<Entry> page = entryRepository.findAll(PageRequest.of(number, 10));

    Map<String, Object> response = new HashMap<>();
    response.put("entries", page.getContent());
    response.put("hasNext", page.hasNext());
    response.put("hasPrevious", page.hasPrevious());
    response.put("numberOfElements", page.getNumberOfElements());
    response.put("currentPage", page.getNumber());

    return response;
  }

  public String addEntry(@Valid MultipartFile file, String title) {
    Map<String, String> response;

    try {
      response = fileService.store(file);
    } catch (FileException e) {
      return e.getMessage();
    }

    Entry entry = new Entry();
    entry.setFileName(response.get("fileName"));
    entry.setRoute(response.get("route"));
    entry.setTitle(title);

    try {
      entryRepository.save(entry);
    } catch (ConstraintViolationException e) {
      String message = e.getMessage();

      try {
        fileService.delete(response.get("fileName"));
      } catch (FileException ex) {
        message += "\n" + ex.getMessage();
      }

      return message;
    }

    return "Entry added successfully";
  }
}
