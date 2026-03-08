<template>
  <div class="app-wrapper">
    <aside class="sidebar left-sidebar">
      <div class="sidebar-top">
        <div class="brand-header">
          <div class="logo-outer-circle">
            <div class="logo-inner-box">
              <img src="@/assets/logo.png" alt="硅谷小智" class="main-logo" />
            </div>
          </div>
          <h1 class="hospital-name">硅谷小智</h1>
          <div class="brand-tag">
            <span class="pulsing-dot"></span>
            北京协和医院 | 智能伴诊助手
          </div>
        </div>

        <div class="action-section">
          <el-button class="new-chat-btn-premium" @click="newChat">
            <i class="fa-solid fa-plus-circle"></i>
            <span>&nbsp;开启新会话</span>
          </el-button>
        </div>

        <nav class="feature-guide">
          <div class="guide-item active">
            <i class="fa-solid fa-comments"></i> 智能咨询
          </div>
          <div class="guide-item">
            <i class="fa-solid fa-book-medical"></i> 就医指南
          </div>
          <div class="guide-item">
            <i class="fa-solid fa-calendar-check"></i> 预约挂号
          </div>
        </nav>
      </div>

      <div class="sidebar-bottom">
        <div class="ai-status-card">
          <p class="status-title">系统状态</p>
          <div class="status-row">
            <span>AI 核心</span>
            <span class="status-tag">在线</span>
          </div>
          <div class="status-row">
            <span>响应延迟</span>
            <span class="status-tag">35ms</span>
          </div>
        </div>
      </div>
    </aside>

    <main class="main-stage">
      <header class="mobile-header">
        <div class="mobile-brand">
          <img src="@/assets/logo.png" class="mini-logo" />
          <span>硅谷小智 · 协和助手</span>
        </div>
        <el-button size="small" circle @click="newChat" class="mobile-reset-btn">
          <i class="fa-solid fa-rotate-right"></i>
        </el-button>
      </header>

      <div class="chat-viewport">
        <div class="message-list" ref="messaggListRef">
          <div class="welcome-hero" v-if="messages.length < 2">
            <div class="hero-icon">
              <i class="fa-solid fa-shield-medical"></i>
            </div>
            <h2>北京协和医院欢迎您</h2>
            <p>我是您的全天候医疗顾问。<br>您可以询问症状初筛、科室选择或报告解读。</p>
            <div class="quick-tags">
              <span @click="inputMessage = '感冒发烧挂什么科？'">挂号建议</span>
              <span @click="inputMessage = '抽血需要空腹吗？'">采血须知</span>
              <span @click="inputMessage = '怎么查询电子报告？'">报告查询</span>
            </div>
          </div>

          <div
              v-for="(message, index) in messages"
              :key="index"
              :class="['message-row', message.isUser ? 'user-row' : 'bot-row']"
          >
            <div class="avatar-box">
              <div class="avatar-circle">
                <i :class="message.isUser ? 'fa-solid fa-user' : 'fa-solid fa-robot'"></i>
              </div>
            </div>
            <div class="message-container">
              <div class="message-bubble">
                <div class="content" v-html="message.content"></div>
                <div class="typing-indicator" v-if="message.isThinking || message.isTyping">
                  <span class="dot"></span><span class="dot"></span><span class="dot"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <footer class="input-area">
        <div class="input-panel">
          <el-input
              v-model="inputMessage"
              placeholder="请简洁描述您的不适症状或疑问..."
              @keyup.enter="sendMessage"
              resize="none"
              type="textarea"
              :autosize="{ minRows: 1, maxRows: 4 }"
          ></el-input>
          <el-button
              type="primary"
              class="send-btn-modern"
              :disabled="isSending"
              @click="sendMessage"
          >
            <i class="fa-solid fa-paper-plane"></i>
          </el-button>
        </div>
        <div class="safe-disclaimer">
          <i class="fa-solid fa-circle-exclamation"></i>
          AI 生成内容仅供参考，不作为最终诊断依据
        </div>
      </footer>
    </main>

    <aside class="sidebar right-sidebar">
      <div class="side-card">
        <h3 class="card-title"><i class="fa-solid fa-star"></i> 就医必读</h3>
        <ul class="tip-list">
          <li><strong>实名就医：</strong><br>请携带患者本人身份证件。</li>
          <li><strong>预约号源：</strong><br>App每日16点更新号源。</li>
          <li><strong>核酸要求：</strong><br>请遵循当地最新防疫政策。</li>
        </ul>
      </div>

      <div class="side-card help-card">
        <h3 class="card-title"><i class="fa-solid fa-circle-question"></i> 常见问题</h3>
        <div class="q-item">如何修改挂号信息？</div>
        <div class="q-item">缴费支持哪些方式？</div>
        <div class="q-item">如何打印发票？</div>
      </div>
      <div class="emergency-contact side-card">
        <h3 class="card-title text-danger"><i class="fa-solid fa-phone-volume"></i> 急诊热线</h3>
        <p class="phone-number">010-69156114</p>
        <p class="note">（24小时全天候服务）</p>
      </div>
    </aside>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, nextTick } from 'vue'
