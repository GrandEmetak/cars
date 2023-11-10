/*
Copyright (c) 2016-2023 VMware Inc. or its affiliates, All Rights Reserved Halsyon.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.ohathaway.model.entity;

import java.util.Date;
import javax.persistence.GenerationType;
import javax.persistence.*;

import java.util.Objects;

/**
 * Модель данных описывающая объявление(Post)
 *
 * @since 22.11.21
 */
@Entity
@Table(name = "post", schema = "develop")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "header")
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "status")
    private Boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Auto auto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    private Image image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Post() {
    }

    public Post(int id, String header) {
        this.id = id;
        this.header = header;
    }

    public static Post of(
            String header,
            String description,
            String price,
            Boolean status
    ) {
        Post post = new Post();
        post.header = header;
        post.description = description;
        post.price = price;
        post.status = status;
        post.created = new Date(System.currentTimeMillis());
        return post;
    }

    public static Post emptyP() {
        Post post = Post.of("", "", "", false);
        post.setId(0);
        User user = User.of("", "", "");
        user.setId(0);
        Auto auto = Auto.of("", "", "", "", "", "", "");
        auto.setId(0);
        Image image = Image.of("");
        image.setId(0);
        post.addPhoto(image);
        post.addCar(auto);
        post.addUser(user);
        return post;
    }

    public void addCar(Auto auto) {
        this.auto = auto;
    }

    public void addUser(User usr) {
        this.user = usr;
    }

    public void addPhoto(Image ph) {
        this.image = ph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auto getCar() {
        return auto;
    }

    public void setCar(Auto auto) {
        this.auto = auto;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Image getPhoto() {
        return image;
    }

    public void setPhoto(Image image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Post: id=%s, header=%s, description=%s, Photo=%s, status=%s,"
                             + " User=%s, Car=%s, price=%s, created=%s",
                id, header, description, image, status, user, auto, price, created);
    }
}
