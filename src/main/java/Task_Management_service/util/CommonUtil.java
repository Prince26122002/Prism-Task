package Task_Management_service.util;

import Task_Management_service.constant.Constants;
import Task_Management_service.dto.request.PageableRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class CommonUtil {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public ObjectMapper getMapperInstance(){
        return OBJECT_MAPPER;
    }

    public Pageable buildPageable(PageableRequest pageableRequest){
        int requestedPage = pageableRequest.getPage() - 1;
        Sort sort = Sort.by(pageableRequest.getSortColumn());
        if(Constants.ASCENDING.equalsIgnoreCase(pageableRequest.getSortOrder())){
            sort = sort.ascending();
        }else {
            sort = sort.descending();
        }
        return PageRequest.of(requestedPage, pageableRequest.getSize(), sort);
    }



}
