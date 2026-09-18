import { inject, Service } from '@angular/core';
import { OrderResponse } from '../model/order-model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Service()
export class Orders {

    private http = inject(HttpClient);

    private apiUrl = 'http://localhost:8080/orders';

    createOrder(userId:number):Observable<OrderResponse>{

        const params = new HttpParams().set('userId', userId);

        return this.http.post<OrderResponse>(this.apiUrl,null,{params});
    }
}
