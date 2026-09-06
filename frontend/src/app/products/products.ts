import { Component, inject, signal } from '@angular/core';
import { Product } from '../services/product';
import { ProductModel } from '../model/product-model';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../services/category';

@Component({
  imports: [],
  selector: 'app-products',
  styleUrl: './products.css',
  templateUrl: './products.html',
})
export class Products {

  private activatedRoute = inject(ActivatedRoute);
  private productService = inject(Product);
  private categoryService = inject(Category);

  products = signal<ProductModel[]>([]);
  categoryName = signal<string>('');

ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe(params => {

        const categoryId = params.get('categoryId');

        if (categoryId) {

            const id = Number(categoryId);

            this.categoryService.getCategoryById(id).subscribe({
              next: (response) => {
                this.categoryName.set(response.name);
              },
              error : (error) => {
                console.log('Failed to log category', error)
              }
            })

            // Category-specific products
            this.productService.getProductsByCategory(Number(categoryId)).subscribe({
                next: (response) => {
                    this.products.set(response);
                },
                error: (error) => {
                    console.error('Failed to load category products:', error);
                }
            });

        } else {

            // All products
            this.productService.getProducts().subscribe({
                next: (response) => {
                    this.products.set(response);
                },
                error: (error) => {
                    console.error('Failed to load products:', error);
                }
            });

        }
    });
}
}