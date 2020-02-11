package by.ipps.admin.controller.base;

import by.ipps.admin.custom.CustomPage;
import by.ipps.admin.entity.BaseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface BaseEntityController<T extends BaseEntity> {

  @CrossOrigin
  @PostMapping
  ResponseEntity<T> create(@RequestBody @Valid T entity);

  @CrossOrigin
  @GetMapping(value = "/{id}")
  ResponseEntity<T> get(
      @PathVariable Long id,
      @RequestParam(value = "language", required = false, defaultValue = "ru") String language);

  @CrossOrigin
  @PutMapping
  ResponseEntity<T> update(@RequestBody @Valid T entity);

  @CrossOrigin
  @DeleteMapping(value = "/{id}")
  ResponseEntity<Boolean> remove(@PathVariable Long id);

  @CrossOrigin
  @GetMapping
  ResponseEntity<CustomPage<T>> getAll(
      @RequestParam(value = "page", required = false, defaultValue = "0") long page,
      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
      @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
      @RequestParam(value = "language", required = false, defaultValue = "ru") String language);
}
