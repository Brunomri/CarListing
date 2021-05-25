package com.bruno.carlisting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints= {
        @UniqueConstraint(columnNames = {"username"})
})
@Getter
@Setter
@NoArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long userId;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 30, message = "Username must have 30 characters or less")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 10, max = 25, message = "The password must have between 10 and 25 characters")
    private String password;

    @NotBlank(message = "Display name is mandatory")
    @Size(min = 3, max = 30, message = "The display name must have between 3 and 30 characters")
    private String displayName;

    @Pattern(regexp = "^[\\w.-]+@[\\w-]+[.]com$",
            message = "Contact must be an e-mail address in format: localpart@domain.com")
    private String contact;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Listing> userListings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Car> cars = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public User(String username, String password, String displayName, String contact) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.contact = contact;
    }
}
