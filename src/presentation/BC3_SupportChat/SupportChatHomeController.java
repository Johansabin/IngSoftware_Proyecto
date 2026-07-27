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
                        :root {
                            color-scheme: light;
                            font-family: Arial, Helvetica, sans-serif;
                            --ink: #111111;
                            --muted: #555555;
                            --line: #bbbbbb;
                            --panel: #ffffff;
                            --bg: #f5f5f5;
                        }

                        * {
                            box-sizing: border-box;
                        }

                        body {
                            background: var(--bg);
                            color: var(--ink);
                            margin: 0;
                        }

                        header {
                            background: #ffffff;
                            border-bottom: 1px solid var(--line);
                            padding: 22px 28px;
                        }

                        h1 {
                            font-size: 24px;
                            margin: 0 0 4px;
                        }

                        h2 {
                            font-size: 18px;
                            margin: 0 0 14px;
                        }

                        p {
                            color: var(--muted);
                            margin: 0;
                        }

                        main {
                            display: grid;
                            gap: 18px;
                            grid-template-columns: 1fr;
                            padding: 24px;
                        }

                        .workspace {
                            display: grid;
                            gap: 18px;
                            grid-template-columns: 360px minmax(0, 1fr);
                        }

                        .modules {
                            display: grid;
                            gap: 10px;
                            grid-template-columns: repeat(3, minmax(0, 1fr));
                        }

                        .module {
                            border: 1px solid var(--line);
                            padding: 14px;
                        }

                        .module strong {
                            display: block;
                            margin-bottom: 6px;
                        }

                        .module button {
                            width: 100%;
                        }

                        section {
                            background: var(--panel);
                            border: 1px solid var(--line);
                            padding: 18px;
                        }

                        label {
                            display: block;
                            font-size: 13px;
                            font-weight: 700;
                            margin: 14px 0 6px;
                        }

                        input, select, textarea {
                            border: 1px solid #999999;
                            font: inherit;
                            padding: 10px;
                            width: 100%;
                        }

                        textarea {
                            min-height: 92px;
                            resize: vertical;
                        }

                        button {
                            background: #222222;
                            border: 1px solid #222222;
                            color: #ffffff;
                            cursor: pointer;
                            font-weight: 700;
                            margin-top: 12px;
                            padding: 10px 14px;
                        }

                        button.secondary {
                            background: #444444;
                            border-color: #444444;
                        }

                        .status {
                            background: #ffffff;
                            border: 1px solid var(--line);
                            color: var(--ink);
                            font-size: 13px;
                            margin-top: 12px;
                            padding: 10px;
                        }

                        .chat {
                            border: 1px solid var(--line);
                            margin-top: 12px;
                            padding: 14px;
                        }

                        .chat strong {
                            display: block;
                            margin-bottom: 6px;
                        }

                        .message {
                            background: #ffffff;
                            border-left: 4px solid #444444;
                            margin-top: 8px;
                            padding: 8px 10px;
                        }

                        code {
                            background: #eeeeee;
                            padding: 2px 5px;
                        }

                        @media (max-width: 820px) {
                            main, .workspace, .modules {
                                grid-template-columns: 1fr;
                                padding: 14px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <header>
                        <h1>MenteEnCasa</h1>
                        <p>Seguimiento emocional, soporte y bienestar para estudiantes.</p>
                    </header>

                    <main>
                        <section>
                            <h2>Modulos del sistema</h2>
                            <div class="modules">
                                <div class="module">
                                    <strong>BC1 Autenticacion</strong>
                                    <p>Sesion institucional y auditoria.</p>
                                    <button onclick="loadModule('/api/modules/bc1-authentication')">Probar BC1</button>
                                </div>
                                <div class="module">
                                    <strong>BC2 Seguimiento Emocional</strong>
                                    <p>Bitacora, emociones y resumen semanal.</p>
                                    <button onclick="loadModule('/api/modules/bc2-emotional-tracking')">Probar BC2</button>
                                </div>
                                <div class="module">
                                    <strong>BC3 Soporte Chat</strong>
                                    <p>Chat anonimo con psicologo.</p>
                                    <button onclick="loadActiveChats()">Probar BC3</button>
                                </div>
                                <div class="module">
                                    <strong>BC4 Recomendaciones</strong>
                                    <p>Catalogo de recomendaciones de bienestar.</p>
                                    <button onclick="loadModule('/api/modules/bc4-recommendations')">Probar BC4</button>
                                </div>
                                <div class="module">
                                    <strong>BC5 Notificaciones</strong>
                                    <p>Recordatorios programados.</p>
                                    <button onclick="loadModule('/api/modules/bc5-notifications')">Probar BC5</button>
                                </div>
                                <div class="module">
                                    <strong>BC6 Privacidad</strong>
                                    <p>Politicas y consentimientos.</p>
                                    <button onclick="loadModule('/api/modules/bc6-privacy-security')">Probar BC6</button>
                                </div>
                            </div>
                        </section>

                        <div class="workspace">
                            <section>
                                <h2>Chat de soporte</h2>
                                <label for="psychologistId">Psicologo ID</label>
                                <input id="psychologistId" value="22222222-2222-2222-2222-222222222222">
                                <button onclick="startChat()">Crear chat</button>

                                <label for="chatId">Chat ID</label>
                                <input id="chatId" placeholder="Se completa al crear un chat">

                                <label for="senderRole">Remitente</label>
                                <select id="senderRole">
                                    <option value="STUDENT">Estudiante</option>
                                    <option value="PSYCHOLOGIST">Psicologo</option>
                                </select>

                                <label for="content">Mensaje</label>
                                <textarea id="content">Hola, necesito apoyo.</textarea>
                                <button class="secondary" onclick="sendMessage()">Enviar mensaje</button>

                                <button onclick="loadActiveChats()">Ver chats activos del psicologo</button>
                                <div id="status" class="status">Listo para probar la API.</div>
                            </section>

                            <section>
                                <h2>Respuesta del backend</h2>
                                <div id="result"></div>
                            </section>
                        </div>
                    </main>

                    <script>
                        const statusBox = document.getElementById('status');
                        const result = document.getElementById('result');

                        function setStatus(text) {
                            statusBox.textContent = text;
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
                                <div class="chat">
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
                            result.innerHTML = renderChat(chat);
                            setStatus('Chat creado correctamente.');
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
                            result.innerHTML = renderChat(chat);
                            setStatus('Mensaje enviado correctamente.');
                        }

                        async function loadActiveChats() {
                            const psychologistId = document.getElementById('psychologistId').value;
                            const response = await fetch(`/api/support-chats/psychologists/${psychologistId}/active`);
                            const chats = await response.json();
                            result.innerHTML = chats.length
                                ? chats.map(renderChat).join('')
                                : '<p>No hay chats activos para este psicologo.</p>';
                            setStatus('Consulta completada.');
                        }

                        async function loadModule(url) {
                            const response = await fetch(url);
                            const data = await response.json();
                            result.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
                            setStatus(`${data.boundedContext} consultado correctamente.`);
                        }
                    </script>
                </body>
                </html>
                """;
    }
}
