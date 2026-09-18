import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { PaymentRequest, PaymentResponse } from '../model/payment-model';

@Service()
export class Payment {

    private http = inject(HttpClient);

    private apiUrl = 'http://localhost:8080/payments';

    createPayment(request: PaymentRequest){

        return this.http.post<PaymentResponse>(this.apiUrl, request);
    }

    getPayment(paymentId:number){

        return this.http.get<PaymentResponse>(`${this.apiUrl}/${paymentId}`);
    }
}
