package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.response.CommentResponse;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {


    @Operation(
            summary = "Получение комментариев объявления",
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

@GetMapping ("/{id}/comments")
    public ResponseEntity <?> getComments (@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
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
    @PostMapping (value = "/{id}")
    public ResponseEntity <?> addComment (@PathVariable int id, @RequestPart CreateOrUpdateComment properties) {
            return ResponseEntity.ok().build();

    }
    @Operation(
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
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

@DeleteMapping("/{adId}/comments/{commentId}")
public  ResponseEntity <?> deleteComment (@PathVariable int adId,@PathVariable int commentId ) {
        CreateOrUpdateComment comment = commentService.getComment (adId,commentId);
        if (сomment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
}
    @Operation(
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType ="application/json",
                            schema = @Schema (

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

    @PatchMapping ( path = "/{adId}/comments/{commentId}")
    public  ResponseEntity <CreateOrUpdateComment> updateComment (@PathVariable int adId,@PathVariable int commentId, @RequestPart CreateOrUpdateComment createOrUpdateComment ) {
       CreateOrUpdateComment oldComment = commentService.getComment (commentId);
        if (oldComment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(createOrUpdateComment);
    }

}