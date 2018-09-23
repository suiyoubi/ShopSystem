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
- `GET /shops/{shopId}` : Returns json data about a single shop.

__sample call:__
```
curl --request GET \
  --url http://localhost:8080/shops/2
```

__sample response__
```
200 OK
{
    "shopId": 2,
    "name": "toy-shop",
    "address": "fake-address2"
}
```

- `GET /shops/all` : Returns json data about all shops.

__sample call:__
```
curl --request GET \
  --url http://localhost:8080/shops/all
```

__sample response:__
```
200 OK
[
    {
        "shopId": 1,
        "name": "car-shop",
        "address": "fake-address1"
    },
    {
        "shopId": 3,
        "name": "EGame-shop",
        "address": "Vancouver"
    },
    {
        "shopId": 4,
        "name": "mobile-shop",
        "address": "fake address 4"
    }
]
```
- `POST /shops` : Creates a shop and returns the json data about the created one.

__sample call:__
```
curl --request POST \
  --url http://localhost:8080/shops \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "mobile-shop",
	"address": "fake address 4"
}'
```

__sample response:__
```
200 OK
{
    "shopId": 4,
    "name": "mobile-shop",
    "address": "fake address 4"
}
```


- `DELETE /shops/{shopId}` : deletes an existing shop.

__sample call:__
```
curl --request DELETE \
  --url http://localhost:8080/shops/2
```

__sample response:__
```
200 OK
```
- `PATCH /shops/{shopId}/address` : update the address information for a specified shop.

__sample call:__
```
curl --request PATCH \
  --url http://localhost:8080/shops/1/address \
  --data 'new address'
```

__sample response:__
```
200 OK
{
    "shopId": 1,
    "name": "car-shop",
    "address": "new address"
}
```
### Product Resources
- `GET /products/{productId}` :Returns json data about a single product.

__sample call:__

```
curl --request GET \
  --url http://localhost:8080/products/3
```

__sample response:__
```
200 OK
{
    "productId": 3,
    "name": "iPhone 3GS",
    "price": 300,
    "description": "ancient good stuff",
    "shop": null
}
```
- `GET /products/all` : Returns json data about all products.

__sample call:__

```
curl --request GET \
  --url http://localhost:8080/products/all
```

__sample response:__
```
200 OK
[
    {
        "productId": 1,
        "name": "iphone",
        "price": 1000,
        "description": "expensive mobile phone",
        "shop": null
    },
    {
        "productId": 2,
        "name": "iPad",
        "price": 500,
        "description": "bigger iPhone",
        "shop": null
    },
    {
        "productId": 3,
        "name": "iPhone 3GS",
        "price": 300,
        "description": "ancient good stuff",
        "shop": null
    },
    {
        "productId": 5,
        "name": "Tesla",
        "price": 100000,
        "description": "cool stuff",
        "shop": {
            "shopId": 1,
            "name": "car-shop",
            "address": "fake-address1"
        }
    }
]
```
- `POST /products` : creates a new product and returns the created product.

  __sample call:__
```
curl --request POST \
  --url http://localhost:8080/products \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Tesla",
	"price": 100000,
	"description": "cool stuff",
	"shopId": 1
}'
```

 __sample response:__
```
200 OK
{
    "productId": 5,
    "name": "Tesla",
    "price": 100000,
    "description": "cool stuff",
    "shop": {
        "shopId": 1,
        "name": "car-shop",
        "address": "fake-address1"
    }
}
```
- `DELETE /products/{productId}` : deletes an existing product.

__sample call:__
```
curl --request DELETE \
  --url http://localhost:8080/products/4
```

__sample response:__
```
200 OK
```
### Order Resources
- `GET /orders/{orderId}` : returns json data about a single order.

  __sample call:__
```
curl --request GET \
  --url http://localhost:8080/orders/4
```

__sample response:__
```
200 OK
{
    "orderId": 4,
    "name": "order-with-one-item",
    "lineItems": [
        {
            "productId": 1,
            "productName": "iphone",
            "quantity": 30,
            "unitPrice": 1000,
            "totalPrice": 30000
        }
    ],
    "orderTotalPrice": 30000,
    "shop": null
}
```
- `GET /orders/all` : returns json data about all orders.

__sample call:__
```
curl --request GET \
  --url http://localhost:8080/orders/all
```

__sample response:__
```
200 OK
```
- `POST /orders` : creates a new order and returns the created order.

__sample call:__
```
curl --request POST \
  --url http://localhost:8080/orders \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "empty-order",
	"shopId": 1
}'

curl --request POST \
  --url http://localhost:8080/orders/ \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "order-with-two-item",
	"items": {"1":"3", "2":4}
}'
```

__sample response:__
```
200 OK
[
    {
        "orderId": 1,
        "name": "another-another-empty-order",
        "lineItems": [],
        "orderTotalPrice": 0,
        "shop": null
    },
    {
        "orderId": 3,
        "name": "order-with-two-item",
        "lineItems": [
            {
                "productId": 1,
                "productName": "iphone",
                "quantity": 3,
                "unitPrice": 1000,
                "totalPrice": 3000
            },
            {
                "productId": 2,
                "productName": "iPad",
                "quantity": 4,
                "unitPrice": 500,
                "totalPrice": 2000
            }
        ],
        "orderTotalPrice": 5000,
        "shop": null
    }
]
```
- `PATCH /orders/{orderId}/productQuantityPair` : updates a line item in a order, can be used to modify the exisiting product's quantity or to add new product.

__sample call:__
```
curl --request PATCH \
  --url http://localhost:8080/orders/4/productQuantityPair \
  --header 'Content-Type: application/json' \
  --data '{
	"productId": 1,
	"quantity": 30
}'
```

__sample response:__
```
200 OK
{
    "orderId": 1,
    "name": "another-another-empty-order",
    "lineItems": [
        {
            "productId": 1,
            "productName": "iphone",
            "quantity": 30,
            "unitPrice": 1000,
            "totalPrice": 30000
        }
    ],
    "orderTotalPrice": 30000,
    "shop": null
}
```
- `PATCH /{orderId}/shop` : updates the shop information about a specified order.

__sample call:__
```
curl --request PATCH \
  --url http://localhost:8080/orders/4/shop \
  --header 'Content-Type: application/json' \
  --data '"2"'
```

__sample response:__
```
200 OK
{
    "orderId": 4,
    "name": "another-empty-order",
    "lineItems": [],
    "orderTotalPrice": 0,
    "shop": {
        "shopId": 2,
        "name": "suiyoubi",
        "address": "fake-address2"
    }
}
```