package pt.com.felix.todolist.task;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import pt.com.felix.todolist.utils.Utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/tasks")
public class TaskController 
{
    @Autowired
    private ITaskRepository taskRepository;


    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request)
    {
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt()) ) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data de incio/fim deve ser maior que a data atual");
        }
       
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data de incio deve ser menor que a data fim");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);

    }

    @GetMapping("/list")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks;
    }

    //quando precisamos que o utilizador esteja autenticado usamos o HttpServletRequest request no parametros
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request)
    {   
        var task = this.taskRepository.findById(id).orElse(null);

        var idUser = request.getAttribute("idUser");

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada!");
        }

        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Utilizador não tem premissao para alterar esta tarefa :C");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }

}
