package com.example.Cursos.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
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
        if(resource.exists()||resource.isReadable()){
            return resource;
        }else{
        throw new RuntimeException("No se puede leer el archivo");
        }
        } catch (MalformedURLException e ) {
          throw new RuntimeException("Error");
         }
       
    }

    @Override
    public void deleteAll() {
      FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
      try {
        return Files.walk(this.root,1).filter(path->!path.equals(this.root))
        .map(this.root::relativize);
      } catch (RuntimeException |IOException e) {
        throw new RuntimeException("No se puede cargar los archivos");
       }
        
    }

    @Override
    public String deleteFile(String filename) {
      try {
        Boolean delete = Files.deleteIfExists(this.root.resolve(filename));
            return "Borrado";
    }catch (IOException e){
        e.printStackTrace();
        return "Error al Borrando ";
    }
}

}
