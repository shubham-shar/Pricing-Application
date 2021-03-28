package com.skillovila.assignment.models.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    private Double amount;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonBackReference(value = "discounts")
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_id")
    @JsonBackReference(value = "discounts")
    private Coupon coupon;
    
    @Override
    public String toString() {
        return "Discount{" + "id=" + id + ", amount=" + amount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
                + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discount)) {
            return false;
        }
        Discount discount = (Discount) o;
        return Objects.equals(id, discount.id) && Objects.equals(amount, discount.amount) && Objects
                .equals(createdAt, discount.createdAt) && Objects.equals(updatedAt, discount.updatedAt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, amount, createdAt, updatedAt);
    }
}
