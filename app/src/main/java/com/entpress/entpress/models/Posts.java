package com.entpress.entpress.models;

/**
 * Created by utimac on 25/06/2018.
 */

import java.io.Serializable;

/**
 * Created by ST_004 on 19-08-2016.
 */
public class Posts implements Serializable {

    private String id;
    private String postAuthor;
    private String postDate;
    private String postDate_gmt;
    private String postContent;
    private String postTitle;
    private String postExcerpt;
    private String postStatus;
    private String commentStatus;
    private String pingStatus;
    private String postPassword;
    private String postName;
    private String toPing;
    private String pinged;
    private String postModified;
    private String postModifiedGMT;
    private String postContentFiltered;
    private String postParent;
    private String guid;
    private String menuOrder;
    private String postType;
    private String postMimeType;
    private String commentCount;
    private String metaId;
    private String postId;
    private String metaKey;
    private String metaValue;
    private String objectId;
    private String termTaxonomyId;
    private String termOrder;
    private String termId;
    private String taxonomy;
    private String description;
    private String parent;
    private String count;
    private String price;
    private String street;
    private String country;
    private String state;
    private String zipCode;
    private String categoryName;
    private String categorySlug;
    private String categoryId;
    private String thumbnail;
    private String images;
    private String city;
    private String lat;
    private String lng;
    private String email;

    public Posts() {

    }

    public Posts(String id, String postAuthor, String postDate, String postDate_gmt, String postContent, String postTitle, String postExcerpt, String postStatus, String commentStatus, String pingStatus, String postPassword, String postName, String toPing, String pinged, String postModified, String postModifiedGMT, String postContentFiltered, String postParent, String guid, String menuOrder, String postType, String postMimeType, String commentCount, String metaId, String postId, String metaKey, String metaValue, String objectId, String termTaxonomyId, String termOrder, String termId, String taxonomy, String description, String parent, String count, String price, String street, String country, String state, String zipCode, String categoryName, String categorySlug, String categoryId, String thumbnail, String images, String city, String lat, String lng, String email) {
        this.id = id;
        this.postAuthor = postAuthor;
        this.postDate = postDate;
        this.postDate_gmt = postDate_gmt;
        this.postContent = postContent;
        this.postTitle = postTitle;
        this.postExcerpt = postExcerpt;
        this.postStatus = postStatus;
        this.commentStatus = commentStatus;
        this.pingStatus = pingStatus;
        this.postPassword = postPassword;
        this.postName = postName;
        this.toPing = toPing;
        this.pinged = pinged;
        this.postModified = postModified;
        this.postModifiedGMT = postModifiedGMT;
        this.postContentFiltered = postContentFiltered;
        this.postParent = postParent;
        this.guid = guid;
        this.menuOrder = menuOrder;
        this.postType = postType;
        this.postMimeType = postMimeType;
        this.commentCount = commentCount;
        this.metaId = metaId;
        this.postId = postId;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
        this.objectId = objectId;
        this.termTaxonomyId = termTaxonomyId;
        this.termOrder = termOrder;
        this.termId = termId;
        this.taxonomy = taxonomy;
        this.description = description;
        this.parent = parent;
        this.count = count;
        this.id = id;
        this.price = price;
        this.street = street;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
        this.categoryName = categoryName;
        this.categorySlug = categorySlug;
        this.categoryId = categoryId;
        this.thumbnail = thumbnail;
        this.images = images;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDate_gmt() {
        return postDate_gmt;
    }

    public void setPostDate_gmt(String postDate_gmt) {
        this.postDate_gmt = postDate_gmt;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    public String getPostPassword() {
        return postPassword;
    }

    public void setPostPassword(String postPassword) {
        this.postPassword = postPassword;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getToPing() {
        return toPing;
    }

    public void setToPing(String toPing) {
        this.toPing = toPing;
    }

    public String getPinged() {
        return pinged;
    }

    public void setPinged(String pinged) {
        this.pinged = pinged;
    }

    public String getPostModified() {
        return postModified;
    }

    public void setPostModified(String postModified) {
        this.postModified = postModified;
    }

    public String getPostModifiedGMT() {
        return postModifiedGMT;
    }

    public void setPostModifiedGMT(String postModifiedGMT) {
        this.postModifiedGMT = postModifiedGMT;
    }

    public String getPostContentFiltered() {
        return postContentFiltered;
    }

    public void setPostContentFiltered(String postContentFiltered) {
        this.postContentFiltered = postContentFiltered;
    }

    public String getPostParent() {
        return postParent;
    }

    public void setPostParent(String postParent) {
        this.postParent = postParent;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostMimeType() {
        return postMimeType;
    }

    public void setPostMimeType(String postMimeType) {
        this.postMimeType = postMimeType;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTermTaxonomyId() {
        return termTaxonomyId;
    }

    public void setTermTaxonomyId(String termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    public String getTermOrder() {
        return termOrder;
    }

    public void setTermOrder(String termOrder) {
        this.termOrder = termOrder;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posts posts = (Posts) o;

        if (!id.equals(posts.id)) return false;
        return postTitle.equals(posts.postTitle);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + postTitle.hashCode();
        return result;
    }
}
