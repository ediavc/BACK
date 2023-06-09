package com.portafolio.eavc.controlador;

import com.portafolio.eavc.Dto.dtoPersona;
import com.portafolio.eavc.Security.Controller.Mensaje;
import com.portafolio.eavc.entidad.Persona;
import com.portafolio.eavc.servicio.ImpPersonaServicio;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personas")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = {"http://localhost:4200", "https://frontendeavc.web.app"})
public class PersonaControlador {
    @Autowired
    ImpPersonaServicio personaService;

@GetMapping("/lista")
public ResponseEntity<List<Persona>> list(){
    List<Persona> list = personaService.list();
    return new ResponseEntity(list, HttpStatus.OK);
}

@GetMapping("/detail/{id}")
public ResponseEntity<Persona> getById(@PathVariable("id") int id){
    if(!personaService.existsById(id)){
        return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.BAD_REQUEST);
    }
    Persona persona = personaService.getOne(id).get();
    return new ResponseEntity(persona, HttpStatus.OK);
}
@PostMapping("/personas/crear")
   public String createPersona(@RequestBody Persona persona)
   {personaService.save(persona);
   return "La persona fue creada correctamente";
   }
@PutMapping("/update/{id}")
public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody dtoPersona dtopersona){
    
    if(!personaService.existsById(id)){
        return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
    }
    if(personaService.existsByNombre(dtopersona.getNombre()) && personaService.getByNombre(dtopersona.getNombre()).get().getId() != id){
        return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
    }
    if(StringUtils.isBlank(dtopersona.getNombre())){
        return new ResponseEntity(new Mensaje("El campo no puede estar vacío"), HttpStatus.BAD_REQUEST);
    }
    Persona persona = personaService.getOne(id).get();
    
    persona.setNombre(dtopersona.getNombre());
    persona.setApellido(dtopersona.getApellido());
    persona.setDescripcion(dtopersona.getDescripcion());
    persona.setImg(dtopersona.getImg());
    
    
    personaService.save(persona);
    
    return new ResponseEntity(new Mensaje("Perfil actualizado"), HttpStatus.OK);
}
}
