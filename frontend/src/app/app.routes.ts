import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Products } from './products/products';
import { Categories } from './categories/categories';
import { Login } from './login/login';
import { ProductDetails } from './product-details/product-details';
import { Register } from './register/register';
import { CartComponent } from './cart/cart';
import { Checkout } from './checkout/checkout';

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
    },
    {
        path: 'products/:id',
        component: ProductDetails
    },
    {
        path: 'register',
        component: Register
    },
    {
        path: 'cart',
        component: CartComponent
    },
    {
        path: 'checkout',
        component: Checkout
    }
];
