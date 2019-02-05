package com.forum.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'f_forum_type')
class ForumTypeEntity  implements Serializable{
    @Id
    String type
}
