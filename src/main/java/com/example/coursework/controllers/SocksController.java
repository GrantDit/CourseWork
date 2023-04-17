package com.example.coursework.controllers;
import com.example.coursework.Service.SocksService;
import com.example.coursework.model.Color;
import com.example.coursework.model.Size;
import com.example.coursework.model.Socks;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Socks", description = "CRUD-операции по работе с носками")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "Новое поступление носков",
            description = "Зарегистрировать новое поступление носков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки успешно зарегистрированы",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description =  "Неверный запрос. Отсутствующие или некорректные параметры запроса формата",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )

    })
    public ResponseEntity<Socks> createSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        Socks createdSocks = socksService.addSocks(socks, quantity);
        return ResponseEntity.ok(createdSocks);
    }

    @PutMapping
    @Operation(
            summary = "Продажа носков",
            description = "Продажа носков со склада"
    )
    @Parameters(value = {
            @Parameter(name = "quantity", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки были успешно проданы",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Носков нет на складе. Плохая просьба. Отсутствующие или некорректные параметры запроса формата",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Socks> editSocks(@RequestBody Socks socks,
                                           @RequestParam long quantity) {
        Socks socks1 = socksService.editSocks(socks, quantity);
        if (socks1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(socks1);
    }

    @GetMapping
    @Operation(
            summary = "Найти количество носков по хлопковой части",
            description = "Показать количество носков по параметру"
    )
    @Parameters(
            value = {
                    @Parameter(
                            name = "cotton min", example = "0"
                    ),
                    @Parameter(
                            name = "cotton max", example = "100"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки были успешно найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Отсутствующие или некорректные параметры формат запроса",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Long> getSocksNumByParam(@RequestParam Color color,
                                                   @RequestParam Size size,
                                                   @RequestParam int cottonMin,
                                                   @RequestParam int cottonMax) {
        long count = socksService.getSocksNumByParam(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping
    @Operation(
            summary = "Списание бракованных носков",
            description = "Зарегистрировать запись бракованных носков"
    )
    @Parameters(value = {
            @Parameter(name = "quantity", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Бракованные носки были успешно списаны",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description =  "Носков нет на складе.Неверный запрос. Отсутствующие или некорректные параметры формат запроса",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Socks.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteSocks(@RequestBody Socks socks, @RequestParam long quantity) {
        if (socksService.deleteSocks(socks, quantity)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}