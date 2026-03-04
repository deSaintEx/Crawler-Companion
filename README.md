# Crawler Companion
*A full‑stack application for organizing Dungeons & Dragons campaigns, characters, storylines, and parties.*

---

## Features
- User authentication and session management  
- Create, view, update, and delete campaigns  
- Character creation with race/class details  
- Storyline and plot organization  
- Party management and cross‑entity relationships  
- Responsive, component‑based UI built with React  
- Semantic markup and accessibility‑focused layouts  

---

## Tech Stack
**Frontend:** React, JavaScript, HTML, CSS  
**Backend:** Java, Spring Boot, RESTful API  
**Database:** PostgreSQL  
**Tools:** IntelliJ, VS Code, Postman, pgAdmin, Git/GitHub  

---

## Project Structure
```
crawler-companion/
  client/        # React frontend
  server/        # Spring Boot backend
  README.md
```

---

## Running the Project Locally

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/crawler-companion.git
cd crawler-companion
```

### 2. Start the backend
```bash
cd server
./mvnw spring-boot:run
```
Backend runs on: **http://localhost:8080**

### 3. Start the frontend
```bash
cd client
npm install
npm start
```
Frontend runs on: **http://localhost:3000**

---

## API Overview
The backend exposes REST endpoints for:

- `/campaigns`
- `/characters`
- `/plots`
- `/parties`
- `/users`

Each endpoint supports standard CRUD operations.  
Authentication is handled via session‑based login.

---

## Database Schema
The PostgreSQL schema includes:

- **users** — authentication + ownership  
- **campaigns** — top‑level container  
- **characters** — linked to campaigns  
- **plots** — storylines and notes  
- **parties** — groupings of characters  

Seed data is included for quick setup.

---

## UI and Design Notes
- Built with reusable React components  
- Pattern‑driven layout structure  
- Semantic HTML for screen‑reader compatibility  
- Responsive flexbox/grid layouts  
- Accessibility and inclusion informed by nonprofit work  

---

## Future Improvements
- Add drag‑and‑drop for organizing storylines  
- Expand character sheets with more D&D attributes  
- Add dark mode and theme toggling  
- Integrate design‑system tooling (Storybook, design tokens, Lit components)  

---

## Author
**Benjamin** — Full‑Stack Developer  
Focused on accessible UI, reusable components, and collaborative design.
