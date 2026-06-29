package com.home.model.provider;

import com.home.model.catalog.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "provider_skills", schema = "provider", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"provider_id", "category_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderProfile providerProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
