package community.community.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursorResult<T> {
    private List<T> values;
    private Boolean hasNext;
    private Long nextCursorId;
}

