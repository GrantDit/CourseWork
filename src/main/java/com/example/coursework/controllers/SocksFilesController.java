package com.example.coursework.controllers;

import com.example.coursework.Service.SocksFilesService;
import com.example.coursework.model.Socks;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/socks/files")
@Tag(name = "Socks files controller", description = "Операция с файлом Socks")
public class SocksFilesController {
    private final SocksFilesService socksFilesService;

    public SocksFilesController(SocksFilesService socksFilesService) {
        this.socksFilesService = socksFilesService;
    }

    @GetMapping("/export")
    @Operation(
            summary = "Скачать файл Socks",
            description = " Скачать файл Socks"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Socks file успешно загружен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Файл пуст",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = socksFilesService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка нового Socks файла",
            description = "Загрузить новый файл Socks"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Новый Socks file успешно загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Void> uploadDataFIle(@RequestParam MultipartFile file) {
        socksFilesService.uploadDataFile(file);
        if (file.getResource().exists()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}