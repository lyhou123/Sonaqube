package co.istad.project.mapper;

import co.istad.project.domain.Authority;
import co.istad.project.features.authority.dto.AuthorityRequest;
import co.istad.project.features.authority.dto.AuthorityResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    Authority toAuthorityRequest(AuthorityRequest authorityRequest);

    AuthorityResponse authorityToResponse(Authority authority);


}
