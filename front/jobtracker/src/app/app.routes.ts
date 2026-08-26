import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: '', redirectTo: 'hello', pathMatch: 'full' },
    {
        path: 'hello',
        loadComponent: () => import('./hello/hello.component').then((m) => m.HelloComponent),
    },
];