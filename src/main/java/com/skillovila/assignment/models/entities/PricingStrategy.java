package com.skillovila.assignment.models.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "pricing_strategy", indexes = @Index(columnList = "price_id"))
public class PricingStrategy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private com.skillovila.assignment.models.enums.PricingStrategy type;
    
    @Column(name = "duration_in_months")
    private int durationInMonths;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id")
    @JsonBackReference("strategy")
    private Price price;
    
    @Override
    public String toString() {
        return "PricingStrategy{" + "id=" + id + ", type='" + type + '\'' + ", durationInMonths=" + durationInMonths
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PricingStrategy)) {
            return false;
        }
        PricingStrategy that = (PricingStrategy) o;
        return durationInMonths == that.durationInMonths && id.equals(that.id) && type.equals(that.type) && createdAt
                .equals(that.createdAt) && updatedAt.equals(that.updatedAt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, type, durationInMonths, createdAt, updatedAt);
    }
}
