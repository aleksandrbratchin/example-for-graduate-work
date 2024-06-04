package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentServiceApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceApi {

    private final CommentRepository commentRepository;

    private final AdRepository adRepository;

    @Override
    @Transactional
    public Comment addCommentToAd(Long id, Comment newComment) {
        Comment save = commentRepository.save(newComment);
        Ad adToComment = getAdById(id);
        adToComment.getComments().add(save);
        adRepository.save(adToComment);
        return save;
    }

    @Override
    @Transactional
    public void deleteComment(Long adId, Long commentId) {
        Ad adToDeleteComment = getAdById(adId);
        adToDeleteComment.getComments().remove(getCommentFromAdById(adId, commentId));
        adRepository.save(adToDeleteComment);
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(
            Long adId,
            Long commentId,
            CreateOrUpdateComment createOrUpdateComment
    ) {
        Comment comment = getCommentFromAdById(adId, commentId);
        comment.setText(createOrUpdateComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long id) {
        return getAdById(id).getComments();
    }

    private Ad getAdById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
    }

    private Comment getCommentFromAdById(Long adId, Long commentId) {
        return getAdById(adId).getComments().stream()
                .filter((comment) -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("такого комментария не найдено"));
    }

}
