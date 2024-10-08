package pt.com.felix.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> 
{
    List<TaskModel> findByIdUser(UUID idUser);

    //TaskModel findByIdAndByIdUser(UUID id, UUID idUser);
}
