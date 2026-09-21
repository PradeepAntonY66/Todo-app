import { Component, inject, OnInit } from '@angular/core';
import {TaskService } from '../../Services/task-service/task-service';
import { Task } from '../../models/task';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  imports: [CommonModule],
  selector: 'app-task-list-component',
  styleUrl: './task-list-component.css',
  templateUrl: './task-list-component.html'
})
export class TaskListComponent implements OnInit {

  tasks : Task[] = [];

  private taskService = inject(TaskService);
  private router = inject(Router);

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getTasks().subscribe({
      next : (data) => {

        
        console.log("API DATA : ", data);
        console.log("is array : ", Array.isArray(data))

        this.tasks = data;

        console.log(this.tasks);

      },
      error : (error) => {
        console.log("Error message: ", error);
      }
    })
  }


  openTodo( id : number ){
    this.router.navigate(['/tasks/',id]);
  }
}
