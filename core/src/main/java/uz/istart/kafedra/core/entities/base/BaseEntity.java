package uz.istart.kafedra.core.entities.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !getClass().getName().equalsIgnoreCase(obj.getClass().getName())){
            return true;
        }
        if(this==obj){
            return true;
        }
        return Objects.equals(getId(),((BaseEntity)obj).getId());
    }

    @Override
    public String toString() {
        return getClass().getName()+"{" +
                "id=" + id +
                '}';
    }
}
