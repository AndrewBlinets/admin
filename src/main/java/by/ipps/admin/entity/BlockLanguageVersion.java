package by.ipps.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlockLanguageVersion extends BaseEntity {

  private String content;
  private String codeLanguage;

  public BlockLanguageVersion(long id, String content, String codeLanguage) {
    super(id);
    this.content = content;
    this.codeLanguage = codeLanguage;
  }
}
