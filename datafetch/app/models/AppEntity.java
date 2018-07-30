package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="entity")
public class AppEntity {

    @Id
    @Column(name="entity_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int entityId;

    @Column(name="entity_name")
    public String entityName;

    @ManyToOne
    @JoinColumn(name="user_id")
    public Token token;

    @OneToMany(mappedBy = "entity", targetEntity = Keyword.class, cascade = CascadeType.ALL)
    public List<Keyword> keywords;

    @OneToMany(mappedBy = "entity", targetEntity = Handle.class, cascade = CascadeType.ALL)
    public List<Handle> handles;

    public List<Handle> getHandles() {
        return handles;
    }

    public void setHandles(List<Handle> handles) {
        this.handles = handles;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
