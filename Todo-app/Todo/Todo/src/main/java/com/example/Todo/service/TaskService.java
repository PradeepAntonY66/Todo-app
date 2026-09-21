package com.example.Todo.service;

import com.example.Todo.dto.task.TaskRequest;
import com.example.Todo.dto.task.TaskResponse;
import com.example.Todo.entity.Task;
import com.example.Todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest request){
        if (taskRepository.existsByName(request.getName()))
            throw new RuntimeException("Task already exists");

        Task savedTask = new Task(
                request.getName(),
                request.getDescription(),
                LocalDateTime.now(),
                request.isCompleted()
        );

        return mapToResponse(taskRepository.save(savedTask));
    }

    public List<TaskResponse> getAllTasks(){
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse updateTask(TaskRequest request, Long id){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));

        existingTask.setName(request.getName());
        existingTask.setDescription(request.getDescription());
        existingTask.setCreatedAt(LocalDateTime.now());
        existingTask.setCompleted(request.isCompleted());

        return mapToResponse(taskRepository.save(existingTask));
    }

    public void deleteTask(Long id){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));

        taskRepository.deleteById(id);
    }

    public TaskResponse getTaskById(Long id){
        return mapToResponse(taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found")));
    }

    public TaskResponse mapToResponse(Task task){
        return new TaskResponse(task.getId(), task.getName(), task.getDescription(), task.getCreatedAt());
    }
}
