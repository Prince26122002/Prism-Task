package Task_Management_service.controller;

import Task_Management_service.dto.request.ActivityLogsReq;
import Task_Management_service.dto.response.ActivityLogsRes;
import Task_Management_service.dto.response.CommentRes;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activityLogs")
public class ActivityLogsController {
    @Autowired
    private final ActivityLogService activityLogService;

    public ActivityLogsController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping("/create")
    public ResponseEntity<ActivityLogsRes> createActivityLogs(@RequestBody ActivityLogsReq activityLogsReq) {
        ActivityLogsRes reqs = activityLogService.createActivityLogs(activityLogsReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(reqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogsRes> getActivityLogsById(@PathVariable Long id ){
        ActivityLogsRes resp = activityLogService.getActivityLogsById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(resp);
    }


    @GetMapping("/all")
    public ResponseEntity<PaginatedResp<ActivityLogsRes>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(activityLogService.getAllActivityLogs(page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id) {
        activityLogService.deleteActivityLogsById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
