package co.istad.project.git.dto;

import lombok.Builder;
import java.util.Collection;


@Builder
public record GitRespone(


        String owner,
        Collection<String> branches

) {
}
