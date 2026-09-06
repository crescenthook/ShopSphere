import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductModel } from '../model/product-model';

@Service()
export class Product {

    private http = inject(HttpClient);

    private apiUrl = 'http://localhost:8080/products';

    getProducts(): Observable<ProductModel[]>{
        return this.http.get<ProductModel[]>(this.apiUrl);
    }

    getProductsByCategory(categoryId:number): Observable<ProductModel[]>{
        return this.http.get<ProductModel[]>(`${this.apiUrl}/category/${categoryId}`);
    }
    
}
