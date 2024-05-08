package com.nisum.userregister.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.userregister.services.UserRegisterService;
import com.nisum.userregister.dtos.request.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/userRegister/")
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @SuppressWarnings("rawtypes")
    @PostMapping(path = "add")
    @Operation(summary = "Add user", description = "Service to add a new user to DB")
    @RequestBody(description = "Request", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject("{\n"
            + "\"name\": \"Juan Rodriguez\",\n" + "\"email\": \"juan@rodriguez.org\",\n"
            + "\"password\": \"hunter2\",\n" + "\"phones\": [\n" + "{\n" + "\"number\": \"1234567\",\n"
            + "\"citycode\": \"1\",\n" + "\"countrycode\": \"57\"\n" + "}\n" + "]\n" + "}\n")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Usuario registrado correctamente", value = ""))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Se presentó una excepción en el proces (usuario existente, password incorrecta, correo inválido)", value = ""))), })
    public ResponseEntity addUserRegister(@org.springframework.web.bind.annotation.RequestBody UserRequest newUser) {
        return userRegisterService.addUserRegister(newUser);
    }

    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/getAll", produces = "application/json")
    @Operation(summary = "Get all the users", description = "Service to get all users using token returned in user record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Información recuperada correctamente", value = ""))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "El token enviado es inválido", value = ""))), })
    public ResponseEntity getAllUsers(@RequestParam String token) {
        return userRegisterService.getAllUsers(token);
    }
}
