import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Products } from './products/products';
import { Categories } from './categories/categories';
import { Login } from './login/login';

export const routes: Routes = [
    {
        path: '',
        component: Home
    },
    {
        path: 'products',
        component: Products
    },
    {
        path: 'categories',
        component: Categories
    },
    {
        path: 'categories/:categoryId',
        component: Products
    },
    {
        path: 'login',
        component: Login
    }
];
