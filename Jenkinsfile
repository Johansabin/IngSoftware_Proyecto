pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    env.SHORT_COMMIT = sh(
                        returnStdout: true,
                        script: 'git rev-parse --short HEAD'
                    ).trim()
                    currentBuild.description = "commit ${env.SHORT_COMMIT} · build #${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Build & Unit Tests') {
            steps {
                // Trazabilidad de versiones de Maven y Java usadas en el agente
                sh 'mvn -v | head -n 1 && java -version 2>&1 | head -n 1'
                // Compila todo el proyecto (BC1-BC6), ejecuta JUnit 5, genera reporte JaCoCo y empaqueta el JAR
                sh 'mvn -B clean package'
            }
        }

        stage('Archive Artifact') {
            steps {
                // Archiva el JAR ejecutable antes de Sonar para conservarlo aunque falle el analisis
                archiveArtifacts artifacts: 'target/menteencasa*.jar',
                                 excludes: '**/*.original',
                                 fingerprint: true,
                                 allowEmptyArchive: false
            }
        }

        stage('Static Analysis (SonarQube)') {
            steps {
                script {
                    try {
                        withCredentials([string(credentialsId: 'sonar-token',
                                                variable: 'SONAR_TOKEN')]) {
                            // Comillas simples para evitar que Groovy interpole o exponga el token
                            sh 'mvn -B sonar:sonar -Dsonar.token=${SONAR_TOKEN}'
                        }
                    } catch (err) {
                        currentBuild.result = 'FAILURE'
                        error("Fallo en el analisis de SonarQube (la compilacion y los tests si pasaron): ${err.message}")
                    }
                }
            }
        }
    }

    post {
        always {
            // Publica los resultados de las pruebas unitarias JUnit en Jenkins
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
        }
        success {
            echo "Pipeline completado en verde para commit ${env.SHORT_COMMIT}."
        }
        failure {
            echo "Pipeline fallido en commit ${env.SHORT_COMMIT}. Revisa la consola y los reportes de tests."
        }
    }
}
