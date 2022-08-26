package com.example.Cursos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Cursos.Models.Planificacion;

@Repository
public interface PlanificacionRepo extends JpaRepository<Planificacion,Long>{
Planificacion findById(int id);
}
