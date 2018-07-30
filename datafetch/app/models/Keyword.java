package models;

import javax.persistence.*;

@Entity
@Table(name="keyword")
public class Keyword {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(name="keyword")
    public String keyword;

    @ManyToOne
    @JoinColumn(name="entity_id")
    public AppEntity entity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public AppEntity getEntity() {
        return entity;
    }

    public void setEntity(AppEntity entity) {
        this.entity = entity;
    }
}
