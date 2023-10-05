package com.example.wearVillage.UpdateEvent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class posting_table {
    @Id
    @Column(name="POST_ID")
    private int postId;
    @Column(name="POST_WRITER_ID")
    private String postWriterId;
    @Column(name="POST_SUBTITLE")
    private String postSubtitle;
    @Column(name="POST_PRICE")
    private String postPrice;
    @Column(name="POST_RENT_DEFAULT_PRICE")
    private String postRentDefaultPrice;
    @Column(name="POST_RENT_DAY_PRICE")
    private String postRentDayPrice;
    @Column(name="POST_TAG_TOP")
    private String postTagTop;
    @Column(name="POST_TAG_MIDDLE")
    private String postTagMiddle;
    @Column(name="POST_MAP_INFO")
    private String postMapInfo;
    @Column(name="POST_TEXT")
    private String postText;
    @Column(name="POST_IMAGE")
    private String postImage;
    @Column(name="POST_DATE")
    private String postDate;
    @Column(name="POST_MODIFY_DATE")
    private String postModifyDate;

}
