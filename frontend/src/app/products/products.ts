import { Component, inject, signal } from '@angular/core';
import { Product } from '../services/product';
import { ProductModel } from '../model/product-model';
import { ActivatedRoute } from '@angular/router';
import { Category } from '../services/category';
import { RouterLink } from '@angular/router';
import { Carts } from '../services/carts';

@Component({
  imports: [RouterLink],
  selector: 'app-products',
  styleUrl: './products.css',
  templateUrl: './products.html',
})
export class Products {

  private activatedRoute = inject(ActivatedRoute);
  private productService = inject(Product);
  private categoryService = inject(Category);
  private cartService = inject(Carts);

  products = signal<ProductModel[]>([]);
  categoryName = signal<string>('');
  
  message = signal<String>('');
  messageType = signal<'success' | 'error'>('success');

  addedProductId = signal<number | null>(null);

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

    addToCart(productId: number): void {

        this.cartService.addToCart(1,productId,1).subscribe({
    
            next: (response) => {  
                console.log(response);
                this.message.set('Product added to cart successfully!');
                this.messageType.set('success');
                this.addedProductId.set(productId);

                setTimeout(() => {this.addedProductId.set(null);}, 2000);
            },
            error: (error) => {
                console.error('Failed to add item to cart:', error);
                this.message.set('Failed to add product to cart.');
                this.messageType.set('error');
            }
  });
}
}