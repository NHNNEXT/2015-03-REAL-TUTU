package org.next.lms.tag;


import lombok.*;
import org.next.lms.like.UserLikesContent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tag", "contentHaveTags", "lectureHaveTags"})
@Entity
@Table(name = "TAG")
public class Tag {

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private List<ContentHaveTag> contentHaveTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private List<LectureHaveTag> lectureHaveTags = new ArrayList<>();


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "태그를을 입력해주세요.")
    @Column(name = "TAG")
    private String tag;

}
