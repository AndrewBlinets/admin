package by.ipps.admin.custom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPage<T> {

  List<T> content;
  long totalElements;
  private int number;
  private int size;
  private int totalPages;
  private int numberOfElements;
}
