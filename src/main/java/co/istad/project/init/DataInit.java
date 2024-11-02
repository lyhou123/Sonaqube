package co.istad.project.init;

import co.istad.project.domain.Authority;
import co.istad.project.features.authority.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final AuthorityRepository authorityRepository;

    @PostConstruct
    void init() {
        // auth generate role (USER,ADMIN)
        if (authorityRepository.count() < 2) {
            List<String> authorityNames = List.of(
                   "user:read",
                    "user:write",
                    "user:update",
                    "user:delete",
                    "admin:control"

            );

            List<Authority> authorities = authorityNames.stream()
                    .map(this::createAuthority)
                    .toList();

            authorityRepository.saveAll(authorities);
        }
    }


    private Authority createAuthority(String authorityName) {

        Authority authority = new Authority();

        authority.setAuthorityName(authorityName);

        authority.setUuid(UUID.randomUUID().toString());

        return authorityRepository.save(authority);
    }

}
