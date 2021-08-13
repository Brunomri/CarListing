package com.bruno.carlisting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints= {
        @UniqueConstraint(columnNames = {"type"})
})
@Getter
@Setter
@NoArgsConstructor
public class Role {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer roleId;

    @NotBlank(message = "Type is mandatory")
    @Size(min = 3, max = 20, message = "Type must have between 3 and 20 characters")
    private String type;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> users = new ArrayList<>();

    public Role(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "\nroleId = %s\n" +
                "type = %s\n",
                this.getRoleId(), this.getType());
    }
}
