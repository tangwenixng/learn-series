package com.twx.querydsl.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author twx
 * @since 2021-09-29
 */
@Entity
@Table(name = "airport_map")
public class AirportMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 地图的url
     */
    private String imageUrl;

    @Override
    public String toString() {
        return "AirportMap{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
