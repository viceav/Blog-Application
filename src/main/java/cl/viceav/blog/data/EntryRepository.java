package cl.viceav.blog.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends PagingAndSortingRepository<Entry, Integer> {
}
