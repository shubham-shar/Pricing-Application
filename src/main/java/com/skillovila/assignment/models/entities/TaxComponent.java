package com.skillovila.assignment.models.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "tax_component", indexes = @Index(columnList = "price_id"))
public class TaxComponent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    @Column(name = "gst_amount")
    private Double gstAmount;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id")
    @JsonBackReference("taxes")
    private Price price;
    
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
        return "TaxComponent{" + "id=" + id + ", gstAmount=" + gstAmount + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxComponent)) {
            return false;
        }
        TaxComponent that = (TaxComponent) o;
        return id.equals(that.id) && gstAmount.equals(that.gstAmount) && createdAt
                .equals(that.createdAt) && updatedAt.equals(that.updatedAt);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, gstAmount, createdAt, updatedAt);
    }
}
