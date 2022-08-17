package com.example.Cursos.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImp implements FileService{
 
    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
      try {
        Files.createDirectory(root);
       } catch (Exception e) {
        throw new RuntimeException("No se ha inicializado la carpeta");
       }
    }

    @Override
    public void save(MultipartFile file) {
        try {
          Files.copy(file.getInputStream(),this.root.resolve(file.getOriginalFilename()));  
        } catch (Exception e) {
           throw new RuntimeException("No se puede guardar el archivo");
        }
        
    }

    @Override
    public Resource load(String filename) {
        try {
        Path file= root.resolve(filename);
        Resource resource= new UrlResource(file.toUri());
        if(resourse.exists()||resource.isReadable()){
            return resource;
        }
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
    }

    @Override
    public void deleteAll() {
       try {
        
       } catch (Exception e) {
        //TODO: handle exception
       }
        
    }

    @Override
    public Stream<Path> loadAll() {
      try {
        
      } catch (Exception e) {
        //TODO: handle exception
      }
        return null;
    }

    @Override
    public String deleteFile(String filename) {
        try {
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
    }

}
