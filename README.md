# 💹 Sistema de Alertas Financieras – Caso de Estudio Reactivo

### 🧪 Estado del Proyecto

[![CI](https://img.shields.io/badge/CI-passing-brightgreen)]()
[![Total Coverage](https://img.shields.io/badge/Total_Coverage-91.3%25-brightgreen)]()
[![New Coverage](https://img.shields.io/badge/New_Coverage-100%25-brightgreen)]()
[![Quality Gate](https://img.shields.io/badge/Quality_Gate-passed-brightgreen)]()
[![Dependabot](https://img.shields.io/badge/Dependabot-active-blue)]()
[![Vulnerabilities](https://img.shields.io/badge/Vulnerabilities-0-brightgreen)]()
[![Release](https://img.shields.io/badge/Release-v1.0.0-blue)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)]()

---

## 📘 Descripción

Este proyecto implementa un **sistema de alertas financieras en tiempo real**, desarrollado como **caso de estudio educativo y profesional**.  
El backend está construido en **Java 21 con Spring Boot WebFlux**, siguiendo principios de **arquitectura hexagonal**, y el frontend es una SPA desarrollada en **React + Vite**.

La solución permite:
- Consultar precios actuales e históricos de criptoactivos.
- Registrar alertas de precio personalizadas.
- Recibir notificaciones en tiempo real vía streaming (SSE).

---

## ⚙️ Backend – Roadmap técnico

| Paso | Funcionalidad principal                                    | Conceptos clave                            |
|------|------------------------------------------------------------|---------------------------------------------|
| 1️⃣   | `GET /assets/price` – Precio actual                       | WebClient, Mono, puertos                    |
| 2️⃣   | `GET /assets/history` – Historial in-memory               | Flux, almacenamiento reactivo, transformación |
| 3️⃣   | `POST /alerts` – Registro de alertas                      | Validación, DTOs, dominio hexagonal         |
| 4️⃣   | `GET /alerts/stream` – Emisión por SSE                    | Intervalo, filtros, backpressure            |
| 5️⃣   | Resiliencia y errores                                     | timeout, retryWhen, manejo global           |
| 6️⃣   | Tests y pruebas integradas                                | StepVerifier, WebClient, WireMock           |
| 7️⃣   | Observabilidad y cache                                    | doOnNext, Micrometer, logging               |
| 8️⃣   | (Opcional) Webhooks o Telegram                            | Puerto de salida con estrategia             |
| 9️⃣   | (Opcional) Seguridad reactiva con JWT o Keycloak          | Filtros + autorización                      |

---

## 🧑‍💻 Frontend – Roadmap React

| Paso        | Funcionalidad                                  | Herramientas clave                      |
|-------------|------------------------------------------------|------------------------------------------|
| 1️⃣          | Crear base con Vite + TypeScript               | Vite, React, TS                          |
| 2️⃣          | Mostrar precio actual (`GET /assets/price`)    | Axios, Hook, Card                        |
| 3️⃣          | Mostrar gráfico de historial (`/assets/history`)| Recharts, graficación dinámica          |
| 4️⃣          | Formulario de alertas                          | Formik, Yup, validaciones                |
| 5️⃣          | Visualizar alertas activas                     | Server-Sent Events (SSE), streaming      |

## ☕ Donaciones

Si este proyecto o el libro te fueron útiles, podés apoyar el desarrollo con una donación. Tu apoyo ayuda a mantener y mejorar este tipo de contenido educativo.

[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://buymeacoffee.com/codefuel)
[![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)](https://www.paypal.com/donate/?hosted_button_id=4TYGJ5S8CLX8J)

- ☕ [Buy Me a Coffee](https://buymeacoffee.com/codefuel)
- 💳 [PayPal Donate](https://www.paypal.com/donate/?hosted_button_id=4TYGJ5S8CLX8J)

---
