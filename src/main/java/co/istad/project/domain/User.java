package co.istad.project.domain;

import co.istad.project.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    @Column(name = "user_name")

    private String name;
    private String email;
    private String profile;
    private String password;
    private LocalDateTime registeredDate;
    private LocalDateTime updatedDate;
    private String bio;
    private Boolean isDeleted;
    private Boolean isVerified;
    private Boolean isActive;


    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;



    // user with project
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Project> projectSet = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    Set<Role> roles = new HashSet<>();

    private String otp;
    private LocalDateTime otpGeneratedTime;


}
