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
@EqualsAndHashCode
@Entity
@Table(name = "TAG")
public class Tag {

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<ContentHaveTag> contentHaveTags = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<LectureHaveTag> lectureHaveTags = new ArrayList<>();






    @Id
    @Column(name = "REPLY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "태그를을 입력해주세요.")
    @Column(name = "TAG")
    private String tag;






}
