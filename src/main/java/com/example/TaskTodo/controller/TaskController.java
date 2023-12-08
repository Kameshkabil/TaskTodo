package com.example.TaskTodo.controller;


import com.example.TaskTodo.entity.Task;
import com.example.TaskTodo.exception.ResourceNotFoundException;
import com.example.TaskTodo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> getAllTask(){
        return this.taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskByID(@PathVariable(value = "id") long id){
        return this.taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id :" + id));
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){

        return this.taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@RequestBody Task task , @PathVariable(value = "id") long id){
        Task taskExisting = this.taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id :" + id));

        taskExisting.setName(task.getName());
        taskExisting.setDescription(task.getDescription());
        taskExisting.setStatus(task.getStatus());
        return this.taskRepository.save(taskExisting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable(value = "id") long id){
        Task taskExisting = this.taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id :" + id));
        this.taskRepository.delete(taskExisting);
        return ResponseEntity.ok().build();
    }
}
