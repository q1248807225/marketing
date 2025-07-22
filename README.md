# Marketing Activities Platform

This repository contains a simplified example of a marketing activities platform using a **Spring Boot** backend with **Flowable** for process orchestration and a **Vue 3** frontend. The goal is to show how process orchestration can reduce development time when implementing activities such as lotteries, tasks, fission and single order rebates.

## Structure

- `backend/` – Spring Boot project with Flowable BPMN process.
- `frontend/` – Vue project for the management and client interfaces.

## Backend

The backend exposes REST endpoints to start activities and handle user tasks. It provides two example BPMN processes:

- `marketing.bpmn20.xml` – a minimal flow issuing a coupon after user interaction.
- `lottery.bpmn20.xml` – a lottery flow where a participant may win a coupon.

To build the backend:

```bash
cd backend
mvn package
```

Run the application:

```bash
java -jar target/marketing-backend-0.0.1-SNAPSHOT.jar
```

## Frontend

The frontend uses Vue 3. Install dependencies and start the dev server:

```bash
cd frontend
npm install
npm run serve
```

This skeleton can be extended with management interfaces to configure campaigns and client pages for users to participate.
