package com.example.Cursos.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cursos.Models.Planificacion;
import com.example.Cursos.Models.RespuestaGenerica;
import com.example.Cursos.Repository.PlanificacionRepo;

@RestController
@CrossOrigin(value = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class PlanificacionController {
	
@Autowired(required = false)
private PlanificacionRepo planificacionRepo;

@GetMapping("/CursoId/{id}")
public ResponseEntity<RespuestaGenerica> cursoById(@PathVariable int id){
    List<Planificacion> data = new ArrayList<>();
    RespuestaGenerica<Planificacion> respuesta = new RespuestaGenerica<>();
    try {
        data.add(planificacionRepo.findById(id));

        respuesta.setMensaje("Se genero LISTADO CURSOS EXITOXAMENTE");
        respuesta.setData(data);
        respuesta.setEstado(0);
    }catch (Exception e){
        respuesta.setMensaje("Hubo un problema al generar LISTADO CURSOS, causa ->"+e.getCause()+" || messagge->"+e.getMessage());
        respuesta.setData(data);
        respuesta.setEstado(1);
    }
    return new ResponseEntity<RespuestaGenerica>(respuesta, HttpStatus.OK);
}

@GetMapping("/ListaCursos")
public ResponseEntity<RespuestaGenerica> ListarCurso(){
    List<Planificacion> data = new ArrayList<>();
    RespuestaGenerica<Planificacion> respuesta = new RespuestaGenerica<>();
    try {
        data=planificacionRepo.findAll();
        respuesta.setMensaje("Se genero LISTADO CURSOS EXITOXAMENTE");
        respuesta.setData(data);
        respuesta.setEstado(0);
    }catch (Exception e){
        respuesta.setMensaje("Hubo un problema al generar LISTADO CURSOS, causa ->"+e.getCause()+" || messagge->"+e.getMessage());
        respuesta.setData(data);
        respuesta.setEstado(1);
    }
    return new ResponseEntity<RespuestaGenerica>(respuesta, HttpStatus.OK);
}


@PostMapping("/CrearCurso")
public ResponseEntity<RespuestaGenerica> CrearPlanificacion(@RequestBody Planificacion  cursoEnviado){
    List<Planificacion> data = new ArrayList<>();
    RespuestaGenerica<Planificacion> respuesta = new RespuestaGenerica<>();
    HttpStatus estado  = HttpStatus.CREATED;
    try {
    	Planificacion curso = planificacionRepo.save(cursoEnviado);
        data.add(curso);
        if(curso !=null){
            respuesta.setMensaje("SE REGISTRO EL CURSO CORRECTAMENTE");
            respuesta.setData(data);
            respuesta.setEstado(0);
        }else{
            respuesta.setMensaje("NO SE REGISTRO EL CURSO CORRECTAMENTE");
            respuesta.setData(data);
            respuesta.setEstado(1);
            estado= HttpStatus.BAD_REQUEST;
        }
    }catch (Exception e){
        respuesta.setMensaje("Hubo un problema al insertar CURSO, causa ->"+e.getCause()+ " || message -> "+e.getMessage());
        respuesta.setData(data);
        respuesta.setEstado(1);
        estado= HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<RespuestaGenerica>(respuesta, estado);
}

@PutMapping("/EditarCurso/{id}")
public ResponseEntity<RespuestaGenerica> EditarCurso(@RequestBody Planificacion cursoEnviado,@PathVariable Long id){
    List<Planificacion> data = new ArrayList<>();
    RespuestaGenerica<Planificacion> respuesta = new RespuestaGenerica<>();
    AtomicReference<HttpStatus> estado  = new AtomicReference<>(HttpStatus.OK);
    try {
    	Planificacion plan = planificacionRepo.findById(id)
                .map(res ->{
                    res.setNombre_curso(cursoEnviado.getNombre_curso());
                    res.setDocente(cursoEnviado.getDocente());
                    res.setFecha_inicio(cursoEnviado.getFecha_inicio());
                    res.setFecha_fin(cursoEnviado.getFecha_fin());
                    res.setHorario(cursoEnviado.getHorario());
                    res.setEstado(cursoEnviado.getEstado());
                  

                    //EN CASO DE ENCONTRAR SE ANADE DATA A RESPUESTA
                    data.add(res);
                    respuesta.setMensaje("SE MODIFICO EL CURSO CORRECTAMENTE");
                    respuesta.setData(data);
                    respuesta.setEstado(0);
                    //SE RETORNA CURSO MODIFICADO 
                    return planificacionRepo.save(res);
                })
                .orElseGet(()->{
                    respuesta.setMensaje("NO SE ENCONTRO CURSO CON EL ID INGRESADO: "+id);
                    respuesta.setData(data);
                    respuesta.setEstado(1);
                    estado.set(HttpStatus.BAD_REQUEST);
                    return new Planificacion();
                });
    }catch (Exception e){
        respuesta.setMensaje("Hubo un problema al MODIFICAR EL CURSO, causa ->"+e.getCause()+ " || message -> "+e.getMessage());
        respuesta.setData(data);
        respuesta.setEstado(1);
        estado.set(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<RespuestaGenerica>(respuesta, estado.get());
}


@DeleteMapping("/EliminarCurso/{id}")
public ResponseEntity EliminarCurso (@PathVariable Long id ){
    List<Planificacion> data = new ArrayList<Planificacion>();
    RespuestaGenerica<Planificacion> respuesta = new RespuestaGenerica<>();
    HttpStatus estado  = HttpStatus.OK;

    try {

        planificacionRepo.deleteById(id);
        if(planificacionRepo!=null){
            data.add(new Planificacion());
            respuesta.setMensaje("SE ELIMINO EL CURSO CORRECTAMENTE");
            respuesta.setData(data);
            respuesta.setEstado(0);
        }else{
            estado= HttpStatus.BAD_REQUEST;
            data.add(null);
            respuesta.setMensaje("NO SE ELIMINO EL CURSO CORRECTAMENTE");
            respuesta.setData(data);
            respuesta.setEstado(1);
        }
    } catch (Exception e) {
        estado= HttpStatus.BAD_REQUEST;
        respuesta.setMensaje("Hubo un problema al ELIMINAR EL CURSO, causa->"+e.getCause()+ " ||  message -> "+e.getMessage());
        respuesta.setData(data);
        respuesta.setEstado(1);
    }

    return new ResponseEntity<RespuestaGenerica>(respuesta,estado);
}
}
