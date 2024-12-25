package cl.viceav.blog.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cl.viceav.blog.data.Entry;
import cl.viceav.blog.data.EntryRepository;

@Service
public class BlogService {
  @Autowired
  private EntryRepository entryRepository;

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
}
