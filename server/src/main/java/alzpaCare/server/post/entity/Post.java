package alzpaCare.server.post.entity;

import alzpaCare.server.audit.Auditable;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.postComment.entity.PostComment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String content;

    @Column(length = 254)
    private String[] imgUrl;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer viewCnt;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer likeCnt;

    @Column(length = 1, columnDefinition = "CHAR DEFAULT 'N'")
    private String deleteYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnoreProperties("member")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("post")
    private List<PostComment> postComments = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer commentCnt;


}
