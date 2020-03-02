package by.ipps.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockForFront extends BaseEntity implements Serializable {

  private int index;
  private String content;
  private long idLanguageVersion;

  public BlockForFront(long id, int index, String content, long idLV) {
    super(id);
    this.idLanguageVersion = idLV;
    this.index = index;
    this.content = content;
  }

  public BlockForFront(long id, int index, String content) {
    super(id);
    this.index = index;
    this.content = content;
  }
}
