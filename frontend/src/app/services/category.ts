import { inject, Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryModel } from '../model/category-model';

@Service()
export class Category {

    private http = inject(HttpClient);

    private apiUrl = 'http://localhost:8080/categories';

    getCategories():Observable<CategoryModel[]>{
        return this.http.get<CategoryModel[]>(this.apiUrl);
    }

    getCategoryById(id: number):Observable<CategoryModel>{
        return this.http.get<CategoryModel>(`${this.apiUrl}/${id}`);
    }
}
