package com.skillovila.assignment.models.entities;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Data
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    private String name;
    
    @ManyToMany(targetEntity = Course.class,cascade = CascadeType.ALL )
    private Set<Course> courses;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "discounts")
    private Set<Discount> discounts;
    
    @Override
    public String toString() {
        return "Coupon{" + "id=" + id + ", name='" + name + '\'' + ", courses=" + courses + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", discounts=" + discounts + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coupon)) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return id.equals(coupon.id) && name.equals(coupon.name) && createdAt.equals(coupon.createdAt) && updatedAt
                .equals(coupon.updatedAt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, updatedAt);
    }
}
