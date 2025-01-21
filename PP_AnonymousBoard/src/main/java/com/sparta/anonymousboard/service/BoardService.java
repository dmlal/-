package com.sparta.anonymousboard.service;

import com.sparta.anonymousboard.dto.BoardRequestDto;
import com.sparta.anonymousboard.dto.BoardResponseDto;
import com.sparta.anonymousboard.dto.DeleteRequestDto;
import com.sparta.anonymousboard.dto.UpdateRequestDto;
import com.sparta.anonymousboard.entity.Board;
import com.sparta.anonymousboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public BoardResponseDto writeBoard(BoardRequestDto requestDto){
        Board board = new Board(requestDto);

        Board saveBoard = boardRepository.save(board);

        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        return boardResponseDto;
    }

    public List<BoardResponseDto> getBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

//    @Transactional
//    public Long updateBoard(Long id, BoardRequestDto requestDto) {
//        Board board = findBoard(id);
//        board.update(requestDto);
//        return id;
//    }


    // 추가구현기능을 HttpStatus.UNAUTHORIZED 이걸로 구현해봤는데 포스트맨을 잘 모르다 보니 이 코드가 잘되는지 모르겠습니다.
    @Transactional
    public Long updateBoard(UpdateRequestDto updateRequestDto) {
        if(!checkPassword(updateRequestDto.getId(), updateRequestDto.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        Board board = findBoard(updateRequestDto.getId());
        board.update(updateRequestDto);
        return updateRequestDto.getId();
    }


    public Long deleteBoard(DeleteRequestDto updateDeleteRequestDto){
        if(!checkPassword(updateDeleteRequestDto.getId(), updateDeleteRequestDto.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        Board board = findBoard(updateDeleteRequestDto.getId());
        boardRepository.deleteById(updateDeleteRequestDto.getId());
        return updateDeleteRequestDto.getId();

    }
    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow( () ->
                new IllegalArgumentException(("선택한 글은 존재하지 않습니다"))
                );
    }

    public BoardResponseDto getBoard(Long id) {
        return Optional.ofNullable(findBoard(id))
                .map(BoardResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글을 찾을 수 없습니다."));
    }


    public boolean checkPassword(Long id, String password) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "선택한 글은 존재하지 않습니다"));

        return board.getPassword().equals(password);
    }



    //    public Long deleteBoard(Long id){
//        Board board = findBoard(id);
//        boardRepository.delete(board);
//        return id;
//    }


    //    public Long deleteBoard(Long id, String password){
//        if(!checkPassword(id, password)){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
//        }
//        Board board = findBoard(id);
//            boardRepository.deleteById(id);
//        return id;
//    }
}
