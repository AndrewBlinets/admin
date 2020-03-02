package by.ipps.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block extends BaseEntity implements Serializable {

  private int index;
  private List<BlockLanguageVersion> languageVersions;
  private int status;

  public Block(int index, int status) {
    this.index = index;
    this.status = status;
    this.languageVersions = new ArrayList<>();
  }

  public Block(int index, int status, long id) {
    this.id = id;
    this.index = index;
    this.status = status;
    this.languageVersions = new ArrayList<>();
  }
}
