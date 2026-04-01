
# 硅谷小智 (Guigu Xiaozhi) - AI 智能伴诊助手

<p align="center">
<img src="https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg" alt="Spring Boot 3.2">
<img src="https://img.shields.io/badge/LangChain4j-1.0.0--beta3-orange.svg" alt="LangChain4j">
<img src="https://img.shields.io/badge/Vue-3.0-blue.svg" alt="Vue 3">
</p>

[**中文版**](https://www.google.com/search?q=%23%E4%B8%AD%E6%96%87%E7%89%88) | [**English Version**](https://www.google.com/search?q=%23english-version)

---

## 中文版

### 1. 项目概述

**硅谷小智**是一款基于 **LangChain4j** 框架构建的智能 AI 伴诊助手。本项目专为北京协和医院设计，旨在通过人工智能技术提升患者就医体验，降低医院接诊压力。

项目融合了大语言模型（LLM）、检索增强生成（RAG）及工具调用（Function Calling）等前沿技术，实现了从智能问诊到预约挂号的一站式线上医疗服务。

### 2. 核心功能

* **智能在线问诊**：基于临床实践提供病因分析、治疗方案建议及就医时机判断。
* **AI 分导诊**：根据患者病情描述，智能推荐最合适的就医科室。
* **智能预约挂号**：通过自然语言对话查询号源，并自动收集信息完成预约，支持防重复预约校验。
* **取消预约**：支持通过对话快速撤销已有预约记录，系统自动同步数据库。
* **多轮对话记忆**：利用 MongoDB 持久化聊天记忆（最多保留 20 条），支持上下文连贯并实现用户间记忆隔离。

### 3. 技术架构

* **后端框架**：Spring Boot 3.2 + WebFlux（支持流式响应 Flux<String>）。
* **AI 核心**：LangChain4j 1.0.0-beta3（负责 AI Service、RAG 及 Tool 调用）。
* **前端框架**：Vue 3 + Element Plus（基于 SSE 实现打字机流式输出效果）。
* **存储方案**：
  * **MySQL**：业务数据库，存储预约挂号记录。
  * **MongoDB**：聊天历史持久化存储。
  * **Pinecone**：向量数据库，用于存储医院知识库并支持 RAG 检索。
* **模型集成**：支持阿里云百炼（Qwen）、DeepSeek-V3 以及本地 Ollama (DeepSeek-R1)。

### 4. 快速启动

1. **环境准备**：JDK 17+、Node.js 18+、MySQL 8.0 及 MongoDB 6.0+。
2. **配置环境变量**：设置 `DASH_SCOPE_API_KEY` 和 `PINECONE_API_KEY`。
3. **知识库初始化**：运行 `EmbeddingTest` 将文档向量化存入 Pinecone。
4. **启动服务**：
   * **后端**：执行 `mvn spring-boot:run` (端口 8080)。
   * **前端**：执行 `npm install && npm run dev` (端口 5173)。

---

## English Version

### 1. Project Overview

**Guigu Xiaozhi** is an intelligent AI medical assistant built on the **LangChain4j** framework. Designed for Peking Union Medical College Hospital, it leverages AI to enhance patient experiences and alleviate hospital administrative pressure.

The project integrates Large Language Models (LLM), Retrieval-Augmented Generation (RAG), and Function Calling to provide a one-stop online service for medical consultation and appointment booking.

### 2. Key Features

* **AI Medical Consultation**: Provides cause analysis, treatment suggestions, and medical timing advice based on clinical practices.
* **Intelligent Triage**: Recommends the most suitable department based on symptom descriptions.
* **Smart Appointment Booking**: Queries availability and completes the booking process via natural language, featuring duplicate booking prevention.
* **Appointment Cancellation**: Quickly cancels existing appointments through simple dialogue.
* **Contextual Memory**: Uses MongoDB to persist up to 20 messages per session, ensuring contextual continuity and user isolation.

### 3. Technical Architecture

* **Backend**: Spring Boot 3.2 + WebFlux (supporting streaming responses via Flux<String>).
* **AI Framework**: LangChain4j 1.0.0-beta3 for AI Services, RAG, and Tool calling.
* **Frontend**: Vue 3 + Element Plus + Vite, utilizing SSE (Server-Sent Events) for real-time rendering.
* **Storage**:
  * **MySQL**: Stores business data and appointment records.
  * **MongoDB**: Persistent storage for chat history.
  * **Pinecone**: Vector database for RAG knowledge retrieval.
* **Models**: Supports Alibaba Cloud (Qwen), DeepSeek-V3, and local Ollama (DeepSeek-R1).

### 4. Quick Start

1. **Requirements**: JDK 17+, Node.js 18+, MySQL 8.0, and MongoDB 6.0+.
2. **Environment Variables**: Configure `DASH_SCOPE_API_KEY` and `PINECONE_API_KEY`.
3. **Knowledge Initialization**: Run `EmbeddingTest` to vectorize documents into Pinecone.
4. **Execution**:
   * **Backend**: Run `mvn spring-boot:run` on port 8080.
   * **Frontend**: Run `npm install && npm run dev` on port 5173.

---

*Last Updated: 2026.03.20*
