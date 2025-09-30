![Screenshot 2025-06-12 175351](https://github.com/user-attachments/assets/1c32102a-fca5-46b9-b89e-eacec6979ac7)# 🛡 Spring Boot JWT Authentication & Authorization

This project demonstrates a complete *JWT-based authentication* and *authorization* system using Spring Boot and Spring Security. It supports:

- ✅ User registration & login
- ✅ JWT token generation & validation
- ✅ Role-based access control (hasRole('ADMIN'))
- ✅ Ownership checks using @AuthenticationPrincipal

---

## 🔁 Authentication Flow Diagram

![Authentication Flow]

![Screenshot 2025-06-12 175351](https://github.com/user-attachments/assets/8b2b2f1c-5457-45cb-9404-d33d71c23ca2)




### 🧠 How It Works

1. *User sends a request* (e.g., login or protected API).
2. The *Security Filter Chain* runs and triggers our custom JwtAuthenticationFilter.
3. The filter extracts the JWT token and:
    - Uses JwtService to extract the username (sub)
    - Loads the user from the database using UserDetailsService
    - Validates the token
4. If valid, Spring creates a UsernamePasswordAuthenticationToken and sets it in SecurityContextHolder.
5. Spring now sees this request as authenticated.
6. Access to protected routes is controlled:
    - 🔐 With @PreAuthorize("hasRole('ADMIN')") for role checks
    - 🔐 With @AuthenticationPrincipal for user-specific checks (e.g., blog ownership)

---

### 🏗 Technologies Used

- Spring Boot
- Spring Security
- JWT (JJWT)
- Lombok
- H2/PostgreSQL
- REST APIs

---

### 📌 Key Concepts

- *SecurityContextHolder* holds the current authenticated user per request
- *UserDetailsService* loads user info from DB
- *AuthenticationManager* delegates to providers like DaoAuthenticationProvider
- *PasswordEncoder* encrypts and verifies passwords
- *JWT Filter* checks tokens on every request

---
