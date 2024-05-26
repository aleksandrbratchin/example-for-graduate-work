package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.response.AdResponse;
import ru.skypro.homework.dto.response.AdsResponse;
import ru.skypro.homework.dto.response.ExtendedAdResponse;
import ru.skypro.homework.service.impl.AdService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @Operation(
            tags = "Объявления",
            summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AdsResponse.class
                                    )
                            )
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.ok().body(adService.getAllAds());
    }

    @Operation(
            tags = "Объявления",
            summary = "Добавление объявления",
            operationId = "addAd",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(
                            mediaType = "multipart/form-data",
                            encoding = @Encoding(
                                    name = "properties",
                                    contentType = "application/json"
                            ))
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AdResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    schema = @Schema(hidden = true)
                            )
                    )
            )),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized")
            }

    )

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAd(
            @RequestPart @Valid CreateOrUpdateAd properties,
            @RequestPart MultipartFile image
    ) {

        return ResponseEntity.ok().body(adService.createAd(properties, image));
    }

    @Operation(
            tags = "Объявления",
            summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExtendedAdResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdById(@PathVariable long id) {
        return ResponseEntity.ok(adService.getAdById(id));
    }

    @Operation(
            tags = "Объявления",
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ExtendedAdResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable long id) {
        adService.deleteAd(id);
    }

    @Operation(
            tags = "Объявления",
            summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = CreateOrUpdateAd.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD_REQUEST"
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAdInfo(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Объявления",
            summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = AdsResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getAdsByAuthUser() {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Объявления",
            summary = "Обновление картинки объявления",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(
                            mediaType = "multipart/form-data"
                    )
                    }
            ),
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/octet-stream",

                            schema = @Schema(
                                    type = "string",
                                    format = "byte"
                            )
                    )),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }

    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImageAds(@PathVariable Integer id,
                                            @RequestPart(name = "image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}
