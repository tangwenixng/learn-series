package com.twx.querydsl.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 预警类型
 * </p>
 *
 * @author twx
 * @since 2021-09-14
 */
@Entity
@Table(name = "warn_type")
public class WarnType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 预警类型名称
     */
    private String name;

    /**
     * 音频id
     */
    private Integer warnVoiceId;

    /**
     * 报警间隔 单位分钟
     */
    private Integer warnInterval;

    /**
     * 行为类型id
     */
    private Integer behaviorId;

    /**
     * 绑定的预警类型code
     */
    private String typeCode;

    /**
     * 行为类型json
     */
    private String behaviorJson;

    /**
     * 是否系统内置 0系统 1用户
     */
    private Integer isSystem;

    /**
     * 状态 0使用中 1未启用
     */
    @Column(columnDefinition = "TINYINT")
    private Integer enable;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWarnVoiceId() {
        return warnVoiceId;
    }

    public void setWarnVoiceId(Integer warnVoiceId) {
        this.warnVoiceId = warnVoiceId;
    }

    public Integer getWarnInterval() {
        return warnInterval;
    }

    public void setWarnInterval(Integer warnInterval) {
        this.warnInterval = warnInterval;
    }

    public Integer getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(Integer behaviorId) {
        this.behaviorId = behaviorId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getBehaviorJson() {
        return behaviorJson;
    }

    public void setBehaviorJson(String behaviorJson) {
        this.behaviorJson = behaviorJson;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
