# 💹 Financial Alerts System – Reactive Case Study

### 🧪 Project Status

[![CI](https://img.shields.io/badge/CI-passing-brightgreen)]()
[![Total Coverage](https://img.shields.io/badge/Total_Coverage-91.3%25-brightgreen)]()
[![New Coverage](https://img.shields.io/badge/New_Coverage-100%25-brightgreen)]()
[![Quality Gate](https://img.shields.io/badge/Quality_Gate-passed-brightgreen)]()
[![Dependabot](https://img.shields.io/badge/Dependabot-active-blue)]()
[![Vulnerabilities](https://img.shields.io/badge/Vulnerabilities-0-brightgreen)]()
[![Release](https://img.shields.io/badge/Release-v1.0.0-blue)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)]()

---

## 📘 Description

This project implements a **real-time financial alerts system**, built as an **educational and professional case study**.
The backend is built with **Java 21 and Spring Boot WebFlux**, following **hexagonal architecture** principles, and the frontend is a SPA built with **React + Vite**.

The solution supports:
- Querying current and historical crypto asset prices.
- Registering custom price alerts.
- Receiving real-time notifications via streaming (SSE).

---

## ⚙️ Backend – Technical Roadmap

| Step | Main Feature                                               | Key Concepts                                 |
|------|--------------------------------------------------------------|-----------------------------------------------|
| 1️⃣   | `GET /assets/price` – Current price                        | WebClient, Mono, ports                        |
| 2️⃣   | `GET /assets/history` – In-memory history                  | Flux, reactive storage, transformation        |
| 3️⃣   | `POST /alerts` – Alert registration                        | Validation, DTOs, hexagonal domain            |
| 4️⃣   | `GET /alerts/stream` – SSE emission                         | Interval, filters, backpressure               |
| 5️⃣   | Resilience and error handling                               | timeout, retryWhen, global handling           |
| 6️⃣   | Tests and integration testing                               | StepVerifier, WebClient, WireMock             |
| 7️⃣   | Observability and cache                                    | doOnNext, Micrometer, logging                 |
| 8️⃣   | (Optional) Webhooks or Telegram                             | Outbound port with strategy pattern           |
| 9️⃣   | (Optional) Reactive security with JWT or Keycloak           | Filters + authorization                       |

---

## 🧑‍💻 Frontend – React Roadmap

| Step        | Feature                                          | Key Tools                                 |
|-------------|---------------------------------------------------|----------------------------------------------|
| 1️⃣          | Set up base project with Vite + TypeScript        | Vite, React, TS                              |
| 2️⃣          | Display current price (`GET /assets/price`)       | Axios, Hook, Card                            |
| 3️⃣          | Display history chart (`/assets/history`)         | Recharts, dynamic charting                   |
| 4️⃣          | Alert creation form                                | Formik, Yup, validation                      |
| 5️⃣          | Display active alerts                              | Server-Sent Events (SSE), streaming          |

## ☕ Donations

If this project or the book was useful to you, you can support its development with a donation. Your support helps maintain and improve this kind of educational content.

[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://buymeacoffee.com/codefuel)
[![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)](https://www.paypal.com/donate/?hosted_button_id=4TYGJ5S8CLX8J)

- ☕ [Buy Me a Coffee](https://buymeacoffee.com/codefuel)
- 💳 [PayPal Donate](https://www.paypal.com/donate/?hosted_button_id=4TYGJ5S8CLX8J)

---
