package uz.istart.kafedra.admin.form.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTablesForm<T> implements Serializable {
    private static final long serialVersionUID = 422250768862371526L;

    public int draw;
    public int recordsTotal;
    public int recordsFiltered;
    private String error;
    public List<T> data;

}
