package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, AdRepository adRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Добавление комментария к объявлению
     *
     * @param id объявления
     * @return CommentResponse
     */
    public CommentResponse addCommentToAd(Long id, CreateOrUpdateComment properties) {
        Comment commentNew = commentMapper.toComment(properties);
        Comment saveComment = commentRepository.save(commentNew);
        Ad adEntityToComment = adRepository.findById(id).orElseThrow(() -> new RuntimeException("Такого объявления не найдено"));
        List<Comment> commentsList = adEntityToComment.getComments();
        commentsList.add(saveComment);
        adRepository.save(adEntityToComment);
        return commentMapper.toCommentResponse(saveComment);
    }

    /**
     * Удаление комментария к объявлению
     *
     * @param adId      объявления
     * @param commentId комментария
     */
    public void deleteComment(Long adId, Long commentId) {
        Ad ad = adRepository.findById(adId).orElseThrow(() -> new RuntimeException("Такого объявления не найдено"));
        Comment comment = ad.getComments().stream().filter(adComment -> adComment.getId().equals(commentId)).findFirst().orElseThrow(() -> new RuntimeException("Такого комментария не найдено"));
        ad.getComments().remove(comment);
        adRepository.save(ad);
        commentRepository.deleteById(comment.getId());
    }

    /**
     * Обновление комментария объявления
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     */
    public CommentResponse updateComment(Long adId,
                                         Long commentId,
                                         CreateOrUpdateComment createOrUpdateComment) {
        Ad ad = adRepository.findById(adId).orElseThrow(() -> new RuntimeException("Такого объявления не найдено"));
        Comment comment = ad.getComments().stream().filter(adComment -> adComment.getId().equals(commentId)).findFirst().orElseThrow(() -> new RuntimeException("Такого комментария не найдено"));
        comment.setText(createOrUpdateComment.getText());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    /**
     * Получение комментариев объявления
     *
     * @param id идентификатор объявления
     */
    public CommentsResponse getComments(Long id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException("Такого объявления не найдено"));
        return commentMapper.toCommentsResponse(ad.getComments());
    }
}
