package Task_Management_service.services;

import Task_Management_service.dto.request.ActivityLogsReq;
import Task_Management_service.dto.request.CommentReq;
import Task_Management_service.dto.response.ActivityLogsRes;
import Task_Management_service.dto.response.CommentRes;
import Task_Management_service.dto.response.PaginatedResp;

import java.util.List;

public interface ActivityLogService {
    ActivityLogsRes createActivityLogs(ActivityLogsReq activityLogsReq);
    ActivityLogsRes getActivityLogsById(Long id);
    PaginatedResp<ActivityLogsRes> getAllActivityLogs(int page, int size, String sortBy, String sortDirection);
    void deleteActivityLogsById(Long id);
}
