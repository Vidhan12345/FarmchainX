# 🌾 FarmChainX - Agricultural Supply Chain Traceability Platform

A comprehensive blockchain-inspired supply chain management system for agricultural products, built with Spring Boot and modern web technologies.

## 📋 Overview

FarmChainX is a full-stack application that enables complete traceability of agricultural products from farm to consumer. It provides role-based access for Farmers, Consumers, Distributors, and Retailers, ensuring transparency and trust in the agricultural supply chain.

## ✨ Features

### 🌱 For Farmers
- **Product Management**: Create, update, and manage agricultural products
- **Batch Tracking**: Auto-generated unique batch IDs for each product
- **Status Updates**: Track product lifecycle (Cultivation → Harvested → In Transit → Delivered)
- **Quality Certification**: Organic certification and quality grading
- **Image Upload**: Multiple product images support
- **Real-time Dashboard**: Monitor all products and their status

### 🛒 For Consumers
- **Product Discovery**: Browse and search products by name, crop type, or keywords
- **Organic Filter**: Filter products by organic certification
- **Product Details**: View complete product information including farming practices
- **Supply Chain Journey**: Track complete product journey from farm to store
- **QR Code Tracing**: Scan QR codes to verify product authenticity and origin
- **Farmer Information**: View farmer details and contact information

### 📊 Supply Chain Traceability
- **Automatic Event Logging**: Harvest, quality checks, and status changes
- **Blockchain-Ready**: Event structure designed for blockchain integration
- **Complete History**: Full audit trail of product lifecycle
- **Timestamp Tracking**: Precise tracking of all supply chain events

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17+
- **Database**: MySQL 8.0
- **ORM**: Hibernate/JPA
- **Security**: Spring Security with JWT
- **Build Tool**: Maven

### Key Dependencies
- Spring Data JPA
- Spring Web
- Spring Security
- MySQL Connector
- Lombok
- JWT (JSON Web Tokens)
- BCrypt Password Encoder

## 📁 Project Structure

```
FarmchainX/
├── src/
│   ├── main/
│   │   ├── java/com/vidhan/FarmchainX/
│   │   │   ├── config/          # Security & JWT configuration
│   │   │   ├── controller/      # REST API endpoints
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA Entities
│   │   │   ├── repository/      # Database repositories
│   │   │   ├── service/         # Business logic
│   │   │   └── util/            # Utility classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── API_REFERENCE.md             # Complete API documentation
├── POSTMAN_TESTING_GUIDE.md     # Testing guide with examples
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/FarmchainX.git
cd FarmchainX
```

2. **Configure Database**

Create a MySQL database:
```sql
CREATE DATABASE farmchainx_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/farmchainx_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build the project**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Farmer Endpoints
- `POST /api/farmer/products` - Create product
- `GET /api/farmer/products` - Get all farmer's products
- `GET /api/farmer/products/{id}` - Get product by ID
- `PUT /api/farmer/products/{id}` - Update product
- `PUT /api/farmer/products/{id}/status` - Update product status
- `POST /api/farmer/products/{id}/images` - Add product images
- `DELETE /api/farmer/products/{id}` - Delete product

### Consumer Endpoints
- `GET /api/consumer/products` - Browse all products (with filters)
- `GET /api/consumer/products/{id}` - Get product details
- `GET /api/consumer/products/{id}/journey` - Get product journey
- `GET /api/consumer/trace/{batchId}` - Trace by QR code

For complete API documentation, see [API_REFERENCE.md](API_REFERENCE.md)

## 🧪 Testing

### Using Postman

1. Import the Postman collection (see [POSTMAN_TESTING_GUIDE.md](POSTMAN_TESTING_GUIDE.md))
2. Set base URL: `http://localhost:8080`
3. Follow the testing guide for step-by-step testing

### Sample Test Flow

1. **Register a Farmer**
```json
POST /api/auth/register
{
  "name": "Ramesh Kumar",
  "email": "ramesh@farm.com",
  "password": "farmer123",
  "role": "FARMER",
  "phone": "+91-9876543210",
  "address": "Village Rampur, Meerut, UP"
}
```

2. **Create a Product**
```json
POST /api/farmer/products
Headers: User-Id: {farmer-id}
{
  "productName": "Organic Tomatoes",
  "cropType": "Vegetables",
  "quantity": 500,
  "price": 80.0,
  "unit": "kg",
  "organic": true,
  "harvestDate": "2024-12-20"
}
```

3. **View Product Journey**
```
GET /api/consumer/products/{product-id}/journey
```

## 🔐 Security

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **Role-Based Access**: Different permissions for different user roles
- **CORS Enabled**: Configured for frontend integration

## 🌟 Key Features Implementation

### Automatic Event Creation
- **Harvest Event**: Created automatically when product is added
- **Quality Check**: Auto-generated for organic products
- **Status Changes**: Tracked automatically on status updates

### Smart Search
- Searches both product name and crop type
- Case-insensitive search
- Combined results without duplicates

### Organic Filtering
- Boolean-based filtering (true/false)
- Compatible with database bit/tinyint storage

## 📊 Database Schema

### Main Entities
- **User**: Farmers, Consumers, Distributors, Retailers
- **Product**: Agricultural products with complete details
- **SupplyChainEvent**: Traceability events
- **Order**: Consumer orders
- **OrderItem**: Individual order items
- **Inventory**: Stock management
- **Notification**: User notifications
- **Review**: Product reviews
- **Advisory**: Farmer advisory system

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Vidhan** - Initial work

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- MySQL for reliable database management
- All contributors and testers

## 📞 Support

For support, email your-email@example.com or open an issue in the GitHub repository.

## 🔮 Future Enhancements

- [ ] Blockchain integration for immutable records
- [ ] IoT sensor integration for real-time monitoring
- [ ] Mobile application (Android/iOS)
- [ ] Advanced analytics dashboard
- [ ] Multi-language support
- [ ] Payment gateway integration
- [ ] AI-based quality prediction
- [ ] Weather integration for farming insights

---

**Made with ❤️ for sustainable agriculture and transparent supply chains**
