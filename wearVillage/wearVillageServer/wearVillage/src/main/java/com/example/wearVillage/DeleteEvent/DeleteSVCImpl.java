package com.example.wearVillage.DeleteEvent;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteSVCImpl implements DeleteSVC {


    private final DeleteDAO deleteDAO;
    @Override
    public int deleteById(Long postId) {
    return deleteDAO.deleteById(postId);
    }
}
