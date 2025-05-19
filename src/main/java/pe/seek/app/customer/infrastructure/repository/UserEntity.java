package pe.seek.app.customer.infrastructure.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.seek.app.shared.common.CopyEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "auth_user")
public class UserEntity implements CopyEntity<UserEntity>, UserDetails {

    @Id
    @ReadOnlyProperty
    @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String authId;
    private String phone;
    private String password;
    private boolean enabled;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public UserEntity copyFrom(UserEntity item) {
        Optional.ofNullable(item.getPhone()).ifPresent(this::setPhone);
        Optional.ofNullable(item.getPassword()).ifPresent(this::setPassword);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
