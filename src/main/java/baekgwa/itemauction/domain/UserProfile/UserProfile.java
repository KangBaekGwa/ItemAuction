package baekgwa.itemauction.domain.UserProfile;

import baekgwa.itemauction.domain.BaseEntity;
import baekgwa.itemauction.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile extends BaseEntity {
    @Id
    private Long userId;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String nickName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserGrade grade;
}