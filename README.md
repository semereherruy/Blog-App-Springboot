# BlogApp Backend

A simple, RESTful backend for a blog website built with Spring Boot.

## 🚀 Features

- **User Management**  
  - Register & login  
  - First name, last name, profile picture, email, phone, bio  

- **Blog Posts**  
  - Create, edit & delete your own posts  
  - Title, content, image, created/updated timestamps  
  - Category & tags  
  - Comments, likes/dislikes  

- **Browsing & Discovery**  
  - List all posts  
  - Search by keyword  
  - Filter by category  
  - Post detail view with comments & reactions  

## 📅 Roadmap

- **Apr 3–5**  User Management  
- **Apr 6–9**  Blog Post CRUD  
- **Apr 10–13** Home & Post Pages

## 🛠️ Tech Stack

- Java 17 + Spring Boot  
- Spring Data JPA (Hibernate)  
- Jakarta Validation  
- Lombok  
- MySQL (or your preferred RDBMS)  
- Spring Security (JWT-ready)

## 🔗 Endpoints Overview

- **Users**  
  - `POST /api/users/signup`  
  - `POST /api/users/login`  
  - `PUT /api/users/update-profile/{id}`  
  - `POST /api/users/forgot-password`  
  - `POST /api/users/verify-reset-code`  
  - `PUT /api/users/reset-password`

- **Blog Posts**  
  - `GET /api/blog/posts`  
  - `GET /api/blog/posts/{id}`  
  - `POST /api/blog/posts/create`  
  - `PUT /api/blog/posts/{id}`  
  - `DELETE /api/blog/posts/{id}`  

- **Comments & Reactions**  
  - `POST /api/blog/posts/{id}/comments`  
  - `POST /api/blog/reactions/toggle?userId=&postId=`  
  - `GET /api/blog/reactions/{postId}/likes`

> _More endpoints & details in the codebase._

## 🚀 Getting Started

1. Clone this repo  
2. Configure your database in `application.yml`  
3. `mvn spring-boot:run`  

Enjoy building on top of this foundation! 🎉  
