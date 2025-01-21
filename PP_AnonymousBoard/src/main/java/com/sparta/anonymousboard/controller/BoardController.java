package com.sparta.anonymousboard.controller;

import com.sparta.anonymousboard.dto.BoardRequestDto;
import com.sparta.anonymousboard.dto.BoardResponseDto;
import com.sparta.anonymousboard.dto.DeleteRequestDto;
import com.sparta.anonymousboard.dto.UpdateRequestDto;
import com.sparta.anonymousboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    // 게시글 작성
    @PostMapping("/post")
    public ResponseEntity<BoardResponseDto> writeBoard(@RequestBody BoardRequestDto requestDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(boardService.writeBoard(requestDto), makeUTF8Header() ,HttpStatus.OK);
    }


    // 게시글 전체 검색
    @GetMapping("/postlist")
    public ResponseEntity<List<BoardResponseDto>> getBoard() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(boardService.getBoard(), makeUTF8Header() ,HttpStatus.OK);
    }

    // 특정 인덱스의 게시글 검색
    @GetMapping("/post/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(boardService.getBoard(id), makeUTF8Header() ,HttpStatus.OK);
    }

    // 수정
//    @PutMapping("/post/{id}")
//    public ResponseEntity<Long> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        return new ResponseEntity<>(boardService.updateBoard(id, requestDto), makeUTF8Header() ,HttpStatus.OK);
//    }

//     수정  json 활용버전
    @PutMapping("/post/update")
    public ResponseEntity<Long> updateBoard(@RequestBody UpdateRequestDto requestDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(boardService.updateBoard(requestDto), makeUTF8Header() ,HttpStatus.OK);
    }


    // 삭제  json 활용버전
    @DeleteMapping("/post/delete")
    public ResponseEntity<Long> deleteBoard(@RequestBody DeleteRequestDto requestDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(boardService.deleteBoard(requestDto), makeUTF8Header() ,HttpStatus.OK);
    }

    private HttpHeaders makeUTF8Header() {
        HttpHeaders returnResHeaders = new HttpHeaders();
        returnResHeaders.add("Content-Type", "application/json;charset=UTF-8");

        return returnResHeaders;
    }

    // 삭제
//    @DeleteMapping("/post/{id}/{password}")
//    public ResponseEntity<Long> deleteBoard(@PathVariable Long id, @PathVariable String password) {
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        return new ResponseEntity<>(boardService.deleteBoard(id, password), makeUTF8Header() ,HttpStatus.OK);
//    }

}
