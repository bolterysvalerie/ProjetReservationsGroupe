package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Models.Tag;
import be.icc.Pid_Reservations_2024.Repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Page<Show> searchByTagName(String tagName, Pageable pageable) {
        return tagRepository.searchByTagName(tagName, pageable);
    }

    public List<Tag> getTagByShowId(Long showId){
        return tagRepository.findByShowId(showId);
    }

    public Tag getByTag(String tag){
        return tagRepository.findByTag(tag).orElse(null);
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public void insertShowTagRelation(Long showId, Long tagId){
        tagRepository.insertTagShowRelation(showId, tagId);
    }

    public List<Show> getShowWithoutTag(){
        return tagRepository.findShowWithoutTags();
    }


}
