package tg.onlinelibrary.book;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Book> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book create(@Valid @RequestBody Book book) {
        book.setId(null);
        return repo.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book existing = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        return repo.save(existing);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Book not found: " + id);
        }
        repo.deleteById(id);
    }
}
