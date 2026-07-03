package com.ujjwal.blogapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostDTO {

    //data container class to safely catch JSON request payload from the client
    private String title;
    private String content;
    private String summary;
    private Long authorId;
    private List<Long> categoryIds; // Added to receive category selections from the client

}