import axios from 'axios'

const messaggListRef = ref()
const isSending = ref(false)
const uuid = ref()
const inputMessage = ref('')
const messages = ref([])

onMounted(() => {
  initUUID()
  watch(messages, () => scrollToBottom(), { deep: true })
  hello()
})

const scrollToBottom = async () => {
  await nextTick()
  if (messaggListRef.value) {
    messaggListRef.value.scrollTo({
      top: messaggListRef.value.scrollHeight,
      behavior: 'smooth'
    })
  }
}

const hello = () => {
  sendRequest('你好')
}

const sendMessage = () => {
  if (inputMessage.value.trim() && !isSending.value) {
    sendRequest(inputMessage.value.trim())
    inputMessage.value = ''
  }
}

const sendRequest = (message) => {
  isSending.value = true
  const userMsg = { isUser: true, content: message, isTyping: false, isThinking: false }
  if(messages.value.length > 0) messages.value.push(userMsg)

  const botMsg = { isUser: false, content: '', isTyping: true, isThinking: false }
  messages.value.push(botMsg)
  const lastMsg = messages.value[messages.value.length - 1]
  scrollToBottom()

  axios.post('/api/xiaozhi/chat',
      { memoryId: uuid.value, message },
      {
        responseType: 'text',
        onDownloadProgress: (e) => {
          const fullText = e.event.target.responseText
          lastMsg.content = convertStreamOutput(fullText)
          scrollToBottom()
        },
      }
  )
      .then(() => {
        lastMsg.isTyping = false
        isSending.value = false
      })
      .catch((error) => {
        lastMsg.content = '网络异常，请尝试刷新页面。'
        lastMsg.isTyping = false
        isSending.value = false
      })
}

const initUUID = () => {
  let storedUUID = localStorage.getItem('user_uuid')
  if (!storedUUID) {
    storedUUID = Math.floor(Math.random() * 1000000)
    localStorage.setItem('user_uuid', storedUUID)
  }
  uuid.value = storedUUID
}

const convertStreamOutput = (output) => {
  if (!output) return ''
  return output.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\n/g, '<br>')
}

const newChat = () => {
  localStorage.removeItem('user_uuid')
  window.location.reload()
}
</script>

<style scoped>
/* 颜色系统 */
:root {
  --primary: #005bac;
  --primary-light: #e3f2fd;
  --primary-dark: #004494;
  --secondary: #f0f4f8;
  --bot-bubble: #f4f7f9;
  --user-bubble: #005bac;
  --text-dark: #1a202c;
  --text-muted: #718096;
  --bg-app: #f4f7f6;
  --accent-danger: #d32f2f;
}

.app-wrapper {
  display: flex;
  height: 100vh;
  width: 100vw;
  background-color: var(--bg-app);
  overflow: hidden;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

.sidebar {
  width: 280px;
  background-color: #ffffff;
  border-right: 1px solid #edf2f7;
  display: flex;
  flex-direction: column;
  padding: 30px 20px;
  flex-shrink: 0;
  justify-content: space-between;
}

.brand-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 30px;
}

/* Logo 外部圆环 - 增加呼吸灯和旋转流光效果 */
.logo-outer-circle {
  width: 180px;
  height: 180px;
  background: white;
  border-radius: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 12px 24px rgba(0, 91, 172, 0.1); /* 基础阴影 */
  margin-bottom: 20px;
  border: 1px solid #f0f4f8;
  position: relative;
  overflow: hidden;
  /* 移除了 logo-float 动画，确保容器不动 */
}

