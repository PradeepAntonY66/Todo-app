import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../../Services/task-service/task-service';
import { Task } from '../../models/task';

@Component({
  imports: [],
  selector: 'app-task-details',
  styleUrl: './task-details.css',
  templateUrl: './task-details.html',
})
export class TaskDetails {
  private http = inject(ActivatedRoute);
  private taskService = inject(TaskService);

  task ?: Task;

  ngOnInit() {
    const id = Number( this.http.snapshot.paramMap.get('id'));
    console.log("Task id: ", id);
    this.loadTask(id);
  }

  loadTask(id : number) {
    this.taskService.getTasksById(id).subscribe({
      next : (task) => {
        this.task = task;
        console.log("Task details: ", task);
      },
      error : (error) => {
        console.log("Error response: ", error);
      }
    });
  }
}
