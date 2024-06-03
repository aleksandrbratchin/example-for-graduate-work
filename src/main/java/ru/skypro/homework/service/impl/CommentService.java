package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
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
        Comment save = commentRepository.save(commentNew);
        Ad adToComment = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
        List<Comment> commentsList = adToComment.getComments();
        commentsList.add(save);
        adRepository.save(adToComment);
        return commentMapper.toCommentResponse(commentNew);
    }

    /**
     * Удаление комментария к объявлению
     *
     * @param adId      объявления
     * @param commentId комментария
     */
    public void deleteComment(Long adId, Long commentId) {
        Ad adToDeleteComment = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
        List<Comment> commentsList = adToDeleteComment.getComments();
        Comment nes = commentsList.stream()
                .filter((comment) -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("такого комментария не найдено"));
        commentsList.remove(nes);
        adRepository.save(adToDeleteComment);
        commentRepository.deleteById(commentId);
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

        Ad adToUpdateComment = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
        List<Comment> commentsList = adToUpdateComment.getComments();
        Comment nes = commentsList.stream()
                .filter((comment) -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("такого комментария не найдено"));
        nes.setText(createOrUpdateComment.getText());
        commentRepository.save(nes);
        return commentMapper.toCommentResponse(nes);
    }

    /**
     * Получение комментариев объявления
     *
     * @param id идентификатор объявления
     */
    public CommentsResponse getComments(Long id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Такого объявления не найдено"));
        return commentMapper.toCommentsResponse(ad.getComments());
    }

}
