package Task_Management_service.controller;

import Task_Management_service.dto.request.WorkflowRequest;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.WorkflowResponse;
import Task_Management_service.implementation.WorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/create")
    public ResponseEntity<WorkflowResponse> createWorkflow(@RequestBody WorkflowRequest request) {
        return ResponseEntity.ok(workflowService.createWorkflow(request));
    }

    @GetMapping("/{workflowId}")
    public ResponseEntity<WorkflowResponse> getWorkflowById(@PathVariable Long workflowId) {
        return ResponseEntity.ok(workflowService.getWorkflowById(workflowId));
    }

    @PutMapping("/{workflowId}")
    public ResponseEntity<WorkflowResponse> updateWorkflow(@PathVariable Long workflowId, @RequestBody WorkflowRequest request) {
        return ResponseEntity.ok(workflowService.updateWorkflow(workflowId, request));
    }

    @DeleteMapping("/{workflowId}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable Long workflowId) {
        workflowService.deleteWorkflow(workflowId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResp<WorkflowResponse>> getAllWorkflows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(workflowService.getAllWorkflows(page, size, sortBy, sortDirection));
    }
}
