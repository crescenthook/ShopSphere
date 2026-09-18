export interface OrderResponse {
  orderId: number;
  userId: number;
  totalAmount: number;
  status: string;
  createdAt: string;
  items: OrderItemResponse[];
}

export interface OrderItemResponse {
  productId: number;
  productName: string;
  price: number;
  quantity: number;
  subtotal: number;
}
