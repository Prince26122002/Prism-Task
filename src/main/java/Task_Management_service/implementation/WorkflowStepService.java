package Task_Management_service.implementation;

import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.dto.request.WorkflowStepRequest;
import Task_Management_service.dto.response.WorkflowStepResponse;
import Task_Management_service.entity.WorkflowEntity;
import Task_Management_service.entity.WorkflowStepEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.repository.WorkflowRepository;
import Task_Management_service.repository.WorkflowStepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowStepService {

    private final WorkflowStepRepository stepRepository;
    private final WorkflowRepository workflowRepository;

    public WorkflowStepService(WorkflowStepRepository stepRepository, WorkflowRepository workflowRepository) {
        this.stepRepository = stepRepository;
        this.workflowRepository = workflowRepository;
    }

    public WorkflowStepResponse createStep(WorkflowStepRequest request) {
        WorkflowEntity workflow = workflowRepository.findById(request.getWorkflowId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.WORKFLOW_NOT_FOUND.getErrorCode(), ApiErrorCodes.WORKFLOW_NOT_FOUND.getErrorMessage()));

        WorkflowStepEntity step = new WorkflowStepEntity();
        step.setType(request.getType());
        step.setPosition(request.getPosition());
        step.setWorkflow(workflow);

        WorkflowStepEntity savedStep = stepRepository.save(step);
        return mapEntityToDto(savedStep);
    }

    public List<WorkflowStepResponse> getStepsByWorkflowId(Long workflowId) {
        List<WorkflowStepEntity> steps = stepRepository.findByWorkflowIdOrderByPositionAsc(workflowId);
        if (steps.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.STEP_NOT_FOUND.getErrorCode(), ApiErrorCodes.STEP_NOT_FOUND.getErrorMessage());
        }
        return steps.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    public WorkflowStepResponse updateStep(Long stepId, WorkflowStepRequest request) {
        WorkflowStepEntity step = stepRepository.findById(stepId)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.STEP_NOT_FOUND.getErrorCode(), ApiErrorCodes.STEP_NOT_FOUND.getErrorMessage()));

        WorkflowEntity workflow = workflowRepository.findById(request.getWorkflowId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.WORKFLOW_NOT_FOUND.getErrorCode(), ApiErrorCodes.WORKFLOW_NOT_FOUND.getErrorMessage()));

        step.setType(request.getType());
        step.setPosition(request.getPosition());
        step.setWorkflow(workflow);

        WorkflowStepEntity updatedStep = stepRepository.save(step);
        return mapEntityToDto(updatedStep);
    }

    public void deleteStep(Long stepId) {
        WorkflowStepEntity step = stepRepository.findById(stepId)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.STEP_NOT_FOUND.getErrorCode(), ApiErrorCodes.STEP_NOT_FOUND.getErrorMessage()));
        stepRepository.delete(step);
    }

    private WorkflowStepResponse mapEntityToDto(WorkflowStepEntity step) {
        WorkflowStepResponse response = new WorkflowStepResponse();
        response.setStepId(step.getId());
        response.setType(step.getType());
        response.setPosition(step.getPosition());
        response.setWorkflowId(step.getWorkflow().getId());
        return response;
    }
}
