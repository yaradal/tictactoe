package com.tictactoe.tictactoe;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BoardController {
    Board board;

    public BoardController(Board board) {
        this.board = board;
    }

    @PostMapping(value = "/play", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlayDTO greeting(@RequestParam int row, @RequestParam int column, @RequestParam Board.Player player) {
        try {
            Board.Winner winner = board.Play(row,column,player);
            return new PlayDTO(Optional.of(winner),"");
        } catch(Exception e) {
            return new PlayDTO(Optional.empty(),e.toString());
        }
    }

    public class PlayDTO {
        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        public Optional<Board.Winner> winner;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public String error;

        public PlayDTO(Optional<Board.Winner> winner, String error) {
            this.winner = winner;
            this.error = error;
        }
    }
}