package by.ipps.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Logger extends BaseEntity implements Serializable {

  private User user;
  private String tableName;
  private long rowId;
  private String event;
  private String oldValue;
}
