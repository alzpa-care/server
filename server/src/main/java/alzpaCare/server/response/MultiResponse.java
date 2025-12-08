package alzpaCare.server.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponse<T> {

    private PageInfo pageInfo;
    private List<T> result;

    public MultiResponse(List<T> result, Page page) {
        this.pageInfo = new PageInfo(page.getNumber() + 1,
                page.getSize(), page.getTotalElements(), page.getTotalPages());
        this.result = result;
    }


}
