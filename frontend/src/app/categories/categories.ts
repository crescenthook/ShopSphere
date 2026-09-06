import { Component, inject, signal } from '@angular/core';
import { CategoryModel } from '../model/category-model';
import { Category } from '../services/category';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-categories',
  styleUrl: './categories.css',
  templateUrl: './categories.html',
})
export class Categories {

  private categoryService = inject(Category);

  categories = signal<CategoryModel[]>([]);

  ngOnInit():void{
    this.categoryService.getCategories().subscribe({

      next: (response) => {
        this.categories.set(response);
      },
      error: (error) => {
        console.log('Failed to load categories:', error);
      }
    });
  }


}
