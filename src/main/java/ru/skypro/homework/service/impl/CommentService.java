package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

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

    public CommentResponse addCommentToAd(int id, CreateOrUpdateComment properties, User user) {
        Comment commentNew = commentMapper.toComment(properties);
        commentNew.setUser(user);
        commentNew.setCreatedAt(LocalDateTime.now());
        commentRepository.save(commentNew);
        Optional<Ad> adToComment = adRepository.findById((long) id);
        Ad adEntityToComment;
        if (adToComment.isPresent()) {
            adEntityToComment = adToComment.get();
        } else {
            throw new RuntimeException("Такого объявления не найдено");
        }
        List<Comment> commentsList = adEntityToComment.getComments();
        commentsList.add(commentNew);
        adEntityToComment.setComments(commentsList);
        adRepository.save(adEntityToComment);
        return commentMapper.toCommentResponse(commentNew);
    }

    /**
     * Удаление комментария к объявлению
     *
     * @param adId      объявления
     * @param commentId комментария
     */
    public void deleteComment(int adId, int commentId) {
        Optional<Ad> adToDeleteComment = adRepository.findById((long) adId);
        Ad adEntityToDeleteComment;
        if (adToDeleteComment.isPresent()) {
            adEntityToDeleteComment = adToDeleteComment.get();
        } else {
            throw new RuntimeException("Такого объявления не найдено");
        }
        List<Comment> commentsList = adEntityToDeleteComment.getComments();
        commentsList.remove(commentId);
        commentRepository.deleteById((long) commentId);
    }

    /**
     * Обновление комментария объявления
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     */

    public CommentResponse updateComment(int adId,
                                         int commentId,
                                         CreateOrUpdateComment createOrUpdateComment,
                                         User user) {
        Comment commentUpdate = commentMapper.toComment(createOrUpdateComment);
        commentUpdate.setId((long) commentId);
        commentUpdate.setUser(user);
        commentUpdate.setCreatedAt(LocalDateTime.now());
        commentRepository.save(commentUpdate);

        Optional<Ad> adToUpdateComment = adRepository.findById((long) adId);
        Ad adEntityToUpdateComment;
        if (adToUpdateComment.isPresent()) {
            adEntityToUpdateComment = adToUpdateComment.get();
        } else {
            throw new RuntimeException("Такого объявления не найдено");
        }
        List<Comment> commentList = adEntityToUpdateComment.getComments();
        commentList.set(commentId, commentUpdate);
        adRepository.save(adEntityToUpdateComment);

        return commentMapper.toCommentResponse(commentUpdate);
    }

    /**
     * Получение комментариев объявления
     *
     * @param id идентификатор объявления
     */

    public CommentsResponse getComments(int id) {
        Optional<Ad> ad = adRepository.findById((long) id);
        Ad adEntity;
        if (ad.isPresent()) {
            adEntity = ad.get();
        } else {
            throw new RuntimeException("Такого объявления не найдено");
        }

        return commentMapper.toCommentsResponse(adEntity.getComments());
    }
}
