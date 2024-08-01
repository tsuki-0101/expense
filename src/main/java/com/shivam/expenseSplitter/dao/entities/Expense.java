package com.shivam.expenseSplitter.dao.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shivam.expenseSplitter.models.SplitType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "expenses")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "paid_by_user_id", nullable = false)
    private User paidBy;

    @ManyToMany
    @JoinTable(name = "expense_shared", joinColumns = @JoinColumn(name = "expense_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> sharedWith;

    @Column(name = "is_settled", nullable = false)
    private boolean isSettled;

    @Column(name = "split_type", nullable = false)
    private SplitType splitType;
}