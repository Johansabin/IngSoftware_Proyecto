package presentation.BC3_SupportChat;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupportChatHomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!doctype html>
                <html lang="es">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>MenteEnCasa</title>
                    <style>
                        * {
                            box-sizing: border-box;
                        }

                        body {
                            align-items: center;
                            background: #eeeeee;
                            color: #111111;
                            display: flex;
                            font-family: Arial, Helvetica, sans-serif;
                            justify-content: center;
                            margin: 0;
                            min-height: 100vh;
                            padding: 12px;
                        }

                        button, input, select, textarea {
                            font: inherit;
                        }

                        .phone {
                            background: #050505;
                            border: 3px solid #222222;
                            border-radius: 42px;
                            box-shadow: 0 18px 44px rgba(0, 0, 0, 0.28);
                            height: min(844px, calc(100vh - 24px));
                            padding: 14px;
                            position: relative;
                            width: min(390px, calc(100vw - 24px));
                        }

                        .screen {
                            background: #ffffff;
                            border-radius: 30px;
                            height: 100%;
                            overflow: hidden;
                            position: relative;
                        }

                        .notch {
                            background: #000000;
                            border-radius: 0 0 16px 16px;
                            height: 30px;
                            left: 50%;
                            position: absolute;
                            top: 0;
                            transform: translateX(-50%);
                            width: 106px;
                            z-index: 2;
                        }

                        .view {
                            height: 100%;
                            overflow-y: auto;
                            padding: 62px 34px 90px;
                            scrollbar-width: thin;
                        }

                        .hidden {
                            display: none !important;
                        }

                        .login-view {
                            align-items: center;
                            display: flex;
                            flex-direction: column;
                            justify-content: flex-start;
                            padding-top: 152px;
                        }

                        .brand {
                            font-size: 14px;
                            font-weight: 700;
                            margin-bottom: 22px;
                        }

                        .avatar {
                            height: 88px;
                            margin-bottom: 38px;
                            position: relative;
                            width: 88px;
                        }

                        .avatar::before {
                            background: #000000;
                            border-radius: 50%;
                            content: "";
                            height: 88px;
                            left: 0;
                            position: absolute;
                            top: 0;
                            width: 88px;
                        }

                        .avatar::after {
                            background:
                                radial-gradient(circle at 50% 25%, #ffffff 0 18px, transparent 19px),
                                radial-gradient(ellipse at 50% 78%, #ffffff 0 35px, transparent 36px);
                            content: "";
                            height: 88px;
                            left: 0;
                            position: absolute;
                            top: 0;
                            width: 88px;
                        }

                        .login-form {
                            align-items: center;
                            display: flex;
                            flex-direction: column;
                        }

                        .field {
                            border: 1px solid #555555;
                            height: 42px;
                            margin-bottom: 18px;
                            padding: 0 13px;
                            width: 250px;
                        }

                        .login-options {
                            display: flex;
                            font-size: 12px;
                            justify-content: space-between;
                            margin: 34px 0 78px;
                            width: 230px;
                        }

                        .login-button {
                            background: #101c5c;
                            border: 0;
                            color: #ffffff;
                            cursor: pointer;
                            font-size: 13px;
                            font-weight: 700;
                            height: 62px;
                            position: relative;
                            touch-action: manipulation;
                            width: 260px;
                            z-index: 4;
                        }

                        .app-header {
                            border-bottom: 1px solid #d0d0d0;
                            margin: -14px -18px 18px;
                            padding: 22px 18px 14px;
                        }

                        .app-header-row {
                            align-items: flex-start;
                            display: flex;
                            gap: 12px;
                            justify-content: space-between;
                        }

                        .app-title {
                            font-size: 18px;
                            font-weight: 700;
                            margin: 0;
                        }

                        .app-subtitle {
                            color: #555555;
                            font-size: 12px;
                            margin: 4px 0 0;
                        }

                        .module-list {
                            display: grid;
                            gap: 10px;
                        }

                        .module-row {
                            background: #ffffff;
                            border: 1px solid #b7b7b7;
                            cursor: pointer;
                            padding: 12px;
                            text-align: left;
                            width: 100%;
                        }

                        .module-row strong {
                            display: block;
                            font-size: 13px;
                            margin-bottom: 4px;
                        }

                        .module-row span {
                            color: #555555;
                            display: block;
                            font-size: 12px;
                        }

                        .section-title {
                            font-size: 15px;
                            margin: 18px 0 10px;
                        }

                        .panel {
                            border: 1px solid #b7b7b7;
                            margin-top: 12px;
                            padding: 12px;
                        }

                        .panel pre {
                            font-family: Consolas, "Courier New", monospace;
                            font-size: 11px;
                            margin: 0;
                            overflow-x: auto;
                            white-space: pre-wrap;
                        }

                        .input {
                            border: 1px solid #777777;
                            height: 38px;
                            margin-bottom: 10px;
                            padding: 0 10px;
                            width: 100%;
                        }

                        textarea.input {
                            height: 76px;
                            padding-top: 10px;
                            resize: none;
                        }

                        .action {
                            background: #101c5c;
                            border: 0;
                            color: #ffffff;
                            cursor: pointer;
                            font-size: 12px;
                            font-weight: 700;
                            height: 42px;
                            margin: 6px 0;
                            width: 100%;
                        }

                        .secondary-action {
                            background: #333333;
                        }

                        .status {
                            border: 1px solid #b7b7b7;
                            font-size: 12px;
                            margin-top: 10px;
                            padding: 9px;
                        }

                        .chat-card {
                            border: 1px solid #b7b7b7;
                            margin-top: 10px;
                            padding: 10px;
                        }

                        .chat-card strong {
                            display: block;
                            font-size: 12px;
                            margin-bottom: 5px;
                        }

                        .message {
                            border-left: 3px solid #101c5c;
                            font-size: 12px;
                            margin-top: 8px;
                            padding: 6px 8px;
                        }

                        .top-actions {
                            align-items: center;
                            background: #ffffff;
                            display: flex;
                            gap: 10px;
                            justify-content: space-between;
                            margin: -14px -18px 14px;
                            padding: 14px 18px 8px;
                            position: sticky;
                            top: -62px;
                            z-index: 4;
                        }

                        .back-button {
                            background: #ffffff;
                            border: 1px solid #777777;
                            cursor: pointer;
                            display: inline-flex;
                            font-size: 12px;
                            font-weight: 700;
                            margin-bottom: 12px;
                            padding: 8px 12px;
                            position: sticky;
                            top: 0;
                            z-index: 3;
                        }

                        .logout-button {
                            background: #ffffff;
                            border: 1px solid #777777;
                            cursor: pointer;
                            font-size: 12px;
                            font-weight: 700;
                            padding: 8px 12px;
                        }

                        .bottom-back-button {
                            margin-top: 18px;
                            width: 100%;
                        }

                        @media (max-width: 520px) {
                            body {
                                background: #ffffff;
                                padding: 0;
                            }

                            .phone {
                                border: 0;
                                border-radius: 0;
                                box-shadow: none;
                                height: 100dvh;
                                padding: 0;
                                width: 100vw;
                            }

                            .screen {
                                border-radius: 0;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="phone">
                        <div class="screen">
                            <div class="notch"></div>

                            <main id="loginView" class="view login-view">
                                <div class="brand">MenteEnCasa</div>
                                <div class="avatar" aria-hidden="true"></div>
                                <form id="loginForm" class="login-form">
                                    <input id="email" class="field" value="20261234" placeholder="Email">
                                    <input id="password" class="field" value="password" type="password" placeholder="Password">
                                    <div class="login-options">
                                        <span>Remember me</span>
                                        <span>Forgot password</span>
                                    </div>
                                    <button class="login-button" type="submit">LOGIN</button>
                                </form>
                            </main>

                            <main id="dashboardView" class="view hidden">
                                <div class="app-header">
                                    <div class="app-header-row">
                                        <div>
                                            <p class="app-title">MenteEnCasa</p>
                                            <p class="app-subtitle">Bienestar emocional universitario</p>
                                        </div>
                                        <button class="logout-button" type="button" onclick="logout()">Cerrar sesion</button>
                                    </div>
                                </div>

                                <h2 class="section-title">Modulos</h2>
                                <div class="module-list">
                                    <button class="module-row" onclick="openModule('BC1 Autenticacion', '/api/modules/bc1-authentication')">
                                        <strong>BC1 Autenticacion</strong>
                                        <span>Sesion institucional y auditoria.</span>
                                    </button>
                                    <button class="module-row" onclick="openModule('BC2 Seguimiento Emocional', '/api/modules/bc2-emotional-tracking')">
                                        <strong>BC2 Seguimiento Emocional</strong>
                                        <span>Bitacora, emociones y resumen semanal.</span>
                                    </button>
                                    <button class="module-row" onclick="openChat()">
                                        <strong>BC3 Soporte Chat</strong>
                                        <span>Chat anonimo con psicologo.</span>
                                    </button>
                                    <button class="module-row" onclick="openModule('BC4 Recomendaciones', '/api/modules/bc4-recommendations')">
                                        <strong>BC4 Recomendaciones</strong>
                                        <span>Recomendaciones de bienestar.</span>
                                    </button>
                                    <button class="module-row" onclick="openModule('BC5 Notificaciones', '/api/modules/bc5-notifications')">
                                        <strong>BC5 Notificaciones</strong>
                                        <span>Recordatorios programados.</span>
                                    </button>
                                    <button class="module-row" onclick="openModule('BC6 Privacidad', '/api/modules/bc6-privacy-security')">
                                        <strong>BC6 Privacidad</strong>
                                        <span>Politicas y consentimientos.</span>
                                    </button>
                                </div>
                                <div id="dashboardStatus" class="status">Sesion iniciada con BC1.</div>
                            </main>

                            <main id="moduleView" class="view hidden">
                                <div class="top-actions">
                                    <button class="back-button" type="button" onclick="showDashboard()">Volver</button>
                                    <button class="logout-button" type="button" onclick="logout()">Cerrar sesion</button>
                                </div>
                                <div class="app-header">
                                    <p id="moduleTitle" class="app-title">Modulo</p>
                                    <p class="app-subtitle">Respuesta del bounded context</p>
                                </div>
                                <div id="moduleResult" class="panel"></div>
                                <button class="back-button bottom-back-button" onclick="showDashboard()">Volver</button>
                            </main>

                            <main id="chatView" class="view hidden">
                                <div class="top-actions">
                                    <button class="back-button" type="button" onclick="showDashboard()">Volver</button>
                                    <button class="logout-button" type="button" onclick="logout()">Cerrar sesion</button>
                                </div>
                                <div class="app-header">
                                    <p class="app-title">Soporte Chat</p>
                                    <p class="app-subtitle">Sesion anonima con psicologo</p>
                                </div>
                                <input id="psychologistId" class="input" value="22222222-2222-2222-2222-222222222222">
                                <button class="action" onclick="startChat()">Crear chat</button>
                                <input id="chatId" class="input" placeholder="Chat ID">
                                <select id="senderRole" class="input">
                                    <option value="STUDENT">Estudiante</option>
                                    <option value="PSYCHOLOGIST">Psicologo</option>
                                </select>
                                <textarea id="content" class="input">Hola, necesito apoyo.</textarea>
                                <button class="action secondary-action" onclick="sendMessage()">Enviar mensaje</button>
                                <button class="action" onclick="loadActiveChats()">Ver chats activos</button>
                                <div id="chatStatus" class="status">Listo para crear una sesion.</div>
                                <div id="chatResult"></div>
                                <button class="back-button bottom-back-button" onclick="showDashboard()">Volver</button>
                            </main>
                        </div>
                    </div>

                    <script>
                        const loginForm = document.getElementById('loginForm');
                        const loginView = document.getElementById('loginView');
                        const dashboardView = document.getElementById('dashboardView');
                        const moduleView = document.getElementById('moduleView');
                        const chatView = document.getElementById('chatView');
                        const dashboardStatus = document.getElementById('dashboardStatus');
                        const moduleTitle = document.getElementById('moduleTitle');
                        const moduleResult = document.getElementById('moduleResult');
                        const chatStatus = document.getElementById('chatStatus');
                        const chatResult = document.getElementById('chatResult');

                        function show(view) {
                            loginView.classList.add('hidden');
                            dashboardView.classList.add('hidden');
                            moduleView.classList.add('hidden');
                            chatView.classList.add('hidden');
                            view.classList.remove('hidden');
                            view.scrollTop = 0;
                        }

                        async function login() {
                            const response = await fetch('/api/modules/bc1-authentication');
                            const data = await response.json();
                            dashboardStatus.textContent = `Sesion activa para ${data.codigoUsuario}.`;
                            show(dashboardView);
                        }

                        function logout() {
                            moduleResult.innerHTML = '';
                            chatResult.innerHTML = '';
                            chatStatus.textContent = 'Listo para crear una sesion.';
                            dashboardStatus.textContent = 'Sesion iniciada con BC1.';
                            show(loginView);
                        }

                        function showDashboard() {
                            show(dashboardView);
                        }

                        async function openModule(title, url) {
                            moduleTitle.textContent = title;
                            moduleResult.innerHTML = '<pre>Cargando...</pre>';
                            show(moduleView);
                            const response = await fetch(url);
                            const data = await response.json();
                            moduleResult.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
                        }

                        function openChat() {
                            show(chatView);
                        }

                        function renderChat(chat) {
                            const messages = chat.messages.map(message => `
                                <div class="message">
                                    <strong>${message.senderRole}</strong>
                                    <div>${message.content}</div>
                                    <small>${message.sentAt}</small>
                                </div>
                            `).join('');

                            return `
                                <div class="chat-card">
                                    <strong>Chat ${chat.id}</strong>
                                    <div>Estudiante: ${chat.studentPseudonym}</div>
                                    <div>Psicologo: ${chat.psychologistId}</div>
                                    <div>Estado: ${chat.status}</div>
                                    ${messages || '<p>Sin mensajes todavia.</p>'}
                                </div>
                            `;
                        }

                        async function startChat() {
                            const psychologistId = document.getElementById('psychologistId').value;
                            const response = await fetch('/api/support-chats', {
                                method: 'POST',
                                headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify({psychologistId})
                            });
                            const chat = await response.json();
                            document.getElementById('chatId').value = chat.id;
                            chatResult.innerHTML = renderChat(chat);
                            chatStatus.textContent = 'Chat creado correctamente.';
                        }

                        async function sendMessage() {
                            const chatId = document.getElementById('chatId').value;
                            const content = document.getElementById('content').value;
                            const senderRole = document.getElementById('senderRole').value;
                            const response = await fetch(`/api/support-chats/${chatId}/messages`, {
                                method: 'POST',
                                headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify({content, senderRole})
                            });
                            const chat = await response.json();
                            chatResult.innerHTML = renderChat(chat);
                            chatStatus.textContent = 'Mensaje enviado correctamente.';
                        }

                        async function loadActiveChats() {
                            const psychologistId = document.getElementById('psychologistId').value;
                            const response = await fetch(`/api/support-chats/psychologists/${psychologistId}/active`);
                            const chats = await response.json();
                            chatResult.innerHTML = chats.length
                                ? chats.map(renderChat).join('')
                                : '<p>No hay chats activos para este psicologo.</p>';
                            chatStatus.textContent = 'Consulta completada.';
                        }

                        loginForm.addEventListener('submit', event => {
                            event.preventDefault();
                            login();
                        });
                    </script>
                </body>
                </html>
                """;
    }
}
