package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'f_forumList')
class ForumListEntity implements Serializable{
    String fid
    String fname
    String img
    String creator
    String description
    String creatorIP
    boolean enable
    boolean authority
    String type
    String date

}
