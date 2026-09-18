import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { Carts } from '../services/carts';
import { Orders } from '../services/orders';
import { Cart } from '../model/cart-model';
import { OrderResponse } from '../model/order-model';
import { ProductModel } from '../model/product-model';
import { Product } from '../services/product';
import { DatePipe } from '@angular/common';
import { Payment } from '../services/payment';
import { PaymentRequest } from '../model/payment-model';
import { PaymentResponse } from '../model/payment-model';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [RouterLink,DatePipe],
  templateUrl: './checkout.html',
  styleUrl: './checkout.css'
})
export class Checkout implements OnInit {

  private cartService = inject(Carts);
  private orderService = inject(Orders);
  private productService = inject(Product);
  private router = inject(Router);
  private paymentService = inject(Payment);
  
  products = signal<Map<Number,ProductModel>>(new Map());
  cart = signal<Cart | null>(null);
  order = signal<OrderResponse | null>(null);
  payment = signal<PaymentResponse | null>(null);
  message = signal<string>('');
  messageType = signal<'success' | 'error'>('success'); 

  userId = 1;

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {

    this.cartService.getCart(this.userId).subscribe({
      next: (response) => {
        this.cart.set(response);
        this.products.set(new Map());
        this.loadProducts(response);
      },
      error: (error) => {
        console.error('Failed to load cart:', error);
      }
    });

  }

  placeOrder(): void {

  this.orderService.createOrder(this.userId).subscribe({
    next: (response) => {

      this.order.set(response);

      console.log('Order created:', response);

      const paymentRequest: PaymentRequest = {
        orderId: response.orderId,
        userId: response.userId,
        amount: response.totalAmount
      };

      this.paymentService.createPayment(paymentRequest).subscribe({
        next: (paymentResponse) => {

          this.payment.set(paymentResponse);

          this.message.set(
            `Order placed and payment successful! Order ID: ${response.orderId}`
          );

          this.messageType.set('success');

          console.log('Payment created:', paymentResponse);
        },

        error: (error) => {

          this.message.set(
            `Order ${response.orderId} was created, but payment failed.`
          );

          this.messageType.set('error');

          console.error('Payment failed:', error);
        }
      });
    },

    error: (error) => {

      this.message.set(
        'Failed to place order. Please try again.'
      );

      this.messageType.set('error');

      console.error('Failed to create order:', error);
    }
  });

}

  loadProducts(cart: Cart): void{

    cart.items.forEach(item =>
    {
      this.productService.getProductsById(item.productId).subscribe({
        next: (product) => {

          const updatedProducts = new Map(this.products());
          updatedProducts.set(product.id, product);
          this.products.set(updatedProducts);
        },
        error: (error) => {
          console.error(`Failed to load product ${item.productId}`,error);
        }
      });
    });
  }
}