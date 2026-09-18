import { inject, Service } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cart } from '../model/cart-model';

@Service()
export class Carts {

    private http = inject(HttpClient);

    private apiUrl = 'http://localhost:8080/cart'

    getCart(userId: number): Observable<Cart> {
    const params = new HttpParams()
      .set('userId', userId);

    return this.http.get<Cart>(this.apiUrl, { params });
  }

    addToCart(userId: number,productId: number,quantity: number): Observable<string> {

    const params = new HttpParams()
      .set('userId', userId);

    const body = {
      productId,
      quantity
    };

    return this.http.post<string>(
      `${this.apiUrl}/items`,
      body,
      { params,
        responseType: 'text'
       }
    );
  }

    updateCartItem(userId: number,productId: number,quantity: number): Observable<string> {

    const params = new HttpParams()
      .set('userId', userId);

    const body = {
      quantity
    };

    return this.http.put<string>(
      `${this.apiUrl}/items/${productId}`,
      body,
      { params,
        responseType: 'text'
       }
    );
  }

  removeFromCart(userId: number,productId: number): Observable<string> {

  const params = new HttpParams()
    .set('userId', userId);

  return this.http.delete(
    `${this.apiUrl}/items/${productId}`,
    {
      params,
      responseType: 'text'
    }
  );
}

  clearCart(userId: number): Observable<string> {

  const params = new HttpParams()
    .set('userId', userId);

  return this.http.delete(
    this.apiUrl,
    {
      params,
      responseType: 'text'
    }
  );
}
}
