package model;

import javax.persistence.*;

/**
 * Created by Vasiliy on 16.12.2015.
 */

@Entity
@Table(name = "account",
        uniqueConstraints = {@UniqueConstraint(columnNames = "id")} )
public class Account {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false, unique = true, length = 11)
    private Integer id;

    @Column(name = "state", length = 11)
    private Integer state;

    public Integer getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", state=" + state +
                '}'+"\n";
    }
}
