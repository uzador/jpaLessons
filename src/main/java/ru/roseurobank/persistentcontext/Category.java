package ru.roseurobank.persistentcontext;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MuratovaO on 17.01.2015.
 */
@Entity
@Table(name = "CATEGORY_TABLE")
public class Category implements Auditable{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "CATEGORY_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_CATEGORY_ID")
    private Category parentCategory;
    //
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> childCategories = new ArrayList<>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    //
    public List<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public void addChildCategory(Category childCategory) {
        childCategory.setParentCategory(this);
        this.getChildCategories().add(childCategory);
    }

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + '\'';
    }
}
