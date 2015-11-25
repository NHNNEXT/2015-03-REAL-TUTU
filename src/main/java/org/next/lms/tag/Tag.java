package org.next.lms.tag;


import lombok.*;
import org.next.lms.content.Content;
import org.next.lms.like.UserLikesContent;
import org.next.lms.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "content"})
@Entity
@Table(name = "TAG")
public class Tag {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "태그를 입력해주세요.")
    @Column(name = "TEXT")
    private String text;

}
