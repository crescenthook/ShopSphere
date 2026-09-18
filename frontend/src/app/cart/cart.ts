import { Component, OnInit,inject, signal } from '@angular/core';
import { Carts } from '../services/carts';
import { Cart } from '../model/cart-model';
import { ProductModel } from '../model/product-model';
import { Product } from '../services/product';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class CartComponent implements OnInit {

  cart = signal<Cart | null>(null);
  products = signal<Map<number, ProductModel>>(new Map());

  userId = 1;

  private cartService = inject(Carts);
  private productService = inject(Product)

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart(this.userId).subscribe({
      next: (response) => {
        this.cart.set(response);
        this.loadProducts(response);
      },
      error: (error) => {
        console.error('Failed to load cart', error);
      }
    });
  }

  loadProducts(cart: Cart): void {

    cart.items.forEach(item => {

      this.productService.getProductsById(item.productId).subscribe({
        next: (product) => {

          const updatedProducts = new Map(this.products());

          updatedProducts.set(product.id, product);

          this.products.set(updatedProducts);
        },
        error: (error) => {
          console.error(
            `Failed to load product ${item.productId}`,
            error
          );
        }
      });

    });
  }

  updateQuantity(productId:number, quantity:number):void{

    if(quantity < 1){
      return;
    }

    this.cartService.updateCartItem(this.userId, productId,quantity).subscribe({
      next: () => {this.loadCart();},
      error: (error) => {console.log('Failed to update the Cart Item', error);}
    });
  }

  removeItem(productId:number):void{

    this.cartService.removeFromCart(this.userId,productId).subscribe({
      next: () => {this.loadCart();},
      error: (error) => {console.error('Failed to remove cart item:', error);}
    });
  }
}