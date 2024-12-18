package Task_Management_service.services;

import Task_Management_service.dto.request.CommentReq;
import Task_Management_service.dto.request.TeamMembersReq;
import Task_Management_service.dto.response.CommentRes;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamMembersRes;

import java.util.List;

public interface CommentService {
    CommentRes createComment(CommentReq commentReq);
    CommentRes getCommentById(Long id);
    PaginatedResp<CommentRes> getAllComments(int page, int size, String sortBy, String sortDirection);
    void deleteCommentById(Long id);
}
