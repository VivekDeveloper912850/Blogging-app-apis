package com.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "post_title" , length = 100 , nullable = false)
    private String title;
    @Column(length = 10000)
    private String content;
    private  String imagName;
    private Date addDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Comments> comments = new HashSet<>();

}
