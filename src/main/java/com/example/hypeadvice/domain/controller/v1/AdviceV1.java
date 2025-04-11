package com.example.hypeadvice.domain.controller.v1;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Advice", description = "Gestão dos conselhos")
@CrossOrigin
@RestController
@RequestMapping("/advice/v1")
public class AdviceV1 {

    @Autowired public AdviceService adviceService;

    @GetMapping(
    		path = "/listar"
    		)
    @Operation(
    		summary = "Listar todos os conselhos cadastrados.",
    		description = "Método utilizado para listar todos os conselhos cadastrados."
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Advice.class)))}),
    })
    public ResponseEntity<List<Advice>> listar(HttpServletRequest request) {
        List<Advice> all = adviceService.findAll();
        ResponseEntity responseEntity = new ResponseEntity(all, HttpStatus.OK);
        return responseEntity;
    }
}
