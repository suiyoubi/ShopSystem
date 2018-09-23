# Shop-System 
Shop System is a simple server-side web API that models the common relationships between shops, orders and products.

## Model Relationship
* Shop has many products, while products could not belong to any store (still in the inventory!) 
* Order has many line items
* Line item contains a product and quantity
* Oder can have several line items (or nothing !)
* Shop has many orders





## API Endpoints

Complete JavaDoc is documented.

### Shop Resources
- `GET /shops/{shopId}`
- `GET /shops/all`
- `POST /shops`
- `DELETE /shops/{shopId}`
- `PATCH /shops/{shopId}/address`

### Product Resources
- `GET /products/{productId}`
- `GET /products/all`
- `POST /products` 
- `DELETE /products/{productId}`

### Order Resources
- `GET /orders/{orderId}`
- `GET /orders/all`
- `POST /orders`
- `PATCH /orders/{orderId}/productQuantityPair`
- `PATCH /{orderId}/shop`
