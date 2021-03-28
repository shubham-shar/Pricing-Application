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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(indexes = @Index(columnList = "course_id"))
public class Price {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    @Column(name = "base_amount")
    private Double baseAmount;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonBackReference(value = "prices")
    private Course course;
    
    @OneToOne(mappedBy = "price", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("taxes")
    private TaxComponent taxes;
    
    @OneToOne(mappedBy = "price", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("strategy")
    private PricingStrategy strategy;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @Override
    public String toString() {
        return "Price{" + "id=" + id + ", baseAmount=" + baseAmount + ", taxes=" + taxes
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price price = (Price) o;
        return id.equals(price.id) && baseAmount.equals(price.baseAmount) && course.equals(price.course) && taxes
                .equals(price.taxes) && createdAt.equals(price.createdAt) && updatedAt.equals(price.updatedAt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, baseAmount, course, taxes, createdAt, updatedAt);
    }
}
