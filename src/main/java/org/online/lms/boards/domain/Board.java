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

    @Column(name = "board_type", nullable = true)
    private String boardType;

//    @Column(name = "board_title", nullable = false)
//    private String boardTitle;
//
//    @Column(name = "board_yn")
//    private Boolean boardYn;

    @Builder
    public Board(Long boardNo) {
        this.boardNo = boardNo;
        this.boardType = boardType;
    }

}
