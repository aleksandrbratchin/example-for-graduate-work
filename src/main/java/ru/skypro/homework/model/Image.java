package ru.skypro.homework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image extends ParentIDEntity {

    private byte[] data;

    @Builder
    public Image(Long id, byte[] data) {
        super(id);
        this.data = data;
    }

}