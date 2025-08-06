package com.srg.app.apigateway.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
/* Con esta etiqueta le decimos que está representando una tabla de nuestra
 * base de datos.
*/
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
/* Con la etiqueta "@Data" de la librería "Lombok" podemos ahorrar el hacer los getters y 
 * setters para cada atributo de la clase.
*/
@Data
/* Con las siguientes eqtiquetas podemos generar un constructor vacio y uno que reciba
 * todos los datos
*/
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /* Vamos a decir que genere el id de la tarea de forma automática*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private int estado;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "tasks")
    private List<User> user = new ArrayList<>();

}
