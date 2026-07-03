package com.ujjwal.blogapi.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long postId;
    private String content;
    private String commenterName;

}
