## Requirements

- Java version 17.0.4.1
- Apache Maven 3.8.6


- **(Optional)** Protocol Buffer Plugin

## RPC Types

- Unary RPC: client sends a single request to a server and receives a single response in return.
- Server-side Streaming RPC: client sends a single request to a server and receives a multiple responses in return.
- Client-side Streaming RPC: client sends a multiple requests to a server and receives a single response in return.
- Bi-directional Streaming RPC: client sends a multiple requests to a server and receives a multiple responses in return.

## RPC Error Codes

Predefined grpc error codes are available by 
[link](https://developers.google.com/maps-booking/reference/grpc-api-v2/status_codes?hl=en).

## RPC Stub Types

- Blocking Stub: block current thread until all responses processed
- Stub: Allow process all responses async
- Future Stub: 