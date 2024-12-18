package Task_Management_service.controller;

import Task_Management_service.dto.request.WorkflowStepRequest;
import Task_Management_service.dto.response.WorkflowStepResponse;
import Task_Management_service.implementation.WorkflowStepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/workflow-steps")
public class WorkflowStepController {

    private final WorkflowStepService stepService;

    public WorkflowStepController(WorkflowStepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping("/create")
    public ResponseEntity<WorkflowStepResponse> createStep(@RequestBody WorkflowStepRequest request) {
        WorkflowStepResponse response = stepService.createStep(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkflowStepResponse>> getStepsByWorkflowId(@RequestParam("workflowId") Long workflowId) {
        List<WorkflowStepResponse> responses = stepService.getStepsByWorkflowId(workflowId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{stepId}")
    public ResponseEntity<WorkflowStepResponse> updateStep(
            @PathVariable("stepId") Long stepId,
            @RequestBody WorkflowStepRequest request) {
        WorkflowStepResponse response = stepService.updateStep(stepId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable("stepId") Long stepId) {
        stepService.deleteStep(stepId);
        return ResponseEntity.noContent().build();
    }
}