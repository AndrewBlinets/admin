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
public class SectionForCreate extends BaseEntity implements Serializable {
  private List<SectionLanguageVersion> languageVersions;
  private int type;
  private int index;
  private int status;
  private List<Block> blocks;
  private List<Long> files;
  private String name;

  public void setFiles(List<FileManager> files) {
    if (files != null) {
      this.files = new ArrayList<>();
      for (FileManager fileManager : files) {
        this.files.add(fileManager.getId());
      }
    }
  }
}
