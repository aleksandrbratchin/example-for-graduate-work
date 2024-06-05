package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdServiceApi;
import ru.skypro.homework.service.CommentServiceApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceApi {

    private final CommentRepository commentRepository;

    private final AdServiceApi adService;

    @Override
    @Transactional
    public Comment addCommentToAd(Long adId, Comment newComment) {
        Comment save = commentRepository.save(newComment);
        Ad ad = adService.getAdById(adId);
        ad.getComments().add(save);
        adService.save(ad);
        return save;
    }

    @Override
    @Transactional
    public void deleteCommentFromAd(Long adId, Long commentId) {
        Ad ad = adService.getAdById(adId);
        Comment comment = getCommentFromAdById(ad, commentId);
        ad.getComments().remove(comment);
        adService.save(ad);
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment updateComment(
            Long adId,
            Long commentId,
            CreateOrUpdateComment createOrUpdateComment
    ) {
        Ad ad = adService.getAdById(adId);
        Comment comment = getCommentFromAdById(ad, commentId);
        comment.setText(createOrUpdateComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsForAd(Long id) {
        return adService.getAdById(id).getComments();
    }

    private Comment getCommentFromAdById(Ad ad, Long commentId) {
        return ad.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException(
                        "Комментарий с id=" + commentId + " не найден в объявлении с id=" + ad.getId()));
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("Комментария с id=" + id + " не найдено")
        );
    }

}
