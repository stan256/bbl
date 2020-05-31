package com.github.stan256.bblaccount.model.entity;

import com.github.stan256.bblaccount.model.DeviceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Entity(name = "user_device")
public class UserDevice extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_device_id")
    @SequenceGenerator(name = "user_device_id", sequenceName = "user_device_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "device_type")
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @OneToOne(optional = false, mappedBy = "userDevice")
    private RefreshToken refreshToken;

    @Column(name = "refresh_active")
    private Boolean refreshActive;
}
