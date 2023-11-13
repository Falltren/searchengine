package searchengine.dto.indexation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SuccessfulIndexation extends IndexingResponse {

    private boolean result = true;
}