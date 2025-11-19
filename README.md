# FarmChainX 🌾

**AI-Driven Agricultural Traceability Network**

A comprehensive blockchain-inspired traceability system for agricultural supply chains, enabling transparent tracking from farm to consumer with AI-powered insights.

## 🚀 Features

### Core Functionality
- **Complete Supply Chain Tracking**: Farm → Distribution → Retail → Consumer
- **Role-Based Access Control**: Farmer, Distributor, Retailer, Consumer, Admin
- **Product Journey Visualization**: Real-time tracking of product movement
- **Quality Assurance**: Audit trails and quality checkpoints
- **Price Management**: Dynamic pricing across the supply chain

### Advanced Features
- **AI-Powered Analytics**: Predictive insights and trend analysis
- **Secure Authentication**: JWT-based security with BCrypt encryption
- **RESTful API**: Comprehensive API for all stakeholders
- **Data Integrity**: Immutable audit trails for transparency
- **Multi-Category Support**: Various agricultural products and practices

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Database**: MySQL with JPA/Hibernate
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Password Encryption**: BCrypt

### Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- MySQL Connector
- Lombok
- BCrypt

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd FarmchainX
```

### 2. Database Setup
```sql
CREATE DATABASE farmchainx;
CREATE USER 'farmchainx_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON farmchainx.* TO 'farmchainx_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure Application Properties
Create `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/farmchainx
spring.datasource.username=farmchainx_user
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080

# JWT Configuration
app.jwt.secret=your-jwt-secret-key
app.jwt.expiration=86400000
```

### 4. Build and Run
```bash
# Using Maven Wrapper
./mvnw clean install
./mvnw spring-boot:run

# Or using Maven directly
mvn clean install
mvn spring-boot:run
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/vidhan/FarmchainX/
│   │   ├── config/          # Security and configuration
│   │   ├── controller/      # REST API endpoints
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA Entities
│   │   ├── repository/     # Data Access Layer
│   │   ├── service/        # Business Logic
│   │   └── FarmchainXApplication.java
│   └── resources/
│       ├── application.properties
│       └── static/
└── test/                   # Unit and Integration Tests
```

## 🔐 API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/signin` - User login

### Farmer Operations
- `POST /api/farmer/products` - Add new product
- `GET /api/farmer/products` - Get farmer's products
- `PUT /api/farmer/products/{id}` - Update product
- `POST /api/farmer/products/{id}/transfer` - Transfer to distributor

### Distributor Operations
- `GET /api/distributor/products` - Get received products
- `POST /api/distributor/products/{id}/transfer` - Transfer to retailer
- `PUT /api/distributor/products/{id}/audit` - Add audit information

### Retailer Operations
- `GET /api/retailer/products` - Get inventory
- `POST /api/retailer/products/{id}/sale` - Record sale
- `PUT /api/retailer/products/{id}/price` - Update price

### Consumer Operations
- `GET /api/consumer/products/search` - Search products
- `GET /api/consumer/products/{id}/journey` - View product journey

### Admin Operations
- `GET /api/admin/stats` - System statistics
- `GET /api/admin/users` - User management
- `POST /api/admin/users` - Create user accounts

## 👥 User Roles

### 🌱 Farmer
- Add products with farming details
- Transfer products to distributors
- Track product status and journey

### 🚛 Distributor
- Receive products from farmers
- Add quality audit information
- Transfer products to retailers

### 🏪 Retailer
- Manage inventory from distributors
- Set retail prices
- Record consumer sales

### 👤 Consumer
- Search available products
- View complete product journey
- Access transparency information

### 🔧 Admin
- System oversight and statistics
- User management
- Data integrity monitoring

## 🌟 Key Features Explained

### Product Journey Tracking
Every product maintains a complete audit trail from creation to final sale, including:
- Farming practices and location
- Quality checkpoints
- Price changes
- Ownership transfers
- Timestamps and locations

### Security Features
- JWT-based authentication
- Role-based access control
- Password encryption with BCrypt
- Secure API endpoints

### Data Integrity
- Immutable audit trails
- Comprehensive logging
- Validation at every step
- Error handling and recovery

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=FarmchainXApplicationTests
```

## 📚 Development Guidelines

### Code Style
- Follow Java naming conventions
- Use Lombok for boilerplate code
- Implement proper error handling
- Write comprehensive tests

### Database Design
- Use appropriate JPA annotations
- Implement proper relationships
- Consider performance implications
- Maintain data consistency

## 🚀 Deployment

### Development
```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### Production
```bash
./mvnw clean package
java -jar target/FarmchainX-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

## 🔮 Future Enhancements

- Blockchain integration for immutable records
- IoT sensor integration
- Mobile application
- Advanced analytics dashboard
- Multi-language support
- API rate limiting
- Caching mechanisms
- Microservices architecture

---

**FarmChainX** - Bringing transparency and trust to agricultural supply chains through technology.
