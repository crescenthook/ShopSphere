import { Component,inject,signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../services/product';
import { ProductModel } from '../model/product-model';
import { Carts } from '../services/carts';

@Component({
  imports: [],
  selector: 'app-product-details',
  styleUrl: './product-details.css',
  templateUrl: './product-details.html',
})
export class ProductDetails {

  private activatedRoute = inject(ActivatedRoute);
  private productService = inject(Product);
  private cartService = inject(Carts);

  product = signal<ProductModel | null>(null);
  message = signal<String>('');
  messageType = signal<'success' | 'error'>('success');

  addedProductId = signal<number | null>(null);

  ngOnInit():void{

    const productId = this.activatedRoute.snapshot.paramMap.get('id');

    if(productId){
      this.productService.getProductsById(Number(productId)).subscribe(
        {
          next: (response) => {
            console.log('Product response:', response);
            this.product.set(response);
          },
          error: (error) => console.log('Failed to load product:', error)
        });
    }
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
