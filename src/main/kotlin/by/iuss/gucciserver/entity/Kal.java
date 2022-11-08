package by.iuss.gucciserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Kal {
    private @Id @GeneratedValue Long id;

    private String value;

    public Kal(String value) {
        this.value = value;
    }

    public Kal() {
    }


    public String getValue() {
        return value;
    }

    public void setValue(String kal) {
        this.value = kal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
