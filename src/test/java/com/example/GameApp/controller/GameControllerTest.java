package com.example.GameApp.controller;


import com.example.GameApp.exception.ErrorCode;
import com.example.GameApp.exception.ErrorMessage;
import com.example.GameApp.model.Game;
import com.example.GameApp.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GameController.class)
public class GameControllerTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private GameService gameService;

    private String uri = "/games";

    static Game game1 = new Game("Jitunu", "08/07/2023 12:23:30", true);
    static Game game2 = new Game("Santanu", "03/07/2023 12:23:30", true);
    static Game game3 = new Game("Swaroop", "09/07/2023 12:23:30", true);

    private static Map<String, Game> dataMap = new HashMap<String, Game>();

    @BeforeAll
    public static void setUp() {
        dataMap.put("Jitunu", game1);
        dataMap.put("Santanu", game2);
        dataMap.put("Swaroop", game3);
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void createGameTest() throws Exception {
        String inputJson = mapToJson(game1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void retrieveAllGameList() throws Exception {

        when(gameService.getAllGames()).thenReturn(Arrays.asList(game1, game2, game3));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        List<Game> gameList = mapFromJson(content, List.class);
        assertTrue(gameList.size() == 3);
    }

    @Test
    public void retrieveGameBasedOnName() throws Exception {
        when(gameService.getGame(any(String.class))).thenReturn(game1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/Jitunu")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Game game = mapFromJson(content, Game.class);
        assertEquals(game.getName(), "Jitunu");
    }

    @Test
    public void deleteGameSuccessfullyBasedOnName() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + "/Jitunu")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.NO_CONTENT.value(), status);
    }

    @Test
    public void notFoundUpdateGameBasedOnName() throws Exception {
        when(gameService.updateGame(any(String.class), any(Game.class))).thenReturn(null);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/NoName")
                .content(mapToJson(game1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void updateGameBasedOnName() throws Exception {
        when(gameService.updateGame(any(String.class), any(Game.class))).thenReturn(dataMap.get("Jitunu"));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/Jitunu")
                .content(mapToJson(game1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void methodNotAllowedExceptionTest() throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains(ErrorCode.METHOD_NOT_ALLOWED_ERROR_CODE.getValue()));
    }

    @Test
    public void notFoundExceptionTest() throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri+"/test/test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains(ErrorCode.NOT_FOUND_ERROR_CODE.getValue()));
    }

    @Test
    public void genericExceptionTest() throws Exception{
        when(gameService.getAllGames()).thenThrow(new NullPointerException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains(ErrorCode.INTERNAL_SERVER_ERROR_CODE.getValue()));
    }

    @Test
    public void createGameValidationTest() throws Exception {
        Game game = new Game("", "08 07 2023 12:23:30", true);
        String inputJson = mapToJson(game);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
        String responseString = mvcResult.getResponse().getContentAsString();
        assertTrue(responseString.contains(ErrorCode.BAD_REQUEST_ERROR_CODE.getValue()));
        assertTrue(responseString.contains(ErrorMessage.GAME_NAME_INVALID_MESSAGE.getValue()));
        assertTrue(responseString.contains(ErrorMessage.CREATION_DATE_INVALID_MESSAGE.getValue()));
    }
}
