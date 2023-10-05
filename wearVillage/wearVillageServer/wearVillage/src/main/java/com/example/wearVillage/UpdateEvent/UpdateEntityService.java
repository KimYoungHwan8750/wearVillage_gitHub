package com.example.wearVillage.UpdateEvent;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@Slf4j
@Service
public class UpdateEntityService {

    Logger log = LoggerFactory.getLogger(UpdateEntityService.class);

    private final UpdateEntityRepository updateEntityRepository;

    @Autowired
    public UpdateEntityService(UpdateEntityRepository updateEntityRepository) {
        this.updateEntityRepository = updateEntityRepository;
    }

    public void updatePost(int postId, String postSubtitle, String postPrice, String postRentDefaultPrice, String postRentDayPrice, String postTagTop, String postTagMiddle, String postMapInfo, String postText, String postModifyDate ) {
        if (postSubtitle == null || postText == null || postPrice==null || postRentDefaultPrice==null || postRentDayPrice==null || postTagTop == null || postTagMiddle == null || postModifyDate==null) {
            log.info("null error zz.");
            log.info("postSub={}",postSubtitle);
            log.info("postPri={}",postPrice);
            log.info("postDef={}",postRentDefaultPrice);
            log.info("postDay={}",postRentDayPrice);
            log.info("postTop={}",postTagTop);
            log.info("postMid={}",postTagMiddle);
            log.info("postMap={}",postMapInfo);
            log.info("postTex={}",postText);
            log.info("postMod={}",postModifyDate);
            return;
        }

        Optional<posting_table> optionalPost = updateEntityRepository.findById(postId);

        if (optionalPost.isPresent()) {
            posting_table post = optionalPost.get();
            post.setPostSubtitle(postSubtitle);
            post.setPostPrice(postPrice);
            post.setPostRentDefaultPrice(postRentDefaultPrice);
            post.setPostRentDayPrice(postRentDayPrice);
            post.setPostTagTop(postTagTop);
            post.setPostTagMiddle(postTagMiddle);
            post.setPostMapInfo(postMapInfo);
            post.setPostText(postText);
            post.setPostModifyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            updateEntityRepository.save(post);
        } else {
            log.info("포스팅 넘버 찾기 실패");


        }

    }
}