package com.example.Cursos.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cursos.Models.Planificacion;
import com.example.Cursos.Repository.PlanificacionRepo;
import com.example.Cursos.Service.PlanificacionService;

@RestController
@RequestMapping("/curso")
public class PlanificacionRest {
 @Autowired
 private PlanificacionService planificacionService;   

 private ResponseEntity<List<Planificacion>>getAll(){
return ResponseEntity.ok(planificacionService.findAll());
 }
}