/* 增加流光溢彩效果 (伪元素实现) */
.logo-outer-circle::before {
  content: "";
  position: absolute;
  width: 160%; /* 略微扩大，确保旋转时覆盖边缘 */
  height: 160%;
  /* 使用更明显的渐变色带 */
  background: conic-gradient(
      from 0deg,
      transparent 0%,
      rgba(0, 91, 172, 0.2) 25%,
      transparent 50%,
      rgba(0, 91, 172, 0.2) 75%,
      transparent 100%
  );
  animation: rotate-shimmer 6s linear infinite; /* 减慢旋转速度，更优雅 */
  z-index: 0;
}

/* 确保内部容器遮盖流光中心 */
.logo-inner-box {
  position: relative;
  z-index: 1;
  width: 165px;
  height: 165px;
  border-radius: 30px;
  overflow: hidden;
  background: #fff;
}

/* Logo 图片本身的轻微缩放动画 */
.main-logo {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.logo-outer-circle:hover .main-logo {
  transform: scale(1.1); /* 鼠标悬停时放大 */
}

/* --- 定义动画关键帧 --- */

/* 1. 上下浮动效果 */
@keyframes logo-float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

/* 2. 背景旋转流光 */
@keyframes rotate-shimmer {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.hospital-name {
  font-size: 22px;
  font-weight: 800;
  color: var(--primary-dark);
  letter-spacing: 1px;
  margin: 0;
}

.brand-tag {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: var(--text-muted);
  margin-top: 8px;
}

.pulsing-dot {
  width: 8px;
  height: 8px;
  background-color: #48bb78;
  border-radius: 50%;
  animation: pulse 2s infinite;
}

.new-chat-btn-premium {
  width: 100%;
  height: 48px;
  background: linear-gradient(135deg, #005bac 0%, #0072d6 100%) !important;
  color: white !important;
  border: none !important;
  border-radius: 12px !important;
  font-weight: 600 !important;
  font-size: 15px !important;
  box-shadow: 0 4px 15px rgba(0, 91, 172, 0.3) !important;
  transition: all 0.3s ease !important;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.new-chat-btn-premium:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 91, 172, 0.4) !important;
}

.feature-guide {
  margin-top: 10px;
}

.guide-item {
  padding: 12px 15px;
  border-radius: 10px;
  color: var(--text-muted);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 5px;
}

.guide-item:hover {
  background-color: #f7fafc;
  color: var(--primary);
}

.guide-item.active {
  background-color: var(--primary-light);
  color: var(--primary);
  font-weight: 600;
}

.ai-status-card {
  background: #f8fafc;
  padding: 15px;
  border-radius: 12px;
  border: 1px solid #edf2f7;
}

.status-title {
  font-size: 11px;
  text-transform: uppercase;
  color: var(--text-muted);
  margin: 0 0 10px 0;
}

.status-row {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  margin-bottom: 6px;
}

.status-tag {
  color: #48bb78;
  font-weight: bold;
}

.main-stage {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #ffffff;
  height: 100vh; /* 锁定高度 */
}

/* 核心容器：占据除去 Header 和 Footer 之外的所有空间 */
.chat-viewport {
  flex: 1;
  display: flex;
  flex-direction: column;
  max-width: 1000px;
  width: 100%;
  margin: 0 auto;
  overflow: hidden; /* 防止内部滚动条影响外部布局 */
}

/* 消息列表：启用垂直滚动 */
.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 30px;
}

/* 欢迎页美化 */
.welcome-hero {
  text-align: center;
  padding: 60px 20px;
}

.hero-icon {
  font-size: 50px;
  color: var(--primary);
  margin-bottom: 20px;
  opacity: 0.8;
}

.welcome-hero h2 {
  color: var(--text-dark);
  margin-bottom: 12px;
}

.welcome-hero p {
  color: var(--text-muted);
  max-width: 450px;
  margin: 0 auto 25px;
  line-height: 1.6;
}

.quick-tags {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.quick-tags span {
  padding: 8px 16px;
  background: #fff;
  border: 1px solid #e2e8f0;
  border-radius: 20px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s;
}

.quick-tags span:hover {
  border-color: var(--primary);
  color: var(--primary);
  background: var(--primary-light);
}

.message-row {
  display: flex;
  margin-bottom: 25px;
  width: 100%;
}

.avatar-box {
  width: 45px;
  flex-shrink: 0;
}

.avatar-circle {
  width: 36px;
  height: 36px;
  background: #f1f5f9;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-muted);
}

.user-row { flex-direction: row-reverse; }
.user-row .avatar-circle { background: var(--primary-light); color: var(--primary); }

.message-container {
  max-width: 75%;
  margin: 0 12px;
}

.message-bubble {
  padding: 14px 18px;
  border-radius: 16px;
  font-size: 15px;
  line-height: 1.6;
  position: relative;
  box-shadow: 0 2px 10px rgba(0,0,0,0.03);
  word-break: break-all; /* 防止长字符撑破布局 */
}

/* 顺便优化：机器人气泡保持浅色，文字为深色 */
.bot-row .message-bubble {
  background-color: var(--bot-bubble); /* 保持原有的浅灰色/白色背景 */
  color: var(--text-dark);
  border-top-left-radius: 4px;

  /* 核心修改：添加阴影效果 */
  /* 第一个阴影提供基础深度，第二个阴影提供环境光感 */
  box-shadow: 0 4px 12px rgba(0, 91, 172, 0.2);
  /* 可选：增加一个极细的边框，防止在纯白背景下“消失” */
  border: 1px solid rgba(0, 0, 0, 0.02);
  /* 增加过渡动画，让出现更平滑 */
  transition: transform 0.2s ease;
}

/* 修改：确保用户发送的消息气泡文字清晰可见 */
.user-row .message-bubble {
  /* 强制使用深蓝色背景 */
  background-color: var(--primary) !important;
  /* 强制文字为白色，解决看不见的问题 */
  color: #ffffff !important;
  border-top-right-radius: 4px;
  /* 增加阴影让气泡更立体 */
  box-shadow: 0 4px 12px rgba(0, 91, 172, 0.2);
}
/* 确保用户消息内部的所有文本（包括可能存在的 HTML）都继承白色 */
.user-row .content {
  color: black !important;
}

/* 输入区域：固定在底部 */
.input-area {
  padding: 20px 30px;
  background: #fff;
  border-top: 1px solid #f1f5f9;
  flex-shrink: 0; /* 保证输入区域不被压缩 */
}

.input-panel {
  display: flex;
  align-items: flex-end;
  gap: 12px;
  background: #f8fafc;
  padding: 10px 15px;
  border-radius: 16px;
  border: 1px solid #e2e8f0;
  transition: border-color 0.2s;
}

.input-panel:focus-within {
  border-color: var(--primary);
  background: #fff;
  box-shadow: 0 0 0 3px rgba(0, 91, 172, 0.05);
}

.input-panel :deep(.el-textarea__inner) {
  border: none;
  background: transparent;
  box-shadow: none;
  font-size: 15px;
  padding: 5px 0;
}

.send-btn-modern {
  height: 42px;
  width: 42px;
  min-width: 42px;
  border-radius: 12px;
  padding: 0 !important;
}

.safe-disclaimer {
  text-align: center;
  font-size: 12px;
  color: var(--text-muted);
  margin-top: 12px;
}

.right-sidebar {
  width: 260px;
  border-left: 1px solid #edf2f7;
  padding: 30px 20px;
}

/* 通用侧边栏卡片美化 */
.side-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 22px;
  border: 1px solid rgba(0, 91, 172, 0.08);
  /* 柔和的多层阴影，增加深度感 */
  box-shadow:
      0 4px 6px -1px rgba(0, 0, 0, 0.05),
      0 2px 4px -1px rgba(0, 0, 0, 0.03);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

.side-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 20px -5px rgba(0, 91, 172, 0.12);
}

.card-title {
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 10px;
  color: var(--primary-dark);
}

/* 就医必读卡片特别样式 */
.side-card:nth-child(1) {
  background: linear-gradient(to bottom right, #ffffff, #f0f7ff);
}

.side-card:nth-child(1) .card-title i {
  color: #e6a23c; /* 星星图标使用暖金色 */
  filter: drop-shadow(0 0 2px rgba(230, 162, 60, 0.4));
}

.tip-list li {
  position: relative;
  padding-left: 12px;
  margin-bottom: 14px;
  border-left: 2px solid var(--primary-light);
}

.tip-list li strong {
  color: var(--primary-dark);
  font-size: 14px;
}

.tip-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

/* 常见问题卡片特别样式 */
.help-card {
  background: linear-gradient(to bottom right, #ffffff, #f6f5ff);
}

.help-card .card-title i {
  color: var(--primary);
}

.q-item {
  font-size: 13px;
  color: #4a5568;
  padding: 10px 12px;
  margin-bottom: 8px;
  background: rgba(255, 255, 255, 0.6);
  border: 1px solid rgba(0, 91, 172, 0.05);
  border-radius: 8px;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
}

.q-item::before {
  content: "•";
  color: var(--primary);
  margin-right: 8px;
  font-weight: bold;
}

.q-item:hover {
  background: #ffffff;
  color: var(--primary);
  border-color: var(--primary-light);
  padding-left: 16px; /* 增加左移效果 */
  box-shadow: 2px 4px 8px rgba(0, 0, 0, 0.04);
}

.q-item {
  font-size: 14px;
  color: var(--primary);
  padding: 8px 0;
  border-bottom: 1px solid #f7fafc;
  cursor: pointer;
}

/* 急诊热线卡片 - 醒目红色增强版 */
.emergency-contact {
  background-color: #fff1f0 !important; /* 浅红色背景 */
  border: 2px solid #ff4d4f !important; /* 明显的红色边框 */
  box-shadow: 0 4px 12px rgba(211, 47, 47, 0.15) !important;
  text-align: center;
  position: relative;
  overflow: hidden;
  /* 呼吸闪烁动画，使其更醒目 */
  animation: emergency-glow 3s infinite ease-in-out;
}

/* 呼吸灯动画效果 */
@keyframes emergency-glow {
  0% { box-shadow: 0 0 5px rgba(255, 77, 79, 0.2); }
  50% { box-shadow: 0 0 20px rgba(255, 77, 79, 0.5); }
  100% { box-shadow: 0 0 5px rgba(255, 77, 79, 0.2); }
}

/* 标题文字颜色修改 */
.emergency-contact .card-title {
  color: #cf1322 !important; /* 深红色标题 */
  justify-content: center;
  font-weight: bold;
}

/* 电话号码样式增强 */
.phone-number {
  font-size: 22px !important; /* 增大字号 */
  font-weight: 900;
  color: #cf1322 !important;
  margin: 10px 0 5px;
  letter-spacing: 1px;
}

/* 底部说明文字 */
.note {
  font-size: 12px;
  color: #f5222d; /* 红色辅助文字 */
  margin: 0;
  opacity: 0.8;
}

/* 增加一个背景装饰图标（可选） */
.emergency-contact::after {
  content: "🚑";
  position: absolute;
  right: -5px;
  bottom: -5px;
  font-size: 40px;
  opacity: 0.1;
  transform: rotate(-15deg);
}
.mobile-header { display: none; }

@media (max-width: 1100px) {
  .right-sidebar { display: none; }
}

@media (max-width: 768px) {
  .left-sidebar { display: none; }
  .mobile-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 15px;
    background: #fff;
    border-bottom: 1px solid #edf2f7;
  }
  .mobile-brand {
    display: flex;
    align-items: center;
    gap: 10px;
    font-weight: 700;
    color: var(--primary);
  }
  .mini-logo { width: 28px; height: 28px; border-radius: 6px; }
  .chat-viewport { width: 100%; }
  .message-list { padding: 15px; }
  .message-container { max-width: 85%; }
  .input-area { padding: 10px 15px; }
}

@keyframes pulse {
  0% { transform: scale(0.95); box-shadow: 0 0 0 0 rgba(72, 187, 120, 0.7); }
  70% { transform: scale(1); box-shadow: 0 0 0 6px rgba(72, 187, 120, 0); }
  100% { transform: scale(0.95); box-shadow: 0 0 0 0 rgba(72, 187, 120, 0); }
}

.typing-indicator .dot {
  display: inline-block;
  width: 6px;
  height: 6px;
  background: var(--text-muted);
  border-radius: 50%;
  margin-right: 3px;
  animation: typing 1s infinite;
}
.typing-indicator .dot:nth-child(2) { animation-delay: 0.2s; }
.typing-indicator .dot:nth-child(3) { animation-delay: 0.4s; }

@keyframes typing {
  0%, 100% { opacity: 0.3; transform: translateY(0); }
  50% { opacity: 1; transform: translateY(-4px); }
}

/* 自定义滚动条美化（可选） */
.message-list::-webkit-scrollbar {
  width: 6px;
}
.message-list::-webkit-scrollbar-thumb {
  background: #dcdfe6;
  border-radius: 10px;
}
.message-list::-webkit-scrollbar-track {
  background: transparent;
}
</style>