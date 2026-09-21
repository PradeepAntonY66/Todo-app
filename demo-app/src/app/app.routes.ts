import { Routes } from '@angular/router';
import { TaskListComponent } from './components/task-list-component/task-list-component';
import { authGuard } from './guards/auth-guard';
import { Login } from './pages/login/login';
import { TaskDetails } from './components/task-details/task-details';

export const routes: Routes = [
    {
        path : "tasks",
        component : TaskListComponent,
        canActivate : [authGuard]
    },
    {
        path : "login",
        component : Login
    },
    {
        path : "tasks/:id",
        component : TaskDetails
    },
    {
        path : '',
        redirectTo : "login",
        pathMatch : 'full'
    }
];
