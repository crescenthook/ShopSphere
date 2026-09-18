export interface PaymentRequest {
  orderId: number;
  userId: number;
  amount: number;
}

export interface PaymentResponse {
  id: number;
  orderId: number;
  userId: number;
  amount: number;
  status: string;
  createdAt: string;
  updatedAt: string;
}