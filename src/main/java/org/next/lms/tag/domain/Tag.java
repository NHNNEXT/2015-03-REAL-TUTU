package org.next.lms.tag.domain;


import lombok.*;
import org.next.lms.content.domain.Content;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(exclude = {"id", "content"})
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
