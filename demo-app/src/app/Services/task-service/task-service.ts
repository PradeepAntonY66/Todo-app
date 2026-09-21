import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Task} from '../../models/task';
import { LoginResponse } from '../../models/auth';
import { Observable } from 'rxjs';


@Service()
export class TaskService {
    private http = inject(HttpClient);
    private readonly apiUrl = 'http://localhost:8080/api/v1/todo/task';


    getTasks() : Observable<Task[]> {
        return this.http.get<Task[]>(`${this.apiUrl}`);
    }

    getTasksById( id : number){
        return this.http.get<Task>(`${this.apiUrl}/${id}`);
    }


}
