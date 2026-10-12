pipeline {
    agent any
    
    // Herramientas configuradas previamente en Jenkins[cite: 164]
    tools {
        maven 'maven'
        jdk 'JAVA'
    }

    environment {
        SCANNER_HOME = tool 'sonar-scanner'
        IMAGE_NAME = "menteencasa-bc1"
    }

    stages {
        stage('Git Checkout') {
            steps {
                // Disparado por eventos (commits en GitHub)[cite: 166]
                checkout scm
            }
        }

        stage('Build & Unit Tests (Maven)') {
            steps {
                // Compilación, gestión de dependencias y empaquetado[cite: 166]
                // Genera el reporte JaCoCo para SonarQube[cite: 170]
                bat "mvn clean package"
            }
        }

        stage('Static Analysis (SonarQube)') {
            steps {
                // Análisis estático integrado con Jenkins[cite: 166]
                bat "${SCANNER_HOME}/bin/sonar-scanner"
            }
        }

        stage('Docker Build & Run (Deployment)') {
            steps {
                // Despliegue automático por "Container" - Docker[cite: 166, 167]
                bat "docker build -t ${IMAGE_NAME}:latest ."
                // Levanta el contenedor temporalmente en el puerto 8080 para las pruebas dinámicas
                bat "docker run -d -p 8080:8080 --name app-bc1 ${IMAGE_NAME}:latest"
                // Espera a que la aplicación inicie
                sleep(time: 15, unit: 'SECONDS')
            }
        }

        stage('Functional Tests (Newman/Postman)') {
            steps {
                // Pruebas automatizadas funcionales[cite: 164, 166]
                // Requiere NodeJS y Newman instalados en el servidor Jenkins
                bat "newman run tests-automatizados/bc1-postman-collection.json"
            }
        }

        stage('Performance Tests (JMeter)') {
            steps {
                // Pruebas automatizadas de rendimiento[cite: 164, 166]
                bat "jmeter -n -t tests-automatizados/bc1-rendimiento.jmx -l tests-automatizados/resultados-jmeter.jtl"
            }
        }

        stage('Security Tests (OWASP ZAP)') {
            steps {
                // Pruebas automatizadas de seguridad integradas[cite: 164, 166]
                // Lanza un escaneo rápido sobre el contenedor desplegado localmente
                bat "zap-cli quick-scan --self-contained http://localhost:8080"
            }
        }
    }

    post {
        always {
            // Detiene y elimina el contenedor de prueba al terminar el pipeline
            bat "docker stop app-bc1"
            bat "docker rm app-bc1"
        }
        failure {
            // Notificación de errores al equipo (Gestión de cambios)[cite: 166]
            echo "El pipeline ha fallado. Revisa los logs y actualiza el Kanban (GitHub Projects)."
        }
    }
}
