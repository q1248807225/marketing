# Marketing Activities Platform

This repository contains a simplified example of a marketing activities platform using a **Spring Boot** backend with **Flowable** for process orchestration and a **Vue 3** frontend. The goal is to show how process orchestration can reduce development time when implementing activities such as lotteries, tasks, fission and single order rebates.

## Structure

- `backend/` – Spring Boot project with Flowable BPMN process.
- `frontend/` – Vue project for the management and client interfaces.

## Backend

The backend exposes REST endpoints to deploy and start processes. BPMN definitions can be uploaded from the management interface rather than being packaged in the application. Activity configurations and participation records are stored in an embedded H2 database so multiple activities can be managed at once.

Example to deploy a process:

```bash
curl -X POST \
  -H "Content-Type: text/plain" \
  --data-binary @yourProcess.bpmn20.xml \
  "http://localhost:8080/api/management/process?activityType=lottery"
```

Then start the activity:

```bash
curl -X POST "http://localhost:8080/api/management/start?activityType=lottery"
```

Clients can also join an activity directly:

```bash
curl -X POST "http://localhost:8080/api/client/start?activityType=lottery&userId=42"
```

### Lottery wheel

Configure prizes from the management interface:

```bash
curl -X POST "http://localhost:8080/api/management/wheel/prize?activityType=lottery&name=Car&probability=0.1"
```

Clients view the wheel and draw:

```bash
curl http://localhost:8080/api/client/wheel?activityType=lottery
curl -X POST "http://localhost:8080/api/client/draw?activityType=lottery&userId=42"
curl http://localhost:8080/api/client/draw-records?activityType=lottery&userId=42
```

### Rewards

Configure reward items and issue them to users:

```bash
curl -X POST "http://localhost:8080/api/management/reward?rewardType=COUPON&name=10%25off&detail=code123"
curl http://localhost:8080/api/management/rewards?rewardType=COUPON
curl -X POST "http://localhost:8080/api/client/reward/issue?rewardId=1&userId=42"
curl http://localhost:8080/api/client/reward/records?userId=42
```

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

The frontend uses Vue 3 with Vue Router and includes a small management page
to upload BPMN processes and a client page to interact with running tasks.
Install dependencies and start the dev server:

```bash
cd frontend
npm install
npm run serve
```

Open `http://localhost:8080` in the browser to reach the management and client
pages. The management page lets you deploy and start activities, while the
client page shows tasks for a selected process instance. You can list configured
activities via `GET /api/management/activities`.
