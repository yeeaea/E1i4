package org.online.lms.boards.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "board")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_no", updatable = false)
    private Long boardNo;

    @Column(name = "board_type", updatable = false)
    private String boardType;


    @Builder
    public Board(Long boardNo, String boardType) {
        this.boardNo = boardNo;
        this.boardType = boardType;
    }

}
