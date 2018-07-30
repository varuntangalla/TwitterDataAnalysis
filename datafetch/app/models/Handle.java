package models;

import javax.persistence.*;

@Entity
@Table(name="handle")
public class Handle {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name = "handle")
    public String handle;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    public AppEntity entity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public AppEntity getEntity() {
        return entity;
    }

    public void setEntity(AppEntity entity) {
        this.entity = entity;
    }
}


