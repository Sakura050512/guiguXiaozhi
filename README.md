---

# 硅谷小智 (Guigu Xiaozhi) - AI 智能伴诊助手

## 中文版

### 1. 项目简介
[cite_start]**硅谷小智**是一款基于 **LangChain4j** 框架构建的智能 AI 伴诊助手 [cite: 7][cite_start]。该项目专为医疗场景（如北京协和医院）设计，通过大语言模型 (LLM)、检索增强生成 (RAG) 和工具调用 (Function Calling) 等技术，为患者提供从健康咨询到挂号预约的一站式服务 [cite: 7, 8]。

### 2. 核心功能
* [cite_start]**智能在线问诊**：基于临床实践提供病因分析、治疗方案建议及就医时机判断 [cite: 10, 11, 14]。
* [cite_start]**AI 分导诊**：根据病情描述智能推荐最合适的科室 [cite: 15, 16]。
* [cite_start]**智能预约挂号**：支持自然语言查询号源并完成预约，具备防重复预约校验功能 [cite: 17, 18, 21]。
* [cite_start]**取消预约**：通过对话快速撤销已有挂号记录 [cite: 22, 23]。
* [cite_start]**多轮对话记忆**：利用 MongoDB 实现最近 20 条消息的持久化记忆，支持多用户会话隔离 [cite: 24, 25]。

### 3. 技术架构
* [cite_start]**后端**：Spring Boot 3.2, LangChain4j 1.0.0-beta3, MyBatis-Plus [cite: 4, 29]。
* [cite_start]**前端**：Vue 3, Element Plus, Vite, SSE (Server-Sent Events) 流式渲染 [cite: 4, 28, 29]。
* [cite_start]**AI/大模型**：阿里云百炼 (Qwen), DeepSeek-V3, Ollama (本地部署 DeepSeek-R1) [cite: 29, 32]。
* **数据库**：
    * [cite_start]**MySQL**：存储业务预约数据 [cite: 29, 74]。
    * [cite_start]**MongoDB**：持久化存储多轮对话历史 [cite: 29, 74]。
    * [cite_start]**Pinecone**：向量数据库，用于 RAG 知识检索 [cite: 29, 32]。

### 4. 快速启动
1.  [cite_start]**环境要求**：JDK 17+, Node.js 18+, MySQL 8.0, MongoDB 6.0+ [cite: 55, 56, 57, 58, 59]。
2.  [cite_start]**配置**：设置 `DASH_SCOPE_API_KEY` 和 `PINECONE_API_KEY` 环境变量 [cite: 63]。
3.  [cite_start]**初始化**：运行 `EmbeddingTest` 将 Markdown/PDF 医院知识库写入 Pinecone [cite: 65]。
4.  [cite_start]**启动后端**：执行 `mvn spring-boot:run`（默认端口 8080）[cite: 66]。
5.  [cite_start]**启动前端**：执行 `npm install && npm run dev`（默认端口 5173）[cite: 67]。

---

## English Version

### 1. Project Overview
[cite_start]**Guigu Xiaozhi** is an intelligent AI medical assistant built on the **LangChain4j** framework[cite: 7]. [cite_start]Designed for clinical environments like Peking Union Medical College Hospital, it leverages Large Language Models (LLM), Retrieval-Augmented Generation (RAG), and Function Calling to enhance patient experience and reduce hospital workload[cite: 7, 8].

### 2. Key Features
* [cite_start]**AI Medical Consultation**: Provides cause analysis, treatment suggestions, and medical advice based on professional clinical practices[cite: 10, 11, 14].
* [cite_start]**Intelligent Triage**: Recommends the appropriate department based on user-described symptoms[cite: 15, 16].
* [cite_start]**Smart Appointment Booking**: Facilitates department queries and appointment scheduling via natural language, including duplicate booking prevention[cite: 17, 18, 21].
* [cite_start]**Appointment Cancellation**: Allows users to cancel existing bookings through simple dialogue[cite: 22, 23].
* [cite_start]**Contextual Memory**: Maintains up to 20 recent messages per session using MongoDB for persistent, isolated multi-user conversations[cite: 24, 25].

### 3. Tech Stack
* [cite_start]**Backend**: Spring Boot 3.2, LangChain4j 1.0.0-beta3, MyBatis-Plus[cite: 4, 29].
* [cite_start]**Frontend**: Vue 3, Element Plus, Vite, SSE for real-time streaming[cite: 4, 28, 29].
* [cite_start]**AI Components**: Alibaba Cloud Bailian (Qwen), DeepSeek-V3, Ollama (Local DeepSeek-R1)[cite: 29, 32].
* **Storage**:
    * [cite_start]**MySQL**: Business logic and appointment records[cite: 29, 74].
    * [cite_start]**MongoDB**: Chat history persistence[cite: 29, 74].
    * [cite_start]**Pinecone**: Vector database for RAG knowledge retrieval[cite: 29, 32].

### 4. Quick Start
1.  [cite_start]**Requirements**: JDK 17+, Node.js 18+, MySQL 8.0, MongoDB 6.0+[cite: 55, 56, 57, 58, 59].
2.  [cite_start]**Configuration**: Set environment variables `DASH_SCOPE_API_KEY` and `PINECONE_API_KEY`[cite: 63].
3.  [cite_start]**Initialization**: Run `EmbeddingTest` to vectorize medical knowledge into Pinecone[cite: 65].
4.  [cite_start]**Backend**: Run `mvn spring-boot:run` (Port 8080)[cite: 66].
5.  [cite_start]**Frontend**: Run `npm install && npm run dev` (Port 5173)[cite: 67].
