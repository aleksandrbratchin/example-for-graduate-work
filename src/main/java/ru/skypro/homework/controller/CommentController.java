package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.config.security.UserPrincipal;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;
import ru.skypro.homework.dto.response.CommentsResponse;
import ru.skypro.homework.service.impl.CommentService;

import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Получение комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentsResponse.class
                                    )

                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true))
                    ),

            },
            tags = "Комментарии"
    )

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getComments(
            @PathVariable int id,
            BindingResult bindingResult
    ) {
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
            }
        return ResponseEntity.ok().body(commentService.getComments(id));
    }

    @Operation(
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true))
                    ),

            },
            tags = "Комментарии"
    )
    @PostMapping(value = "/{id}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable int id,
            @RequestBody @Valid CreateOrUpdateComment properties,
            @AuthenticationPrincipal UserPrincipal principal,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        return ResponseEntity.ok().body(commentService.addCommentToAd(id,properties,principal.getUser()));

    }

    @Operation(
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )

            },
            tags = "Комментарии"
    )
    @PreAuthorize("hasRole('ADMIN') or #user.user.id == @adService.findById(#id).user.id")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable int adId,
            @PathVariable int commentId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        commentService.deleteComment(adId,commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )

            },
            tags = "Комментарии"
    )
    @PreAuthorize("hasRole('ADMIN') or #user.user.id == @adService.findById(#id).user.id")
    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable int adId,
            @PathVariable int commentId,
            @RequestBody @Valid CreateOrUpdateComment createOrUpdateComment,
            @AuthenticationPrincipal UserPrincipal principal,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
        return ResponseEntity.ok().body(commentService.updateComment(adId,commentId, createOrUpdateComment,principal.getUser()));
    }
}


